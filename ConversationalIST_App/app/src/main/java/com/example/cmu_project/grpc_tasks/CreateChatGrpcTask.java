package com.example.cmu_project.grpc_tasks;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.cmu_project.activities.ChatActivity;
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
    String new_chat_name;

    public CreateChatGrpcTask(Activity activity,String new_chat_name) {
        this.activityReference = new WeakReference<>(activity);
        this.new_chat_name = new_chat_name;
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

            if(type_of_chat.equals("GeoFanced")) {
                Location location = Location.newBuilder().setLatitude("11.11.11").setLongitude("22.22.22").build();
                request = CreateChatRequest.newBuilder().setChatroomName(new_chat_name).setUser(user).setTypeOfChat(type_of_chat).setLocation(location).setRadius("10").build();
            } else {
                request = CreateChatRequest.newBuilder().setChatroomName(new_chat_name).setUser(user).setTypeOfChat(type_of_chat).build();
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

            //jump to the chat activity
            ((GlobalVariableHelper) activity.getApplication()).setCurrentChatroomName(new_chat_name);
            Intent myIntent = new Intent(activity, ChatActivity.class);
            myIntent.putExtra("chatroom",new_chat_name);
            activity.startActivity(myIntent);
        } else {
            Toast.makeText(activity.getApplicationContext(), "Error contacting the server",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
