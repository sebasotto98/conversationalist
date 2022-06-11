package com.example.cmu_project.grpc_tasks;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.cmu_project.R;
import com.example.cmu_project.enums.MessageType;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.examples.backendserver.*;
import io.grpc.stub.StreamObserver;

public class ListenToChatroomsGrpcTask extends AsyncTask<Object, Void, Iterator<messageResponse>> {

    private final String CHANNEL_ID = "ConversationalIST notification";
    private final List<String> userChats;
    private StreamObserver<listenToChatroom> requestObserver;
    private final Context context;
    private int currentNotificationID;

    public ListenToChatroomsGrpcTask(List<String> userChats, Context context, int currentNotificationID) {
        this.userChats = userChats;
        this.context = context;
        this.currentNotificationID = currentNotificationID;
    }

    @Override
    protected Iterator<messageResponse> doInBackground(Object... params) {
        String host = (String) params[0];
        String portStr = (String) params[1];
        int port = TextUtils.isEmpty(portStr) ? 0 : Integer.parseInt(portStr);
        try {
            ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
            ServerGrpc.ServerStub nonBlockingStub = ServerGrpc.newStub(channel);
            StreamObserver<messageResponse> responseMessages = new StreamObserver<messageResponse>() {

                @Override
                public void onNext(messageResponse message) {
                    Log.d("listenToChatroomsGrpcTask", String.valueOf(message));
                    //TODO -> create Pending intent that opens the chatroom
                     /*Intent notificationIntent = new Intent(this, ChatActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);*/
                    PendingIntent pendingIntent = null;
                    if(message.getType() == MessageType.TEXT.getValue()){
                        pushNotification(message.getChatroom(),
                                message.getUsername() + ": " + message.getData(),pendingIntent);
                    } else if(message.getType() == MessageType.PHOTO.getValue()){
                        pushNotification(message.getChatroom(),
                                message.getUsername() + " sent a photo",pendingIntent);
                    } else if(message.getType() == MessageType.GEOLOCATION.getValue()){
                        pushNotification(message.getChatroom(),
                                message.getUsername() + " sent a geolocation",pendingIntent);
                    }

                }

                @Override
                public void onError(Throwable t) {
                    Log.d("listenToChatroomsGrpcTask", t.getMessage());
                }

                @Override
                public void onCompleted() {
                    Log.d("listenToChatroomsGrpcTask", "Finished receiving messages");
                }
            };

            requestObserver = nonBlockingStub.listenToChatrooms(responseMessages);

            for(String chat: userChats){
                listenToChatroom newChat = listenToChatroom.newBuilder()
                        .setChatroom(chat)
                        .build();

                requestObserver.onNext(newChat);
            }

        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            pw.flush();
            Log.d("getRemainingMessagesMobileDataGrpcTask", sw.toString());
        }
        return null;
    }

    public int complete(){
        requestObserver.onCompleted();
        return currentNotificationID;
    }

    private void pushNotification(String Title, String Text, PendingIntent intent){
        NotificationCompat.Builder builder;

        createNotificationChannel();

        builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(Title)
                .setContentText(Text)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ist_logo)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if(intent != null) {
            builder.setContentIntent(intent);
        }
        NotificationManagerCompat manager = NotificationManagerCompat.from(context);

        manager.notify(currentNotificationID, builder.build());
        currentNotificationID++;

        Log.d("listenToChatroomsGrpcTask", "Notification Push");
    }

    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "ConversationalIST channel";
            String description = "App channel for notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}