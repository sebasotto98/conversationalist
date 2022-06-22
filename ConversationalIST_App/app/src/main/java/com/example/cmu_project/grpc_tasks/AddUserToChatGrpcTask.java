package com.example.cmu_project.grpc_tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
    List<String> chat_members;
    private String user_to_add;

    public AddUserToChatGrpcTask(Activity activity, List<String> chat_members,String user_to_add) {
        this.activityReference = new WeakReference<>(activity);
        this.chat_members = chat_members;
        this.user_to_add = user_to_add;

    }

    @Override
    protected AddUserToChatReply doInBackground(Object... params) {

        String user_to_add = this.user_to_add;
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
               return;
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
