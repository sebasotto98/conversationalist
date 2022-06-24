package com.example.cmu_project.grpc_tasks;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cmu_project.helpers.GlobalVariableHelper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.grpc.examples.backendserver.GetChatMembersReply;
import io.grpc.examples.backendserver.GetChatMembersRequest;
import io.grpc.examples.backendserver.ServerGrpc;

public class GetChatMembersGrpcTask extends AsyncTask<Object,Void, GetChatMembersReply> {

    WeakReference<Activity> activityReference;
    ListView chatMembersList;
    List<String> chatMembers;
    
    public GetChatMembersGrpcTask(Activity activity, ListView chatMembersList, List<String> chatMembers) {
        this.activityReference = new WeakReference<>(activity);
        this.chatMembersList = chatMembersList;
        this.chatMembers = chatMembers;
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

            chatMembers = reply.getMembersList();
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,android.R.layout.simple_list_item_1, chatMembers);
            chatMembersList.setAdapter(adapter);


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
