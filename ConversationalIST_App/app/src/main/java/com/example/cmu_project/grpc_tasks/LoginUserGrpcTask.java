package com.example.cmu_project.grpc_tasks;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.cmu_project.activities.ChatActivity;
import com.example.cmu_project.activities.ChatroomActivity;
import com.example.cmu_project.helpers.CryptographyHelper;
import com.example.cmu_project.helpers.GlobalVariableHelper;
import com.example.cmu_project.helpers.LinkHelper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.ref.WeakReference;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import io.grpc.examples.backendserver.ServerGrpc;
import io.grpc.examples.backendserver.loginUserReply;
import io.grpc.examples.backendserver.loginUserRequest;
import io.grpc.examples.backendserver.registerUserReply;

public class LoginUserGrpcTask extends AsyncTask<Object,Void, loginUserReply> {

    WeakReference<Activity> activityReference;
    LinkHelper linkHelper;
    String user;

    public LoginUserGrpcTask(Activity activity,LinkHelper linkHelper,String user) {
        this.activityReference = new WeakReference<>(activity);
        this.linkHelper = linkHelper;
        this.user = user;
    }


    @Override
    protected loginUserReply doInBackground(Object... params) {

        String user_name = this.user;
        String password = (String) params[0];

        String password_hashed = null;

        try {
            password_hashed  = CryptographyHelper.hash_string(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        try {
            ServerGrpc.ServerBlockingStub stub = ((GlobalVariableHelper) activityReference.get().getApplication()).getServerBlockingStub();

            loginUserRequest request = loginUserRequest.newBuilder().setUser(user_name).setPasswordHash(password_hashed).build();

            return stub.withDeadlineAfter(5, TimeUnit.SECONDS).loginUser(request);


        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            pw.flush();
            Log.d("LoginUserGrpcTask", sw.toString());
            return null;
        }



    }

    @Override
    protected void onPostExecute(loginUserReply reply) {

        Activity activity = activityReference.get();
        if (activity == null) {
            return;
        }

        if(reply == null){
            Toast.makeText(activity.getApplicationContext(), "Error contacting the server",
                    Toast.LENGTH_SHORT).show();
        } else {

            if(reply.getAck().equals("OK")) {

                Toast.makeText(activity.getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();

                ((GlobalVariableHelper) activity.getApplication()).setUsername(this.user);

                if(linkHelper.getFlag()) {

                    String chat_to_join = linkHelper.getChat_to_go();
                    linkHelper.setToEmpty();
                    new JoinChatGrpcTask(activity,chat_to_join).execute(user);

                } else {

                    //jump to the chatRoom activity
                    Intent myIntent = new Intent(activity, ChatroomActivity.class);
                    myIntent.putExtra("username",user);
                    activity.startActivity(myIntent);

                }


            } else {
                Toast.makeText(activity.getApplicationContext(), reply.getAck(),
                        Toast.LENGTH_SHORT).show();
            }


        }

    }


}
