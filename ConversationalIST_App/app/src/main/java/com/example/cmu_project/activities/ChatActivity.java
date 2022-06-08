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
        messageRecycler.setLayoutManager(new LinearLayoutManager(this));
        messageRecycler.setAdapter(messageAdapter);

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

                int position = messageList.size() - 1;
                new getRemainingMessagesGrpcTask(this, messageRecycler)
                        .execute(
                                "192.168.1.135",
                                "50051",
                                position,
                                ((GlobalVariables) this.getApplication()).getCurrentChatroomName());
            }
        } else if(MDContext.conforms(this)){
            //Toast.makeText(getApplicationContext(),"Connected to mobile data.", Toast.LENGTH_SHORT).show();
            Log.d("ChatActivity", "messageList is Empty");

            if(messageList.isEmpty()) {
                new getLastNMessagesFromChatGrpcTask(this, messageRecycler)
                        .execute(
                                "192.168.1.135",
                                "50051",
                                ((GlobalVariables) this.getApplication()).getCurrentChatroomName());
            } else {
                //TODO
            }
        } else {
            Toast.makeText(getApplicationContext(),
                    "No connection available.", Toast.LENGTH_SHORT).show();
        }

        messageRecycler.post(() -> {
            // Call smooth scroll
            messageRecycler.smoothScrollToPosition(messageAdapter.getItemCount());
        });
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
                    .build();
            Log.d("CHAT ACTIVITY", String.valueOf(message));
            messageList.add(message);

            messagesCursor.moveToNext();
        }
    }

    private static class getAllMessagesFromChatGrpcTask extends AsyncTask<Object, Void, Iterator<messageResponse>> {
        private final WeakReference<Activity> activityReference;
        private ManagedChannel channel;
        private final MessageAdapter messageAdapter;

        private getAllMessagesFromChatGrpcTask(Activity activity, RecyclerView messageRecycler) {
            this.activityReference = new WeakReference<>(activity);
            this.messageAdapter = (MessageAdapter) messageRecycler.getAdapter();
        }

        @Override
        protected Iterator<messageResponse> doInBackground(Object... params) {
            String host = (String) params[0];
            String portStr = (String) params[1];
            String chatroom = (String) params[2];
            int port = TextUtils.isEmpty(portStr) ? 0 : Integer.parseInt(portStr);
            try {
                channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
                ServerGrpc.ServerBlockingStub stub = ServerGrpc.newBlockingStub(channel);

                chatMessageRequest request = chatMessageRequest.newBuilder()
                        .setChatroom(chatroom)
                        .build();

                return stub.getAllChatMessages(request);
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
        protected void onPostExecute(Iterator<messageResponse> messages) {
            if(messages != null) {

                Activity activity = activityReference.get();
                if (activity == null) {
                    return;
                }

                while (messages.hasNext()) {
                    messageResponse nextMessage = messages.next();
                    messageAdapter.addToMessageList(nextMessage);
                    int position = messageAdapter.getItemCount() - 1;
                    messageAdapter.notifyItemInserted(position);

                    //save message in cache
                    boolean r = ((GlobalVariables) activityReference.get().getApplication()).getDb().insertMessage(
                            nextMessage.getData(),
                            nextMessage.getUsername(),
                            nextMessage.getTimestamp(),
                            String.valueOf(nextMessage.getType()),
                            ((GlobalVariables) activityReference.get().getApplication()).getCurrentChatroomName()
                    );

                    if(r) {
                        Log.d("ChatActivity", "Message response inserted in cache.");
                    } else {
                        Log.d("ChatActivity", "Couldn't insert message in cache.");
                    }
                }

                try {
                    channel.shutdown().awaitTermination(1, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    private static class sendMessageGrpcTask extends AsyncTask<Object, Void, messageResponse> {
        private final WeakReference<Activity> activityReference;
        private ManagedChannel channel;
        private final MessageAdapter messageAdapter;

        private sendMessageGrpcTask(Activity activity, RecyclerView messageRecycler) {
            this.activityReference = new WeakReference<>(activity);
            this.messageAdapter = (MessageAdapter) messageRecycler.getAdapter();
        }

        @Override
        protected messageResponse doInBackground(Object... params) {
            String host = (String) params[0];
            String message = (String) params[1];
            String portStr = (String) params[2];
            int type = (int) params[3];
            int port = TextUtils.isEmpty(portStr) ? 0 : Integer.parseInt(portStr);
            try {
                channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
                ServerGrpc.ServerBlockingStub stub = ServerGrpc.newBlockingStub(channel);

                sendingMessage request = sendingMessage.newBuilder()
                        .setType(type)
                        .setData(message)
                        .setUsername(((GlobalVariables) activityReference.get().getApplication()).getUsername())
                        .setChatroom(((GlobalVariables) activityReference.get().getApplication()).getCurrentChatroomName())
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
            if(result != null) {

                Activity activity = activityReference.get();
                if (activity == null) {
                    return;
                }

                messageAdapter.addToMessageList(result);
                int position = messageAdapter.getItemCount() - 1;
                messageAdapter.notifyItemInserted(position);

                //save message in cache
                boolean r = ((GlobalVariables) activityReference.get().getApplication()).getDb().insertMessage(
                        result.getData(),
                        result.getUsername(),
                        result.getTimestamp(),
                        String.valueOf(result.getType()),
                        result.getChatroom()
                );

                if(r) {
                    Log.d("ChatActivity", "Message response inserted in cache.");
                } else {
                    Log.d("ChatActivity", "Couldn't insert message in cache.");
                }

                Button sendButton = (Button) activity.findViewById(R.id.send_button);
                sendButton.setEnabled(true);

                try {
                    channel.shutdown().awaitTermination(1, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    private static class getRemainingMessagesGrpcTask extends AsyncTask<Object, Void, Iterator<messageResponse>> {
        private final WeakReference<Activity> activityReference;
        private ManagedChannel channel;
        private final MessageAdapter messageAdapter;

        private getRemainingMessagesGrpcTask(Activity activity, RecyclerView messageRecycler) {
            this.activityReference = new WeakReference<>(activity);
            this.messageAdapter = (MessageAdapter) messageRecycler.getAdapter();
        }

        @Override
        protected Iterator<messageResponse> doInBackground(Object... params) {
            String host = (String) params[0];
            String portStr = (String) params[1];
            int position = (int) params[2];
            String chatroom = (String) params[3];
            int port = TextUtils.isEmpty(portStr) ? 0 : Integer.parseInt(portStr);
            try {
                channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
                ServerGrpc.ServerBlockingStub stub = ServerGrpc.newBlockingStub(channel);

                chatMessageFromPosition request = chatMessageFromPosition.newBuilder()
                        .setChatroom(chatroom)
                        .setPositionOfLastMessage(position)
                        .build();

                return stub.getChatMessagesSincePosition(request);
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
        protected void onPostExecute(Iterator<messageResponse> messages) {
            if(messages != null) {

                Activity activity = activityReference.get();
                if (activity == null) {
                    return;
                }

                while (messages.hasNext()) {
                    messageResponse nextMessage = messages.next();
                    messageAdapter.addToMessageList(nextMessage);
                    int position = messageAdapter.getItemCount() - 1;
                    messageAdapter.notifyItemInserted(position);


                    //save message in cache
                    boolean r = ((GlobalVariables) activityReference.get().getApplication()).getDb().insertMessage(
                            nextMessage.getData(),
                            nextMessage.getUsername(),
                            nextMessage.getTimestamp(),
                            String.valueOf(nextMessage.getType()),
                            ((GlobalVariables) activityReference.get().getApplication()).getCurrentChatroomName()
                    );

                    if(r) {
                        Log.d("ChatActivity", "Message response inserted in cache.");
                    } else {
                        Log.d("ChatActivity", "Couldn't insert message in cache.");
                    }
                }

                try {
                    channel.shutdown().awaitTermination(1, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    private static class getLastNMessagesFromChatGrpcTask extends AsyncTask<Object, Void, Iterator<messageResponse>> {
        private final WeakReference<Activity> activityReference;
        private ManagedChannel channel;
        private final MessageAdapter messageAdapter;

        private getLastNMessagesFromChatGrpcTask(Activity activity, RecyclerView messageRecycler) {
            this.activityReference = new WeakReference<>(activity);
            this.messageAdapter = (MessageAdapter) messageRecycler.getAdapter();
        }

        @Override
        protected Iterator<messageResponse> doInBackground(Object... params) {
            String host = (String) params[0];
            String portStr = (String) params[1];
            String chatroom = (String) params[2];
            int port = TextUtils.isEmpty(portStr) ? 0 : Integer.parseInt(portStr);
            try {
                channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
                ServerGrpc.ServerBlockingStub stub = ServerGrpc.newBlockingStub(channel);

                NMessagesFromChat request = NMessagesFromChat.newBuilder()
                        .setChatroom(chatroom)
                        .setNumberOfMessages(10) //10 but this can be variable
                        .build();

                return stub.getLastNMessagesFromChat(request);
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
        protected void onPostExecute(Iterator<messageResponse> messages) {
            if(messages != null) {

                Activity activity = activityReference.get();
                if (activity == null) {
                    return;
                }

                while (messages.hasNext()) {
                    messageResponse nextMessage = messages.next();
                    messageAdapter.addToMessageList(nextMessage);
                    int position = messageAdapter.getItemCount() - 1;
                    messageAdapter.notifyItemInserted(position);


                    //save message in cache
                    boolean r = ((GlobalVariables) activityReference.get().getApplication()).getDb().insertMessage(
                            nextMessage.getData(),
                            nextMessage.getUsername(),
                            nextMessage.getTimestamp(),
                            String.valueOf(nextMessage.getType()),
                            ((GlobalVariables) activityReference.get().getApplication()).getCurrentChatroomName()
                    );

                    if(r) {
                        Log.d("ChatActivity", "Message response inserted in cache.");
                    } else {
                        Log.d("ChatActivity", "Couldn't insert message in cache.");
                    }
                }

                try {
                    channel.shutdown().awaitTermination(1, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
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
            Double latitude = data.getDoubleExtra("latitude", 0.0);
            Double longitude = data.getDoubleExtra("longitude", 0.0);

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
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

}
