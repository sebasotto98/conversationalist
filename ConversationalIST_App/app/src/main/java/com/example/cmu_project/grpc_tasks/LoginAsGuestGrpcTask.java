package com.example.cmu_project.grpc_tasks;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.example.cmu_project.activities.ChatroomActivity;
import com.example.cmu_project.helpers.GlobalVariableHelper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import io.grpc.examples.backendserver.ServerGrpc;
import io.grpc.examples.backendserver.empty_message;
import io.grpc.examples.backendserver.loginGuestReply;
import io.grpc.examples.backendserver.loginUserReply;
import io.grpc.examples.backendserver.loginUserRequest;


public class LoginAsGuestGrpcTask extends AsyncTask<Object,Void, loginGuestReply> {

    WeakReference<Activity> activityReference;

    public LoginAsGuestGrpcTask(Activity activity) {
        this.activityReference = new WeakReference<>(activity);
    }

    @Override
    protected loginGuestReply doInBackground(Object... params) {

        try {
            ServerGrpc.ServerBlockingStub stub = ((GlobalVariableHelper) activityReference.get().getApplication()).getServerBlockingStub();

            empty_message request = empty_message.newBuilder().build();
            return stub.withDeadlineAfter(5, TimeUnit.SECONDS).loginGuest(request);

        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            pw.flush();
            Log.d("LoginAsGuestGrpcTask", sw.toString());
            return null;
        }

    }

    @Override
    protected void onPostExecute(loginGuestReply reply) {

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

            if (reply.getAck().equals("OK")) {

                String guest_number = reply.getGuestNumber();

                //jump to the chatRoom activity
                Intent myIntent = new Intent(activity, ChatroomActivity.class);
                myIntent.putExtra("isGuest",true);
                myIntent.putExtra("username","Guest_"+guest_number);
                activity.startActivity(myIntent);

            }else {
                Toast.makeText(activity.getApplicationContext(), "Something went wrong in the server",
                        Toast.LENGTH_SHORT).show();
            }


        }


    }



}
