package com.example.cmu_project.grpc_tasks;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.cmu_project.adapters.UserChatsAdapter;
import com.example.cmu_project.helpers.GlobalVariableHelper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.grpc.examples.backendserver.GetChatMembersReply;
import io.grpc.examples.backendserver.GetChatMembersRequest;
import io.grpc.examples.backendserver.ServerGrpc;


public class GetChatMembersGrpcTask extends AsyncTask<Object,Void, GetChatMembersReply> {

    WeakReference<Activity> activityReference;
    ListView chat_members_list;

    public GetChatMembersGrpcTask(Activity activity, ListView chat_members_list) {
        this.activityReference = new WeakReference<>(activity);
        this.chat_members_list = chat_members_list;
    }

    @Override
    protected GetChatMembersReply doInBackground(Object... params) {

        String chat_name = (String) params[0];

        try {

            ServerGrpc.ServerBlockingStub stub
                    = ((GlobalVariableHelper) activityReference.get().getApplication())
                    .getServerBlockingStub();

            GetChatMembersRequest request = GetChatMembersRequest.newBuilder().setChatName(chat_name).build();

            return stub.withDeadlineAfter(5, TimeUnit.SECONDS).getChatMembers(request);

        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            pw.flush();
            Log.d("GetChatMembersGrpcTask", sw.toString());
            return null;
        }


    }

    @Override
    protected void onPostExecute(GetChatMembersReply reply) {

        Activity activity = activityReference.get();
        if (activity == null) {
            return;
        }
        if(reply != null) {

            List<String> chat_members = reply.getMembersList();
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,android.R.layout.simple_list_item_1,chat_members);
            chat_members_list.setAdapter(adapter);

        } else {
            Toast.makeText(activity.getApplicationContext(), "Error contacting the server",
                    Toast.LENGTH_SHORT).show();
        }


    }





}
