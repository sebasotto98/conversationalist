package com.example.cmu_project.grpc_tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.cmu_project.helpers.GlobalVariableHelper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import io.grpc.examples.backendserver.LeaveChatReply;
import io.grpc.examples.backendserver.LeaveChatRequest;
import io.grpc.examples.backendserver.ServerGrpc;

public class LeaveChatGrpcTask  extends AsyncTask<Object,Void, LeaveChatReply> {

    WeakReference<Activity> activityReference;
    Button callbtn;
    Button leavebtn;


    public LeaveChatGrpcTask(Activity activity, Button callbtn,Button leavebtn) {
        this.activityReference = new WeakReference<>(activity);
        this.callbtn = callbtn;
        this.leavebtn = leavebtn;
    }

    @Override
    protected LeaveChatReply doInBackground(Object... params) {

        String user = (String) params[0];
        String chat_name = (String) params[1];

        try {

            ServerGrpc.ServerBlockingStub stub
                    = ((GlobalVariableHelper) activityReference.get().getApplication())
                    .getServerBlockingStub();

            return stub.withDeadlineAfter(5, TimeUnit.SECONDS).leaveChat(LeaveChatRequest.newBuilder().setUser(user).setChatName(chat_name).build());

        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            pw.flush();
            Log.d("LeaveChatGrpcTask", sw.toString());
            return null;
        }

    }

    @Override
    protected void onPostExecute(LeaveChatReply reply) {

        System.out.println(reply.getAck());
        this.callbtn.setVisibility(View.GONE);
        this.leavebtn.setVisibility(View.GONE);

    }



}
