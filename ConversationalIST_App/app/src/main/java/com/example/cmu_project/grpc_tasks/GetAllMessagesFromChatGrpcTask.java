package com.example.cmu_project.grpc_tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cmu_project.helpers.GlobalVariableHelper;
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

public class GetAllMessagesFromChatGrpcTask extends AsyncTask<Object, Void, Iterator<messageResponse>> {
    private final WeakReference<Activity> activityReference;
    private final MessageAdapter messageAdapter;

    public GetAllMessagesFromChatGrpcTask(Activity activity, RecyclerView messageRecycler) {
        this.activityReference = new WeakReference<>(activity);
        this.messageAdapter = (MessageAdapter) messageRecycler.getAdapter();
    }

    @Override
    protected Iterator<messageResponse> doInBackground(Object... params) {
        String chatroom = (String) params[0];
        try {

            ServerGrpc.ServerBlockingStub stub
                    = ((GlobalVariableHelper) activityReference.get().getApplication())
                        .getServerBlockingStub();

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

            try {
                while (messages.hasNext()) {
                    messageResponse nextMessage = messages.next();
                    messageAdapter.addToMessageList(nextMessage);
                    int position = messageAdapter.getItemCount() - 1;
                    messageAdapter.notifyItemInserted(position);

                    //save message in cache
                    boolean r = ((GlobalVariableHelper) activityReference.get().getApplication()).getDb().insertMessage(
                            nextMessage.getData(),
                            nextMessage.getUsername(),
                            nextMessage.getTimestamp(),
                            String.valueOf(nextMessage.getType()),
                            ((GlobalVariableHelper) activityReference.get().getApplication()).getCurrentChatroomName(),
                            nextMessage.getPosition()
                    );

                    if (r) {
                        Log.d("getAllMessagesFromChatGrpcTask", "Message response inserted in cache.");
                    } else {
                        Log.d("getAllMessagesFromChatGrpcTask", "Couldn't insert message in cache.");
                    }
                }
            } catch (Exception e) {
                Log.d("getAllMessagesFromChatGrpcTask", e.getMessage());
                Toast.makeText(activity.getApplicationContext(), "Error contacting the server",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}
