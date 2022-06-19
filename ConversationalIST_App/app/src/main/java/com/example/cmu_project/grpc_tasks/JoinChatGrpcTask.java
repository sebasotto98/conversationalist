package com.example.cmu_project.grpc_tasks;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.cmu_project.activities.ChatActivity;
import com.example.cmu_project.activities.ChatroomActivity;
import com.example.cmu_project.activities.JoinChatActivity;
import com.example.cmu_project.helpers.GlobalVariableHelper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import io.grpc.examples.backendserver.JoinChatReply;
import io.grpc.examples.backendserver.JoinChatRequest;
import io.grpc.examples.backendserver.JoinableChatsRequest;
import io.grpc.examples.backendserver.ServerGrpc;


public class JoinChatGrpcTask extends AsyncTask<Object,Void, JoinChatReply> {

    private final WeakReference<Activity> activityReference;
    private String current_chat_to_join;

    public JoinChatGrpcTask(Activity activity,String chat_to_join) {
        this.activityReference = new WeakReference<>(activity);
        this.current_chat_to_join = chat_to_join;
    }

    @Override
    protected JoinChatReply doInBackground(Object... params) {

        String user = (String) params[0];
        String chat_to_join = current_chat_to_join;

        try {
            ServerGrpc.ServerBlockingStub stub
                    = ((GlobalVariableHelper) activityReference.get().getApplication())
                    .getServerBlockingStub();

            JoinChatRequest request = JoinChatRequest.newBuilder().setUser(user).setChatName(chat_to_join).build();

            return stub.withDeadlineAfter(5, TimeUnit.SECONDS).joinChat(request);

        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            pw.flush();
            Log.d("JoinChatGrpcTask", sw.toString());
            return null;
        }

    }

    @Override
    protected void onPostExecute(JoinChatReply reply) {
        Activity activity = activityReference.get();
        if (activity == null) {
            return;
        }

        if(reply == null){
            Toast.makeText(activity.getApplicationContext(), "Error contacting the server",
                    Toast.LENGTH_SHORT).show();
        } else {

            //say to the service that we want to listen a new chat
            Intent intent = new Intent("NEWchat");
            intent.putExtra("chat", current_chat_to_join);
            LocalBroadcastManager.getInstance(activity.getApplicationContext()).sendBroadcast(intent);

            //jump to the chat activity
            ((GlobalVariableHelper) activity.getApplication()).setCurrentChatroomName(current_chat_to_join);
            Intent myIntent = new Intent(activity, ChatActivity.class);
            myIntent.putExtra("chatroom",current_chat_to_join);
            activity.startActivity(myIntent);
        }
    }
}
