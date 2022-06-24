package com.example.cmu_project.grpc_tasks;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cmu_project.helpers.GlobalVariableHelper;
import com.example.cmu_project.R;
import com.example.cmu_project.adapters.MessageAdapter;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import io.grpc.examples.backendserver.ServerGrpc;
import io.grpc.examples.backendserver.messageResponse;
import io.grpc.examples.backendserver.sendingMessage;

public class SendMessageGrpcTask extends AsyncTask<Object, Void, messageResponse> {
    private final WeakReference<Activity> activityReference;
    private final MessageAdapter messageAdapter;

    public SendMessageGrpcTask(Activity activity, RecyclerView messageRecycler) {
        this.activityReference = new WeakReference<>(activity);
        this.messageAdapter = (MessageAdapter) messageRecycler.getAdapter();

    }

    @Override
    protected messageResponse doInBackground(Object... params) {
        String message = (String) params[0];
        int type = (int) params[1];
        try {
            ServerGrpc.ServerBlockingStub stub
                    = ((GlobalVariableHelper) activityReference.get().getApplication())
                    .getServerBlockingStub();

            sendingMessage request = sendingMessage.newBuilder()
                    .setType(type)
                    .setData(message)
                    .setUsername(((GlobalVariableHelper) activityReference.get().getApplication()).getUsername())
                    .setChatroom(((GlobalVariableHelper) activityReference.get().getApplication()).getCurrentChatroomName())
                    .build();
            return stub.withDeadlineAfter(5, TimeUnit.SECONDS).sendMessage(request);
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            pw.flush();
            Log.d("sendMessageGrpcTask", sw.toString());

            return null;
        }
    }

    @Override
    protected void onPostExecute(messageResponse result) {

        Activity activity = activityReference.get();
        if (activity == null) {
            return;
        }

        if(result == null){
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
        Button sendButton = activity.findViewById(R.id.send_button);
        sendButton.setEnabled(true);

    }
}
