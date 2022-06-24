package com.example.cmu_project.grpc_tasks;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.cmu_project.activities.ChatroomActivity;
import com.example.cmu_project.helpers.CryptographyHelper;
import com.example.cmu_project.helpers.GlobalVariableHelper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import io.grpc.examples.backendserver.ServerGrpc;
import io.grpc.examples.backendserver.UpgradeAccountReply;
import io.grpc.examples.backendserver.UpgradeAccountRequest;
import io.grpc.examples.backendserver.empty_message;


public class UpgradeAccountGrpcTask extends AsyncTask<Object,Void, UpgradeAccountReply> {

    WeakReference<Activity> activityReference;
    String new_user;

    public UpgradeAccountGrpcTask(Activity activity,String new_user) {
        this.activityReference = new WeakReference<>(activity);
        this.new_user = new_user;
    }

    @Override
    protected UpgradeAccountReply doInBackground(Object... params) {

        String guest_user = (String) params[0];
        String new_user = this.new_user;
        String password = (String) params[1];

        try {
            ServerGrpc.ServerBlockingStub stub = ((GlobalVariableHelper) activityReference.get().getApplication()).getServerBlockingStub();

            String pass_hashed = CryptographyHelper.hashString(password);

            UpgradeAccountRequest request = UpgradeAccountRequest.newBuilder().setGuestUser(guest_user).setNewUser(new_user).setPassword(pass_hashed).build();
            return stub.withDeadlineAfter(5, TimeUnit.SECONDS).upgradeAccount(request);

        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            pw.flush();
            Log.d("UpgradeAccountGrpcTask", sw.toString());
            return null;
        }

    }

    @Override
    protected void onPostExecute(UpgradeAccountReply reply) {

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
                ((GlobalVariableHelper) activity.getApplication()).setUsername(this.new_user);

                //jump to the chatRoom activity
                Intent myIntent = new Intent(activity, ChatroomActivity.class);
                myIntent.putExtra("isGuest",false);
                myIntent.putExtra("username",this.new_user);
                activity.startActivity(myIntent);



            } else if (reply.getAck().equals("Username already in use")){
                Toast.makeText(activity.getApplicationContext(), reply.getAck(),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(activity.getApplicationContext(), "Some error happened",
                        Toast.LENGTH_SHORT).show();
            }

        }

    }


}
