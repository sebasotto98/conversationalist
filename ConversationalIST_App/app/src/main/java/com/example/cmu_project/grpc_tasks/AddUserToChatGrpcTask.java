package com.example.cmu_project.grpc_tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.cmu_project.helpers.GlobalVariableHelper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.grpc.examples.backendserver.AddUserToChatReply;
import io.grpc.examples.backendserver.AddUserToChatRequest;
import io.grpc.examples.backendserver.ServerGrpc;

public class AddUserToChatGrpcTask extends AsyncTask<Object,Void, AddUserToChatReply> {

    WeakReference<Activity> activityReference;
    List<String> chatMembers;
    private final String userToAdd;

    public AddUserToChatGrpcTask(Activity activity, List<String> chatMembers, String userToAdd) {
        this.activityReference = new WeakReference<>(activity);
        this.chatMembers = chatMembers;
        this.userToAdd = userToAdd;
    }

    @Override
    protected AddUserToChatReply doInBackground(Object... params) {

        String user_to_add = this.userToAdd;
        String chat_name  = (String) params[1];

        try {

            ServerGrpc.ServerBlockingStub stub
                    = ((GlobalVariableHelper) activityReference.get().getApplication())
                    .getServerBlockingStub();

            AddUserToChatRequest request = AddUserToChatRequest.newBuilder().setUserToAdd(user_to_add).setChatroom(chat_name).build();
            return stub.withDeadlineAfter(5, TimeUnit.SECONDS).addUserToChat(request);

        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            pw.flush();
            Log.d("AddUserToChatGrpcTask", sw.toString());
            return null;
        }

    }

    @Override
    protected void onPostExecute(AddUserToChatReply reply) {

        Activity activity = activityReference.get();
        if (activity == null) {
            return;
        }
        if(reply != null) {

            if (reply.getAck().equals("OK")) {
                Toast.makeText(activity.getApplicationContext(), "User added with sucess",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(activity.getApplicationContext(), reply.getAck(),
                        Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(activity.getApplicationContext(), "Error contacting the server",
                    Toast.LENGTH_SHORT).show();
        }

    }

}