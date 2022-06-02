package com.example.cmu_project.activities;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adevinta.leku.LocationPickerActivity;
import com.example.cmu_project.GlobalVariables;
import com.example.cmu_project.R;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.examples.backendserver.ServerGrpc;
import io.grpc.examples.backendserver.messageResponse;
import io.grpc.examples.backendserver.sendingMessage;

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
        messageAdapter = new MessageAdapter(this, messageList);
        messageRecycler.setLayoutManager(new LinearLayoutManager(this));
        messageRecycler.setAdapter(messageAdapter);
    }

    public void sendMessage(View view) {
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(hostEdit.getWindowToken(), 0);
        sendButton.setEnabled(false);

        new GrpcTask(this, messageRecycler)
                .execute(
                        hostEdit.getText().toString(),
                        messageEdit.getText().toString(),
                        portEdit.getText().toString());
    }

    private class GrpcTask extends AsyncTask<String, Void, messageResponse> {
        private final WeakReference<Activity> activityReference;
        private ManagedChannel channel;
        private final MessageAdapter messageAdapter;
        private final RecyclerView recyclerView;

        private GrpcTask(Activity activity, RecyclerView messageRecycler) {
            this.activityReference = new WeakReference<>(activity);
            this.messageAdapter = (MessageAdapter) messageRecycler.getAdapter();
            this.recyclerView = messageRecycler;
        }

        @Override
        protected messageResponse doInBackground(String... params) {
            String host = params[0];
            String message = params[1];
            String portStr = params[2];
            int port = TextUtils.isEmpty(portStr) ? 0 : Integer.valueOf(portStr);
            try {
                channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
                ServerGrpc.ServerBlockingStub stub = ServerGrpc.newBlockingStub(channel);

                sendingMessage request = sendingMessage.newBuilder()
                        .setData(message)
                        .setUsername(((GlobalVariables) getApplication()).getUsername())
                        .setChatroom(((GlobalVariables) getApplication()).getCurrentChatroomName())
                        .build();

                return stub.sendMessage(request);

            } catch (Exception e) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                pw.flush();
                logger.info(sw.toString());
                return null;
            }
        }

        @Override
        protected void onPostExecute(messageResponse result) {
            if(result != null){
                try {
                    channel.shutdown().awaitTermination(1, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                Activity activity = activityReference.get();
                if (activity == null) {
                    return;
                }

                messageAdapter.addToMessageList(result);
                int position = messageAdapter.getItemCount() - 1;
                messageAdapter.notifyItemInserted(position);
                recyclerView.post(() -> {
                    // Call smooth scroll
                    recyclerView.smoothScrollToPosition(position);
                });

                //testing global vars
            /* String chatName = ((GlobalVariables) getApplication()).getCurrentChatroomName();
            Log.d("CHAT_NAME: ", chatName);*/

                Button sendButton = (Button) activity.findViewById(R.id.send_button);
                sendButton.setEnabled(true);
            }
        }
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

        new GrpcTask(this, messageRecycler)
                .execute(
                        hostEdit.getText().toString(),
                        geolocation,
                        portEdit.getText().toString());
    }

    public void showCamera(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            logger.warning(e.getMessage());
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
            Double latitude = data.getDoubleExtra("", 0.0);
            Log.d("LATITUDE****", latitude.toString());
            Double longitude = data.getDoubleExtra("", 0.0);
            Log.d("LONGITUDE****", longitude.toString());
            /*
            String address = data.getStringExtra("");
            Log.d("ADDRESS****", address != null ? address : "");
            String postalcode = data.getStringExtra("");
            Log.d("POSTALCODE****", postalcode != null ? postalcode : "");
            String timeZoneId = data.getStringExtra("");
            Log.d("TIME ZONE ID****", timeZoneId != null ? timeZoneId : "");
            String timeZoneDisplayName = data.getStringExtra("");
            Log.d("TIME ZONE NAME****", timeZoneDisplayName != null ? timeZoneDisplayName : "");
             */
            String geolocation = latitude + "/" + longitude;
            sendGeolocation(geolocation);
        }
        if (resultCode == Activity.RESULT_CANCELED) {
            Log.d("RESULT****", "CANCELLED");
        }
    }

    private void sendPhoto(Bitmap imageBitmap) {
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(hostEdit.getWindowToken(), 0);
        sendButton.setEnabled(false);

        new GrpcTask(this, messageRecycler)
                .execute(
                        hostEdit.getText().toString(),
                        bitMapToString(imageBitmap),
                        portEdit.getText().toString());
    }

    private String bitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

}
