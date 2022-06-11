package com.example.cmu_project.grpc_tasks;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cmu_project.R;
import com.example.cmu_project.adapters.MessageAdapter;
import com.example.cmu_project.enums.MessageType;
import com.example.cmu_project.helpers.GlobalVariableHelper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.ref.WeakReference;
import java.util.Arrays;
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
    private final WeakReference<Context> context;
    private int currentNotificationID;
    private RecyclerView messageRecycler;
    private MessageAdapter messageAdapter;

    public ListenToChatroomsGrpcTask(List<String> userChats, Context context, int currentNotificationID) {
        this.userChats = userChats;
        this.context = new WeakReference<>(context);
        this.currentNotificationID = currentNotificationID;
    }

    @Override
    protected Iterator<messageResponse> doInBackground(Object... params) {
        try {

            ServerGrpc.ServerStub nonBlockingStub
                    = ((GlobalVariableHelper) context.get().getApplicationContext())
                    .getNonBlockingStub();
            StreamObserver<messageResponse> responseMessages = new StreamObserver<messageResponse>() {

                @Override
                public void onNext(messageResponse message) {
                    Log.d("listenToChatroomsGrpcTask", String.valueOf(message));


                    boolean r = ((GlobalVariableHelper) context.get().getApplicationContext()).getDb().insertMessage(
                            message.getData(),
                            message.getUsername(),
                            message.getTimestamp(),
                            String.valueOf(message.getType()),
                            message.getChatroom(),
                            message.getPosition()
                    );

                    if(r) {
                        Log.d("ListenToChatroomsGrpcTask", "Message response inserted in cache.");
                    } else {
                        Log.d("ListenToChatroomsGrpcTask", "Couldn't insert message in cache.");
                    }

                    //TODO -> create Pending intent that opens the chatroom
                    //TODO -> check if chat is open and present the new message instead of sending notification
                     /*Intent notificationIntent = new Intent(this, ChatActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);*/
                    PendingIntent pendingIntent = null;

                    messageRecycler = ((GlobalVariableHelper) context.get().getApplicationContext()).getMessageRecycler();
                    messageAdapter = ((GlobalVariableHelper) context.get().getApplicationContext()).getMessageAdapter();

                    if (messageRecycler != null && messageAdapter != null) {

                        messageAdapter.addToMessageList(message);
                        Log.d("ListenToChatroomsGrpcTask", "1");
                        int position = messageAdapter.getItemCount() - 1;

                        Intent intent = new Intent(((GlobalVariableHelper)context.get().getApplicationContext())
                                .getBROADCAST_MESSAGE_INSERTED());
                        intent.putExtra("position", position);
                        Log.d("ListenToChatroomsGrpcTask", "2");
                        LocalBroadcastManager.getInstance(context.get()).sendBroadcast(intent);
                        Log.d("ListenToChatroomsGrpcTask", "3");
                    } else {
                        if (message.getType() == MessageType.TEXT.getValue()) {
                            pushNotification(message.getChatroom(),
                                    message.getUsername() + ": " + message.getData(), pendingIntent);
                        } else if (message.getType() == MessageType.PHOTO.getValue()) {
                            pushNotification(message.getChatroom(),
                                    message.getUsername() + " sent a photo", pendingIntent);
                        } else if (message.getType() == MessageType.GEOLOCATION.getValue()) {
                            pushNotification(message.getChatroom(),
                                    message.getUsername() + " sent a geolocation", pendingIntent);
                        }
                    }
                    Log.d("ListenToChatroomsGrpcTask", "4");
                }

                @Override
                public void onError(Throwable t) {
                    Log.d("listenToChatroomsGrpcTask", Arrays.toString(t.getStackTrace()));
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
            Log.d("listenToChatroomsGrpcTask", sw.toString());
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

        builder = new NotificationCompat.Builder(context.get(), CHANNEL_ID)
                .setContentTitle(Title)
                .setContentText(Text)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ist_logo)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if(intent != null) {
            builder.setContentIntent(intent);
        }
        NotificationManagerCompat manager = NotificationManagerCompat.from(context.get());

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
            NotificationManager notificationManager = context.get().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
