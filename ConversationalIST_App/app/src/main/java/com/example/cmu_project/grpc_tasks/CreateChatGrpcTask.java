package com.example.cmu_project.grpc_tasks;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.cmu_project.activities.ChatActivity;
import com.example.cmu_project.enums.ChatType;
import com.example.cmu_project.helpers.GlobalVariableHelper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import io.grpc.examples.backendserver.CreateChatReply;
import io.grpc.examples.backendserver.CreateChatRequest;
import io.grpc.examples.backendserver.Location;
import io.grpc.examples.backendserver.ServerGrpc;

public class CreateChatGrpcTask extends AsyncTask<Object,Void, CreateChatReply> {

    WeakReference<Activity> activityReference;
    String newChatName;
    String chatLatitude;
    String chatLongitude;
    String radius;

    public CreateChatGrpcTask(Activity activity,String newChatName) {
        this.activityReference = new WeakReference<>(activity);
        this.newChatName = newChatName;
    }

    public CreateChatGrpcTask(Activity activity, String newChatName, String chatLatitude, String chatLongitude, String radius) {
        this.activityReference = new WeakReference<>(activity);
        this.newChatName = newChatName;
        this.chatLatitude = chatLatitude;
        this.chatLongitude = chatLongitude;
        this.radius = radius;
    }

    @Override
    protected CreateChatReply doInBackground(Object... params) {

        String user = (String) params[0];
        String type_of_chat = (String) params[1];



        try {
            ServerGrpc.ServerBlockingStub stub
                    = ((GlobalVariableHelper) activityReference.get().getApplication())
                    .getServerBlockingStub();

            CreateChatRequest request;

            if(type_of_chat.equalsIgnoreCase(ChatType.GEOFENCED.name())) {
                Location location = Location.newBuilder().setLatitude(chatLatitude).setLongitude(chatLongitude).build();
                request = CreateChatRequest.newBuilder().setChatroomName(newChatName).setUser(user).setTypeOfChat(type_of_chat).setLocation(location).setRadius(radius).build();
            } else {
                request = CreateChatRequest.newBuilder().setChatroomName(newChatName).setUser(user).setTypeOfChat(type_of_chat).build();
            }


            return stub.withDeadlineAfter(5, TimeUnit.SECONDS).createChat(request);

        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            pw.flush();
            Log.d("CreateChatGrpcTask", sw.toString());
            return null;
        }

    }

    @Override
    protected void onPostExecute(CreateChatReply reply) {

        Activity activity = activityReference.get();
        if (activity == null) {
            return;
        }
        if(reply != null) {

            //say to the service that we want to listen a new chat
            Intent intent = new Intent("NEWchat");
            intent.putExtra("chat", newChatName);
            LocalBroadcastManager.getInstance(activity.getApplicationContext()).sendBroadcast(intent);

            //jump to the chat activity
            ((GlobalVariableHelper) activity.getApplication()).setCurrentChatroomName(newChatName);
            Intent myIntent = new Intent(activity, ChatActivity.class);
            myIntent.putExtra("chatroom", newChatName);
            activity.startActivity(myIntent);
        } else {
            Toast.makeText(activity.getApplicationContext(), "Error contacting the server",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
