package com.example.cmu_project.grpc_tasks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;


import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.cmu_project.R;
import com.example.cmu_project.adapters.UserChatsAdapter;
import com.example.cmu_project.helpers.GlobalVariableHelper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.grpc.examples.backendserver.CreateChatRequest;
import io.grpc.examples.backendserver.GetChatsReply;
import io.grpc.examples.backendserver.GetChatsRequest;
import io.grpc.examples.backendserver.Location;
import io.grpc.examples.backendserver.ServerGrpc;

public class GetAllUserChatsGrpcTask extends AsyncTask<Object,Void, GetChatsReply> {

    WeakReference<Activity> activityReference;
    ListView my_chats_list;
    private final WeakReference<Context> context;

    public GetAllUserChatsGrpcTask(Activity activity, ListView my_chats_list) {
        this.activityReference = new WeakReference<>(activity);
        this.my_chats_list = my_chats_list;
        this.context = new WeakReference<>(activityReference.get().getApplicationContext());
    }

    @Override
    protected GetChatsReply doInBackground(Object... params) {

        String user = (String) params[0];

        try {

            ServerGrpc.ServerBlockingStub stub
                    = ((GlobalVariableHelper) activityReference.get().getApplication())
                    .getServerBlockingStub();

            return stub.withDeadlineAfter(5, TimeUnit.SECONDS).getAllChats(GetChatsRequest.newBuilder().setUser(user).build());

        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            pw.flush();
            Log.d("GetAllUserChatsGrpcTask", sw.toString());
            return null;
        }

    }

    @Override
    protected void onPostExecute(GetChatsReply reply) {

        Activity activity = activityReference.get();
        if (activity == null) {
            return;
        }
        if(reply != null) {

            List<String> chats_list = reply.getUserChatsList();
            my_chats_list.setAdapter(new UserChatsAdapter(chats_list, activityReference.get(), activityReference.get().getApplication()));

            //Convert from ProtobufArrayList to java ArrayList
            ArrayList<String> chatsToListen = new ArrayList<>(chats_list);
            //broadcast to service the chats to listen
            Intent intent = new Intent("chats");
            intent.putStringArrayListExtra("chats", chatsToListen);

            LocalBroadcastManager.getInstance(context.get()).sendBroadcast(intent);

            Log.d("GetAllUserChatsGrpcTask", "Send chatsToListen: " + chatsToListen);
        } else {
            Toast.makeText(activity.getApplicationContext(), "Error contacting the server",
                    Toast.LENGTH_SHORT).show();
        }
    }
}


