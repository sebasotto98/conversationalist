package com.example.cmu_project.grpc_tasks;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.cmu_project.R;
import com.example.cmu_project.activities.ChatroomActivity;
import com.example.cmu_project.helpers.GlobalVariableHelper;
import com.example.cmu_project.helpers.LinkHelper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import io.grpc.examples.backendserver.ServerGrpc;
import io.grpc.examples.backendserver.registerUserReply;
import io.grpc.examples.backendserver.registerUserRequest;

public class RegisterUserGrpcTask extends AsyncTask<Object,Void, registerUserReply> {

    WeakReference<Activity> activityReference;
    String new_user;
    LinkHelper linkHelper;

    public RegisterUserGrpcTask(Activity activity, String new_user, LinkHelper linkHelper) {
        this.activityReference = new WeakReference<>(activity);
        this.new_user = new_user;
        this.linkHelper = linkHelper;
    }

    @Override
    protected registerUserReply doInBackground(Object... params) {

        try {

            ServerGrpc.ServerBlockingStub stub = ((GlobalVariableHelper) activityReference.get().getApplication()).getServerBlockingStub();
            registerUserRequest request = registerUserRequest.newBuilder().setUser(new_user).build();

            return stub.withDeadlineAfter(5, TimeUnit.SECONDS).registerUser(request);

        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            pw.flush();
            Log.d("RegisterUserGrpcTask", sw.toString());
            return null;
        }


    }

    @Override
    protected void onPostExecute(registerUserReply reply) {
        Activity activity = activityReference.get();
        if (activity == null) {
            return;
        }

        if(reply == null){
            Toast.makeText(activity.getApplicationContext(), "Error contacting the server",
                    Toast.LENGTH_SHORT).show();
        } else {

            Toast.makeText(activity.getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();

            ((GlobalVariableHelper) activity.getApplication()).setUsername(new_user);

            if(linkHelper.getFlag()) {

                String chat_to_join = linkHelper.getChat_to_go();
                linkHelper.setToEmpty();
                new JoinChatGrpcTask(activity,chat_to_join).execute(new_user);

            } else {

                //jump to the chatRoom activity
                Intent myIntent = new Intent(activity, ChatroomActivity.class);
                myIntent.putExtra("username",new_user);
                activity.startActivity(myIntent);

            }


        }
    }
}
