package com.example.cmu_project.grpc_tasks;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.example.cmu_project.activities.ChatActivity;
import com.example.cmu_project.activities.JoinChatActivity;
import com.example.cmu_project.helpers.GlobalVariableHelper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.ref.WeakReference;

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

            return stub.joinChat(request);

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

        if(reply != null) {

            Activity activity = activityReference.get();
            if (activity == null) {
                return;
            }

            try {
                //jump to the chat activity
                ((GlobalVariableHelper) activity.getApplication()).setCurrentChatroomName(current_chat_to_join);
                Intent myIntent = new Intent(activity, ChatActivity.class);
                myIntent.putExtra("chatroom",current_chat_to_join);
                activity.startActivity(myIntent);

            } catch (Exception e) {

            }

        }



    }
}
