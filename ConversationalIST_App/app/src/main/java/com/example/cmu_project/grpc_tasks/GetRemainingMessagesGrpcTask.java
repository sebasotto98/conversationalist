package com.example.cmu_project.grpc_tasks;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cmu_project.activities.ChatActivity;
import com.example.cmu_project.enums.MessageType;
import com.example.cmu_project.helpers.GlobalVariableHelper;
import com.example.cmu_project.adapters.MessageAdapter;
import com.example.cmu_project.helpers.NotificationsHelper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.grpc.examples.backendserver.ServerGrpc;
import io.grpc.examples.backendserver.chatMessageFromPosition;
import io.grpc.examples.backendserver.messageResponse;

public class GetRemainingMessagesGrpcTask extends AsyncTask<Object, Void, Iterator<messageResponse>> {
    private final WeakReference<Context> context;
    private final NotificationsHelper notificationsHelper;

    public GetRemainingMessagesGrpcTask(Context context) {
        this.context = new WeakReference<>(context);
        notificationsHelper = ((GlobalVariableHelper) this.context.get().getApplicationContext()).getNotificationsHelper();
    }

    @Override
    protected Iterator<messageResponse> doInBackground(Object... params) {
        int position = (int) params[0];
        String chatroom = (String) params[1];
        try {
            ServerGrpc.ServerBlockingStub stub
                    = ((GlobalVariableHelper) context.get().getApplicationContext())
                    .getServerBlockingStub();

            chatMessageFromPosition request = chatMessageFromPosition.newBuilder()
                    .setChatroom(chatroom)
                    .setPositionOfLastMessage(position)
                    .build();

            return stub.withDeadlineAfter(5, TimeUnit.SECONDS).getChatMessagesSincePosition(request);
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            pw.flush();
            Log.d("getRemainingMessagesGrpcTask", sw.toString());
            return null;
        }
    }

    @Override
    protected void onPostExecute(Iterator<messageResponse> messages) {

        if(messages != null) {

            while (messages.hasNext()) {
                messageResponse nextMessage = messages.next();

                //save message in cache
                boolean r = ((GlobalVariableHelper) context.get().getApplicationContext()).getDb().insertMessage(
                        nextMessage.getData(),
                        nextMessage.getUsername(),
                        nextMessage.getTimestamp(),
                        String.valueOf(nextMessage.getType()),
                        nextMessage.getChatroom(),
                        nextMessage.getPosition()
                );

                if(r) {
                    Log.d("getRemainingMessagesGrpcTask", "Message response inserted in cache.");
                } else {
                    Log.d("getRemainingMessagesGrpcTask", "Couldn't insert message in cache.");
                }

                Intent notificationIntent = new Intent(context.get().getApplicationContext(),
                        ChatActivity.class);
                Log.d("GetRemainingMessagesGrpcTask", nextMessage.getChatroom());
                notificationIntent.putExtra("chatroom", nextMessage.getChatroom());
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
                if (messageRecycler != null && messageAdapter != null && Objects.equals(currentChatroom, nextMessage.getChatroom())) {

                    messageAdapter.addToMessageList(nextMessage);

                    int p = messageAdapter.getItemCount() - 1;

                    Intent intent = new Intent("new_message_in_adapter");
                    intent.putExtra("position", p);

                    LocalBroadcastManager.getInstance(context.get()).sendBroadcast(intent);

                } else {
                    if (nextMessage.getType() == MessageType.TEXT.getValue()) {
                        notificationsHelper.pushNotification(nextMessage.getChatroom(),
                                nextMessage.getUsername() + ": " + nextMessage.getData(), pendingIntent);
                    } else if (nextMessage.getType() == MessageType.PHOTO.getValue()) {
                        notificationsHelper.pushNotification(nextMessage.getChatroom(),
                                nextMessage.getUsername() + " sent a photo", pendingIntent);
                    } else if (nextMessage.getType() == MessageType.GEOLOCATION.getValue()) {
                        notificationsHelper.pushNotification(nextMessage.getChatroom(),
                                nextMessage.getUsername() + " sent a geolocation", pendingIntent);
                    }
                }
            }
        } else {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context.get().getApplicationContext());
            String language = prefs.getString("language", "English");

            if (language.equals("Português")) {
                Toast.makeText(context.get().getApplicationContext(), "Erro a contactar o servidor",
                        Toast.LENGTH_SHORT).show();
            } else if (language.equals("English")) {
                Toast.makeText(context.get().getApplicationContext(), "Error contacting the server",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}
