package com.example.cmu_project.grpc_tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cmu_project.adapters.MessageAdapter;
import com.example.cmu_project.helpers.GlobalVariableHelper;
import com.example.cmu_project.listeners.OnScrollListener;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.grpc.examples.backendserver.ServerGrpc;
import io.grpc.examples.backendserver.messageResponse;
import io.grpc.examples.backendserver.messagesBetweenPosition;

public class GetMessagesBetweenPositionsMobileDataGrpcTask extends AsyncTask<Object, Void, Iterator<messageResponse>> {
    private final WeakReference<Activity> activityReference;
    private final MessageAdapter messageAdapter;
    private final OnScrollListener scrollListener;

    public GetMessagesBetweenPositionsMobileDataGrpcTask(Activity activity, RecyclerView messageRecycler, OnScrollListener scrollListener) {
        this.activityReference = new WeakReference<>(activity);
        this.messageAdapter = (MessageAdapter) messageRecycler.getAdapter();
        this.scrollListener = scrollListener;
    }

    @Override
    protected Iterator<messageResponse> doInBackground(Object... params) {
        int lastMessagePosition = (int) params[0];
        int numberOfMessages = (int) params[1];
        String chatroom = (String) params[2];
        try {

            ServerGrpc.ServerBlockingStub stub
                    = ((GlobalVariableHelper) activityReference.get().getApplication())
                    .getServerBlockingStub();

            messagesBetweenPosition request = messagesBetweenPosition.newBuilder()
                    .setChatroom(chatroom)
                    .setNumberOfMessages(numberOfMessages) //10 but this can be variable
                    .setPositionOfLastMessage(lastMessagePosition - 1)
                    .build();

            return stub.withDeadlineAfter(5, TimeUnit.SECONDS).getMessagesBetweenPositionsMobileData(request);
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            pw.flush();
            Log.d( "OnScrollListener",sw.toString());
            return null;
        }
    }

    @Override
    protected void onPostExecute(Iterator<messageResponse> messages) {
        Activity activity = activityReference.get();
        if (activity == null) {
            return;
        }
        if(messages != null) {


            List<messageResponse> messageList = messageAdapter.getMessageList();
            List<messageResponse> newMessageList = new ArrayList<>();

            while (messages.hasNext()) {
                messageResponse nextMessage = messages.next();
                newMessageList.add(nextMessage);

                //save message in cache
                boolean r = ((GlobalVariableHelper) activityReference.get().getApplication()).getDb().insertMessage(
                        nextMessage.getData(),
                        nextMessage.getUsername(),
                        nextMessage.getTimestamp(),
                        String.valueOf(nextMessage.getType()),
                        ((GlobalVariableHelper) activityReference.get().getApplication()).getCurrentChatroomName(),
                        nextMessage.getPosition()
                );

                if(r) {
                    Log.d("getMessagesBetweenPositionsMobileDataGrpcTask", "Message response inserted in cache.");
                } else {
                    Log.d("getMessagesBetweenPositionsMobileDataGrpcTask", "Couldn't insert message in cache.");
                }
            }

            List<messageResponse> finalMessageList = new ArrayList<>();
            finalMessageList.addAll(newMessageList);
            finalMessageList.addAll(messageList);
            messageAdapter.setMessageList(finalMessageList);

            messageAdapter.setScrollPosition(finalMessageList.size() - newMessageList.size());

            if(finalMessageList.get(0).getPosition() == 1){
                scrollListener.setGotAllChatMessages(true);
            }

            scrollListener.setLoadingMoreMessages(false);

        } else {
            Toast.makeText(activity.getApplicationContext(), "Error contacting the server",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
