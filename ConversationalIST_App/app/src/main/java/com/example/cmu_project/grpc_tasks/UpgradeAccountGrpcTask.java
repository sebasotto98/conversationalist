package com.example.cmu_project.grpc_tasks;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
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

public class UpgradeAccountGrpcTask extends AsyncTask<Object,Void, UpgradeAccountReply> {

    WeakReference<Activity> activityReference;
    String newUser;

    public UpgradeAccountGrpcTask(Activity activity,String newUser) {
        this.activityReference = new WeakReference<>(activity);
        this.newUser = newUser;
    }

    @Override
    protected UpgradeAccountReply doInBackground(Object... params) {

        String guest_user = (String) params[0];
        String new_user = this.newUser;
        String password = (String) params[1];

        try {
            ServerGrpc.ServerBlockingStub stub = ((GlobalVariableHelper) activityReference.get().getApplication()).getServerBlockingStub();

            String pass_hashed = CryptographyHelper.hashString(password);

            UpgradeAccountRequest request = UpgradeAccountRequest.newBuilder().setGuestUser(guest_user).setNewUser(new_user).setPassword(pass_hashed).build();
            return stub.withDeadlineAfter(10, TimeUnit.SECONDS).upgradeAccount(request);

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

            if(reply.getAck().equals("OK")) {


                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
                String language = prefs.getString("language", "English");
                if (language.equals("Português")) {
                    Toast.makeText(activity.getApplicationContext(), "A Redirecionar...", Toast.LENGTH_SHORT).show();
                } else if (language.equals("English")) {
                    Toast.makeText(activity.getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
                }
                ((GlobalVariableHelper) activity.getApplication()).setUsername(this.newUser);

                //jump to the chatRoom activity
                Intent myIntent = new Intent(activity, ChatroomActivity.class);
                myIntent.putExtra("isGuest",false);
                myIntent.putExtra("username",this.newUser);
                activity.startActivity(myIntent);



            } else if (reply.getAck().equals("Username already in use")){
                Toast.makeText(activity.getApplicationContext(), reply.getAck(),
                        Toast.LENGTH_SHORT).show();
            } else {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
                String language = prefs.getString("language", "English");

                if (language.equals("Português")) {
                    Toast.makeText(activity.getApplicationContext(), "Ocurreu um erro",
                            Toast.LENGTH_SHORT).show();
                } else if (language.equals("English")) {
                    Toast.makeText(activity.getApplicationContext(), "Some error happened",
                            Toast.LENGTH_SHORT).show();
                }

            }

        }

    }


}
