package com.example.cmu_project.grpc_tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.cmu_project.helpers.GlobalVariableHelper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import io.grpc.examples.backendserver.RemoveUserReply;
import io.grpc.examples.backendserver.RemoveUserRequest;
import io.grpc.examples.backendserver.ServerGrpc;

public class RemoveUserGrpcTask extends AsyncTask<Object,Void, RemoveUserReply> {

    WeakReference<Activity> activityReference;

    private String user_to_remove;

    public RemoveUserGrpcTask(Activity activity, String user_to_remove) {
        this.activityReference = new WeakReference<>(activity);
        this.user_to_remove = user_to_remove;
    }

    @Override
    protected RemoveUserReply doInBackground(Object... params) {

        String user_to_remove = this.user_to_remove;
        String chat_name = (String) params[0];

        try {

            ServerGrpc.ServerBlockingStub stub
                    = ((GlobalVariableHelper) activityReference.get().getApplication())
                    .getServerBlockingStub();

            RemoveUserRequest request = RemoveUserRequest.newBuilder().setUserToRemove(user_to_remove).setChatName(chat_name).build();
            return stub.withDeadlineAfter(5, TimeUnit.SECONDS).removeUserFromChat(request);

        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            pw.flush();
            Log.d("RemoveUserGrpcTask", sw.toString());
            return null;
        }

    }

    @Override
    protected void onPostExecute(RemoveUserReply reply) {

        Activity activity = activityReference.get();
        if (activity == null) {
            return;
        }
        if (reply != null) {

            if (reply.getAck().equals("OK")) {
                Toast.makeText(activity.getApplicationContext(), "User removed with sucess",
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
