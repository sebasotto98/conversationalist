package com.example.cmu_project.grpc_tasks;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.cmu_project.activities.ChatroomActivity;
import com.example.cmu_project.helpers.GlobalVariableHelper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.ref.WeakReference;

import io.grpc.examples.backendserver.ServerGrpc;
import io.grpc.examples.backendserver.registerUserReply;
import io.grpc.examples.backendserver.registerUserRequest;

public class RegisterUserGrpcTask extends AsyncTask<Object,Void, registerUserReply> {

    WeakReference<Activity> activityReference;
    String new_user;


    public RegisterUserGrpcTask(Activity activity,String new_user) {
        this.activityReference = new WeakReference<>(activity);
        this.new_user = new_user;
    }

    @Override
    protected registerUserReply doInBackground(Object... params) {

        try {

            ServerGrpc.ServerBlockingStub stub = ((GlobalVariableHelper) activityReference.get().getApplication()).getServerBlockingStub();
            registerUserRequest request = registerUserRequest.newBuilder().setUser(new_user).build();

            return stub.registerUser(request);

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

        if (reply != null) {

            Activity activity = activityReference.get();
            if (activity == null) {
                return;
            }

            try {

                Toast.makeText(activity.getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();

                //jump to the chat activity
                ((GlobalVariableHelper) activity.getApplication()).setUsername(new_user);
                Intent myIntent = new Intent(activity, ChatroomActivity.class);
                myIntent.putExtra("username",new_user);
                activity.startActivity(myIntent);


            } catch (Exception e) {

            }


        }

    }



}
