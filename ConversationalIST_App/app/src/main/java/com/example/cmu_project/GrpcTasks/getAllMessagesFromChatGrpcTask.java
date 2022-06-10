package com.example.cmu_project.GrpcTasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cmu_project.GlobalVariables;
import com.example.cmu_project.adapters.MessageAdapter;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.examples.backendserver.ServerGrpc;
import io.grpc.examples.backendserver.chatMessageRequest;
import io.grpc.examples.backendserver.messageResponse;

public class getAllMessagesFromChatGrpcTask extends AsyncTask<Object, Void, Iterator<messageResponse>> {
    private final WeakReference<Activity> activityReference;
    private ManagedChannel channel;
    private final MessageAdapter messageAdapter;

    public getAllMessagesFromChatGrpcTask(Activity activity, RecyclerView messageRecycler) {
        this.activityReference = new WeakReference<>(activity);
        this.messageAdapter = (MessageAdapter) messageRecycler.getAdapter();
    }

    @Override
    protected Iterator<messageResponse> doInBackground(Object... params) {
        String host = (String) params[0];
        String portStr = (String) params[1];
        String chatroom = (String) params[2];
        int port = TextUtils.isEmpty(portStr) ? 0 : Integer.parseInt(portStr);
        try {
            channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
            ServerGrpc.ServerBlockingStub stub = ServerGrpc.newBlockingStub(channel);

            chatMessageRequest request = chatMessageRequest.newBuilder()
                    .setChatroom(chatroom)
                    .build();

            return stub.getAllChatMessages(request);
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            pw.flush();
            Log.d("getAllMessagesFromChatGrpcTask", sw.toString());
            return null;
        }
    }

    @Override
    protected void onPostExecute(Iterator<messageResponse> messages) {
        if(messages != null) {

            Activity activity = activityReference.get();
            if (activity == null) {
                return;
            }

            while (messages.hasNext()) {
                messageResponse nextMessage = messages.next();
                messageAdapter.addToMessageList(nextMessage);
                int position = messageAdapter.getItemCount() - 1;
                messageAdapter.notifyItemInserted(position);

                //save message in cache
                boolean r = ((GlobalVariables) activityReference.get().getApplication()).getDb().insertMessage(
                        nextMessage.getData(),
                        nextMessage.getUsername(),
                        nextMessage.getTimestamp(),
                        String.valueOf(nextMessage.getType()),
                        ((GlobalVariables) activityReference.get().getApplication()).getCurrentChatroomName(),
                        nextMessage.getPosition()
                );

                if(r) {
                    Log.d("ChatActivity", "Message response inserted in cache.");
                } else {
                    Log.d("ChatActivity", "Couldn't insert message in cache.");
                }
            }

            try {
                channel.shutdown().awaitTermination(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}