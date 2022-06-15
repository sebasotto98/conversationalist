package com.example.cmu_project.grpc_tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;


import com.example.cmu_project.R;
import com.example.cmu_project.adapters.UserChatsAdapter;
import com.example.cmu_project.helpers.GlobalVariableHelper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.ref.WeakReference;
import java.util.List;

import io.grpc.examples.backendserver.CreateChatRequest;
import io.grpc.examples.backendserver.GetChatsReply;
import io.grpc.examples.backendserver.GetChatsRequest;
import io.grpc.examples.backendserver.Location;
import io.grpc.examples.backendserver.ServerGrpc;

public class GetAllUserChatsGrpcTask extends AsyncTask<Object,Void, GetChatsReply> {

    WeakReference<Activity> activityReference;
    ListView my_chats_list;

    public GetAllUserChatsGrpcTask(Activity activity,ListView my_chats_list) {
        this.activityReference = new WeakReference<>(activity);
        this.my_chats_list = my_chats_list;
    }

    @Override
    protected GetChatsReply doInBackground(Object... params) {

        String user = (String) params[0];

        try {

            ServerGrpc.ServerBlockingStub stub
                    = ((GlobalVariableHelper) activityReference.get().getApplication())
                    .getServerBlockingStub();

            return stub.getAllChats(GetChatsRequest.newBuilder().setUser(user).build());

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

        if(reply != null) {

            Activity activity = activityReference.get();
            if (activity == null) {
                return;
            }

            try {

                List<String> chats_list = reply.getUserChatsList();
                my_chats_list.setAdapter(new UserChatsAdapter(chats_list, activityReference.get(), activityReference.get().getApplication()));

            } catch (Exception e) {

            }

        }

    }

}


