package com.example.cmu_project.activities;

import com.adevinta.leku.LocationPickerActivity;
import com.example.cmu_project.DBHelper;
import com.example.cmu_project.GlobalVariables;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;

import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cmu_project.GrpcTasks.*;
import com.example.cmu_project.MyOnScrollListener;
import com.example.cmu_project.R;
import com.example.cmu_project.adapters.MessageAdapter;
import com.example.cmu_project.contexts.MobDataContext;
import com.example.cmu_project.contexts.WifiContext;
import com.example.cmu_project.enums.MessageType;

import java.io.ByteArrayOutputStream;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.examples.backendserver.*;

public class ChatActivity extends AppCompatActivity {

    private static final Logger logger = Logger.getLogger(ChatActivity.class.getName());
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_LOCATION_PICKER = 2;

    private Button sendButton;
    private EditText hostEdit;
    private EditText portEdit;
    private EditText messageEdit;
    private RecyclerView messageRecycler;
    private MessageAdapter messageAdapter;
    private LinearLayoutManager linearLayoutManager;
    private boolean loadingMoreMessages = false;
    private boolean gotAllChatMessages;

    private List<messageResponse> messageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        sendButton = (Button) findViewById(R.id.send_button);
        hostEdit = (EditText) findViewById(R.id.host_edit_text);
        portEdit = (EditText) findViewById(R.id.port_edit_text);
        messageEdit = (EditText) findViewById(R.id.message_edit_text);
        messageRecycler = (RecyclerView) findViewById(R.id.recyclerView);

        retrieveMessagesFromCache();

        messageAdapter = new MessageAdapter(this, messageList);

        linearLayoutManager = new LinearLayoutManager(this);
        messageRecycler.setLayoutManager(linearLayoutManager);
        messageRecycler.setAdapter(messageAdapter);

        MyOnScrollListener myOnScrollListener = new MyOnScrollListener(this, messageRecycler);

        if(!messageList.isEmpty()){
            int firstPosition = messageList.get(0).getPosition();
            if(firstPosition == 1){
                myOnScrollListener.setGotAllChatMessages(true);
            }
        }

        messageRecycler.addOnScrollListener(myOnScrollListener);

        WifiContext wifiContext = new WifiContext();
        MobDataContext MDContext = new MobDataContext();
        if(wifiContext.conforms(this)){
            //Toast.makeText(getApplicationContext(),"Connected to wifi.", Toast.LENGTH_SHORT).show();
            if(messageList.isEmpty()) {
                Log.d("ChatActivity", "messageList is Empty");
                //change IP for this to work
                //after load IP and port from file or whatever just use those vars
                new getAllMessagesFromChatGrpcTask(this, messageRecycler)
                        .execute(
                                "192.168.1.135",
                                "50051",
                                ((GlobalVariables) this.getApplication()).getCurrentChatroomName());
            } else {
                Log.d("ChatActivity", "Loaded some messages from cache. Now contact server.");

                int position = (messageList.get(messageAdapter.getItemCount() - 1)).getPosition();

                new getRemainingMessagesGrpcTask(this, messageRecycler)
                        .execute(
                                "192.168.1.135",
                                "50051",
                                position,
                                ((GlobalVariables) this.getApplication()).getCurrentChatroomName());
            }
        } else if(MDContext.conforms(this)){
            //Toast.makeText(getApplicationContext(),"Connected to mobile data.", Toast.LENGTH_SHORT).show();

            if(messageList.isEmpty()) {
                Log.d("ChatActivity", "messageList is Empty");
                new getLastNMessagesFromChatGrpcTask(this, messageRecycler)
                        .execute(
                                "192.168.1.135",
                                "50051",
                                ((GlobalVariables) this.getApplication()).getCurrentChatroomName());
            } else {
                int position = (messageList.get(messageAdapter.getItemCount() - 1)).getPosition();
                new getRemainingMessagesMobileDataGrpcTask(this, messageRecycler)
                        .execute(
                                "192.168.1.135",
                                "50051",
                                position,
                                ((GlobalVariables) this.getApplication()).getCurrentChatroomName());
            }
        } else {
            Toast.makeText(getApplicationContext(),
                    "No connection available. Please connect to the internet.", Toast.LENGTH_SHORT).show();
        }

        messageRecycler.post(() -> {
            // Call smooth scroll
            messageRecycler.smoothScrollToPosition(messageAdapter.getItemCount());
        });
    }

    public void setLoadingMoreMessages(boolean loadingMoreMessages) {
        this.loadingMoreMessages = loadingMoreMessages;
    }

    private void retrieveMessagesFromCache() {
        String chatroom = ((GlobalVariables) this.getApplication()).getCurrentChatroomName();

        Cursor messagesCursor = ((GlobalVariables) this.getApplication()).getDb()
                                                    .getAllChatroomMessages(chatroom);
        messagesCursor.moveToFirst();
        Log.d("CHAT ACTIVITY", String.valueOf(messagesCursor.getCount()));
        messageResponse message;
        while(!messagesCursor.isAfterLast()){

            message = messageResponse.newBuilder()
                    .setChatroom(messagesCursor.getString(messagesCursor.getColumnIndexOrThrow(DBHelper.MESSAGES_COLUMN_CHATROOM)))
                    .setData(messagesCursor.getString(messagesCursor.getColumnIndexOrThrow(DBHelper.MESSAGES_COLUMN_DATA)))
                    .setUsername(messagesCursor.getString(messagesCursor.getColumnIndexOrThrow(DBHelper.MESSAGES_COLUMN_USERNAME)))
                    .setType(Integer.parseInt(messagesCursor.getString(messagesCursor.getColumnIndexOrThrow(DBHelper.MESSAGES_COLUMN_TYPE))))
                    .setTimestamp(messagesCursor.getString(messagesCursor.getColumnIndexOrThrow(DBHelper.MESSAGES_COLUMN_TIMESTAMP)))
                    .setPosition(Integer.parseInt(messagesCursor.getString(messagesCursor.getColumnIndexOrThrow(DBHelper.MESSAGES_COLUMN_POSITION))))
                    .build();
            Log.d("CHAT ACTIVITY", String.valueOf(message));
            messageList.add(message);

            messagesCursor.moveToNext();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            sendPhoto(imageBitmap);
        } else if (requestCode == REQUEST_LOCATION_PICKER && resultCode == RESULT_OK) {
            double latitude = data.getDoubleExtra("latitude", 0.0);
            double longitude = data.getDoubleExtra("longitude", 0.0);

            String geolocation = latitude + "/" + longitude;

            sendGeolocation(geolocation);
        }
        if (resultCode == Activity.RESULT_CANCELED) {
            Log.d("RESULT****", "CANCELLED");
        }
    }

    public void sendText(View view) {
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(hostEdit.getWindowToken(), 0);
        sendButton.setEnabled(false);

        new sendMessageGrpcTask(this, messageRecycler)
                .execute(
                        "192.168.1.135",
                        messageEdit.getText().toString(),
                        "50051",
                        MessageType.TEXT.getValue());
    }

    public void showMap(View view) {
        Intent locationPickerIntent = new LocationPickerActivity.Builder()
                .withLocation(41.4036299, 2.1743558)
                .withGeolocApiKey("AIzaSyBVHoyrgJOlu_Zla_PLsWFXb8XMhVzCxDU")
                .withSearchZone("en_EN")
                //.withSearchZone(SearchZoneRect(LatLng(26.525467, -18.910366), LatLng(43.906271, 5.394197)))
                .withDefaultLocaleSearchZone()
                .shouldReturnOkOnBackPressed()
                .withStreetHidden()
                .withCityHidden()
                .withZipCodeHidden()
                .withSatelliteViewHidden()
                //.withGooglePlacesEnabled()
                .withGoogleTimeZoneEnabled()
                .withVoiceSearchHidden()
                .withUnnamedRoadHidden()
                //.withSearchBarHidden()
                .build(getApplicationContext());

        try {
            startActivityForResult(locationPickerIntent, REQUEST_LOCATION_PICKER);
        } catch (ActivityNotFoundException e) {
            logger.warning(e.getMessage());
        }

    }

    private void sendGeolocation(String geolocation) {
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(hostEdit.getWindowToken(), 0);
        sendButton.setEnabled(false);

        new sendMessageGrpcTask(this, messageRecycler)
                .execute(
                        "192.168.1.135",
                        geolocation,
                        "50051",
                        MessageType.GEOLOCATION.getValue());
    }

    public void showCamera(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            logger.warning(e.getMessage());
        }
    }

    private void sendPhoto(Bitmap imageBitmap) {
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(hostEdit.getWindowToken(), 0);
        sendButton.setEnabled(false);

        new sendMessageGrpcTask(this, messageRecycler)
                .execute(
                        "192.168.1.135",
                        bitMapToString(imageBitmap),
                        "50051",
                        MessageType.PHOTO.getValue());
    }

    private String bitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

}
