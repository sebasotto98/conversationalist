package com.example.cmu_project.grpc_tasks;


import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cmu_project.activities.ChatActivity;
import com.example.cmu_project.adapters.MessageAdapter;
import com.example.cmu_project.enums.MessageType;
import com.example.cmu_project.helpers.GlobalVariableHelper;
import com.example.cmu_project.helpers.NotificationsHelper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import io.grpc.examples.backendserver.*;
import io.grpc.stub.StreamObserver;

public class ListenToChatroomsGrpcTask extends AsyncTask<Object, Void, Iterator<messageResponse>> {

    private final List<String> userChats;
    private StreamObserver<listenToChatroom> requestObserver;
    private final WeakReference<Context> context;
    private final boolean isMobileData;
    private final NotificationsHelper notificationsHelper;

    public ListenToChatroomsGrpcTask(List<String> userChats, Context context, boolean isMobileData) {
        this.userChats = userChats;
        this.context = new WeakReference<>(context);
        this.isMobileData = isMobileData;
        notificationsHelper = ((GlobalVariableHelper) this.context.get().getApplicationContext()).getNotificationsHelper();
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

                    Intent notificationIntent = new Intent(context.get().getApplicationContext(),
                            ChatActivity.class);
                    Log.d("ListenToChatroomsGrpcTask", message.getChatroom());
                    notificationIntent.putExtra("chatroom", message.getChatroom());
                    notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    PendingIntent pendingIntent = PendingIntent.getActivity(
                            context.get().getApplicationContext(),
                            (int)(System.currentTimeMillis()/1000),
                            notificationIntent,
                            PendingIntent.FLAG_IMMUTABLE);

                    //PendingIntent pendingIntent = null;

                    RecyclerView messageRecycler = ((GlobalVariableHelper) context.get().getApplicationContext()).getMessageRecycler();
                    MessageAdapter messageAdapter = ((GlobalVariableHelper) context.get().getApplicationContext()).getMessageAdapter();
                    String currentChatroom = ((GlobalVariableHelper) context.get().getApplicationContext())
                            .getCurrentChatroomName();

                    //if message is for current opened chatroom
                    if (messageRecycler != null && messageAdapter != null && Objects.equals(currentChatroom, message.getChatroom())) {

                        messageAdapter.addToMessageList(message);

                        int position = messageAdapter.getItemCount() - 1;

                        Intent intent = new Intent("new_message_in_adapter");
                        intent.putExtra("position", position);

                        LocalBroadcastManager.getInstance(context.get()).sendBroadcast(intent);

                    } else {
                        if (message.getType() == MessageType.TEXT.getValue()) {
                            notificationsHelper.pushNotification(message.getChatroom(),
                                    message.getUsername() + ": " + message.getData(), pendingIntent);
                        } else if (message.getType() == MessageType.PHOTO.getValue()) {
                            notificationsHelper.pushNotification(message.getChatroom(),
                                    message.getUsername() + " sent a photo", pendingIntent);
                        } else if (message.getType() == MessageType.GEOLOCATION.getValue()) {
                            notificationsHelper.pushNotification(message.getChatroom(),
                                    message.getUsername() + " sent a geolocation", pendingIntent);
                        }
                    }
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

            if(isMobileData){
                requestObserver = nonBlockingStub.listenToChatroomsMobileData(responseMessages);
            } else {
                requestObserver = nonBlockingStub.listenToChatrooms(responseMessages);
            }
            Log.d("listenToChatroomsGrpcTask", String.valueOf(userChats));
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

    public void complete(){
        requestObserver.onCompleted();
    }

    public void listenNewChat(String chat){
        listenToChatroom newChat = listenToChatroom.newBuilder()
                .setChatroom(chat)
                .build();
        requestObserver.onNext(newChat);
    }

}
