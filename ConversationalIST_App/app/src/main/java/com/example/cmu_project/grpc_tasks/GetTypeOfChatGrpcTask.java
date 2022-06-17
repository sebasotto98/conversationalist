package com.example.cmu_project.grpc_tasks;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.cmu_project.R;
import com.example.cmu_project.activities.ChatActivity;
import com.example.cmu_project.helpers.GlobalVariableHelper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import io.grpc.examples.backendserver.ChatTypeReply;
import io.grpc.examples.backendserver.ChatTypeRequest;
import io.grpc.examples.backendserver.ServerGrpc;

public class GetTypeOfChatGrpcTask extends AsyncTask<Object,Void, ChatTypeReply> {

    WeakReference<Activity> activityReference;

    public GetTypeOfChatGrpcTask(Activity activity) {
        this.activityReference = new WeakReference<>(activity);
    }


    @Override
    protected ChatTypeReply doInBackground(Object... params) {

        String chat_name = (String) params[0];

        try {
            ServerGrpc.ServerBlockingStub stub
                    = ((GlobalVariableHelper) activityReference.get().getApplication())
                    .getServerBlockingStub();

            ChatTypeRequest request = ChatTypeRequest.newBuilder().setChatName(chat_name).build();


            return stub.withDeadlineAfter(5, TimeUnit.SECONDS).getChatType(request);

        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            pw.flush();
            Log.d("GetTypeOfChatGrpcTask", sw.toString());
            return null;
        }

    }

    @Override
    protected void onPostExecute(ChatTypeReply reply) {

        Activity activity = activityReference.get();
        if (activity == null) {
            return;
        }
        if(reply != null) {

            String chat_type = reply.getChatType();
            if (!chat_type.equals("Private"))
                activity.findViewById(R.id.share_link_button).setVisibility(View.GONE);

        } else {
            Toast.makeText(activity.getApplicationContext(), "Error contacting the server",
                    Toast.LENGTH_SHORT).show();
        }

    }

    }


