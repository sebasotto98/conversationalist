package com.example.cmu_project.grpc_tasks;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
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

    private final String userToRemove;

    public RemoveUserGrpcTask(Activity activity, String userToRemove) {
        this.activityReference = new WeakReference<>(activity);
        this.userToRemove = userToRemove;
    }

    @Override
    protected RemoveUserReply doInBackground(Object... params) {

        String chat_name = (String) params[0];

        try {

            ServerGrpc.ServerBlockingStub stub
                    = ((GlobalVariableHelper) activityReference.get().getApplication())
                    .getServerBlockingStub();

            RemoveUserRequest request = RemoveUserRequest.newBuilder().setUserToRemove(this.userToRemove).setChatName(chat_name).build();
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
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
                String language = prefs.getString("language", "English");

                if (language.equals("Português")) {
                    Toast.makeText(activity.getApplicationContext(), "Utilizador removido com sucesso",
                            Toast.LENGTH_SHORT).show();
                } else if (language.equals("English")) {
                    Toast.makeText(activity.getApplicationContext(), "User removed with sucess",
                            Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(activity.getApplicationContext(), reply.getAck(),
                        Toast.LENGTH_SHORT).show();
            }

        } else {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activityReference.get().getApplicationContext());
            String language = prefs.getString("language", "English");

            if (language.equals("Português")) {
                Toast.makeText(activityReference.get().getApplicationContext(), "Erro a contactar o servidor",
                        Toast.LENGTH_SHORT).show();
            } else if (language.equals("English")) {
                Toast.makeText(activityReference.get().getApplicationContext(), "Error contacting the server",
                        Toast.LENGTH_SHORT).show();
            }
        }

    }

}
