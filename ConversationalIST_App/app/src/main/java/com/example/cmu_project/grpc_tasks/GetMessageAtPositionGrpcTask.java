package com.example.cmu_project.grpc_tasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.example.cmu_project.adapters.MessageAdapter;
import com.example.cmu_project.helpers.GlobalVariableHelper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import io.grpc.examples.backendserver.ServerGrpc;
import io.grpc.examples.backendserver.getMessagePosition;
import io.grpc.examples.backendserver.messageResponse;

public class GetMessageAtPositionGrpcTask extends AsyncTask<Object, Void, messageResponse> {
    private final WeakReference<Context> context;
    private final MessageAdapter messageAdapter;

    public GetMessageAtPositionGrpcTask(Context context, MessageAdapter messageAdapter){
        this.context = new WeakReference<>(context);
        this.messageAdapter = messageAdapter;
    }

    @Override
    protected messageResponse doInBackground(Object... objects) {
        try {
            int position = (int) objects[0];

            String chatroom = (String) objects[1];
            ServerGrpc.ServerBlockingStub stub
                    = ((GlobalVariableHelper) context.get().getApplicationContext())
                    .getServerBlockingStub();

            Log.d("GetMessageAtPositionGrpcTask", String.valueOf(position));
            Log.d("GetMessageAtPositionGrpcTask", chatroom);

            getMessagePosition request = getMessagePosition.newBuilder()
                    .setPosition(position)
                    .setChatroom(chatroom)
                    .build();

            return stub.withDeadlineAfter(5, TimeUnit.SECONDS).getMessageAtPosition(request);
        } catch (Exception e) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                pw.flush();
                Log.d("GetMessageAtPositionGrpcTask", sw.toString());
                return null;
            }
    }

    @Override
    protected void onPostExecute(messageResponse result) {

        if(result == null){
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context.get().getApplicationContext());
            String language = prefs.getString("language", "English");

            if (language.equals("Português")) {
                Toast.makeText(context.get().getApplicationContext(), "Erro a contactar o servidor",
                        Toast.LENGTH_SHORT).show();
            } else if (language.equals("English")) {
                Toast.makeText(context.get().getApplicationContext(), "Error contacting the server",
                        Toast.LENGTH_SHORT).show();
            }
        } else {

            String data, username, timestamp, type, chatroom;
            int position;
            data = result.getData();
            username = result.getUsername();
            timestamp = result.getTimestamp();
            type = String.valueOf(result.getType());
            chatroom = result.getChatroom();
            position = result.getPosition();

            boolean r = ((GlobalVariableHelper) context.get().getApplicationContext()).getDb().updateMessage(
                    data,
                    username,
                    timestamp,
                    type,
                    chatroom,
                    position
            );

            if(r) {
                Log.d("GetMessageAtPositionGrpcTask", "Message response inserted in cache.");
            } else {
                Log.d("GetMessageAtPositionGrpcTask", "Couldn't insert message in cache.");
            }

            int i = messageAdapter.changeInMessageList(result);
            messageAdapter.notifyItemChanged(i);
        }
    }
}
