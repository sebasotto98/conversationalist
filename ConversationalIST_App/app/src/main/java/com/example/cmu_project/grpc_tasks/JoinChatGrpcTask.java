package com.example.cmu_project.grpc_tasks;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.cmu_project.activities.ChatActivity;
import com.example.cmu_project.helpers.GlobalVariableHelper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import io.grpc.examples.backendserver.JoinChatReply;
import io.grpc.examples.backendserver.JoinChatRequest;
import io.grpc.examples.backendserver.ServerGrpc;

public class JoinChatGrpcTask extends AsyncTask<Object,Void, JoinChatReply> {

    private final WeakReference<Activity> activityReference;
    private final String currentChatToJoin;

    public JoinChatGrpcTask(Activity activity, String currentChatToJoin) {
        this.activityReference = new WeakReference<>(activity);
        this.currentChatToJoin = currentChatToJoin;
    }

    @Override
    protected JoinChatReply doInBackground(Object... params) {

        String user = (String) params[0];

        try {
            ServerGrpc.ServerBlockingStub stub
                    = ((GlobalVariableHelper) activityReference.get().getApplication())
                    .getServerBlockingStub();

            JoinChatRequest request = JoinChatRequest.newBuilder().setUser(user).setChatName(currentChatToJoin).build();

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
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activityReference.get().getApplicationContext());
            String language = prefs.getString("language", "English");

            if (language.equals("Português")) {
                Toast.makeText(activityReference.get().getApplicationContext(), "Erro a contactar o servidor",
                        Toast.LENGTH_SHORT).show();
            } else if (language.equals("English")) {
                Toast.makeText(activityReference.get().getApplicationContext(), "Error contacting the server",
                        Toast.LENGTH_SHORT).show();
            }
        } else {

            //say to the service that we want to listen a new chat
            Intent intent = new Intent("NEWchat");
            intent.putExtra("chat", currentChatToJoin);
            LocalBroadcastManager.getInstance(activity.getApplicationContext()).sendBroadcast(intent);

            //jump to the chat activity
            ((GlobalVariableHelper) activity.getApplication()).setCurrentChatroomName(currentChatToJoin);
            Intent myIntent = new Intent(activity, ChatActivity.class);
            myIntent.putExtra("chatroom", currentChatToJoin);
            activity.startActivity(myIntent);
        }
    }
}
