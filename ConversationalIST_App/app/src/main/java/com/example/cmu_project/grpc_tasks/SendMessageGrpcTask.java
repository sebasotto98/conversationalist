package com.example.cmu_project.grpc_tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cmu_project.helpers.GlobalVariableHelper;
import com.example.cmu_project.R;
import com.example.cmu_project.adapters.MessageAdapter;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.examples.backendserver.ServerGrpc;
import io.grpc.examples.backendserver.messageResponse;
import io.grpc.examples.backendserver.sendingMessage;

public class SendMessageGrpcTask extends AsyncTask<Object, Void, messageResponse> {
    private final WeakReference<Activity> activityReference;
    private ManagedChannel channel;
    private final MessageAdapter messageAdapter;

    public SendMessageGrpcTask(Activity activity, RecyclerView messageRecycler) {
        this.activityReference = new WeakReference<>(activity);
        this.messageAdapter = (MessageAdapter) messageRecycler.getAdapter();
    }

    @Override
    protected messageResponse doInBackground(Object... params) {
        String host = (String) params[0];
        String message = (String) params[1];
        String portStr = (String) params[2];
        int type = (int) params[3];
        int port = TextUtils.isEmpty(portStr) ? 0 : Integer.parseInt(portStr);
        try {
            channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
            ServerGrpc.ServerBlockingStub stub = ServerGrpc.newBlockingStub(channel);

            sendingMessage request = sendingMessage.newBuilder()
                    .setType(type)
                    .setData(message)
                    .setUsername(((GlobalVariableHelper) activityReference.get().getApplication()).getUsername())
                    .setChatroom(((GlobalVariableHelper) activityReference.get().getApplication()).getCurrentChatroomName())
                    .build();

            return stub.sendMessage(request);
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            pw.flush();
            Log.d("sendMessageGrpcTask", sw.toString());
            return null;
        }
    }

    @Override
    protected void onPostExecute(messageResponse result) {
        if(result != null) {

            Activity activity = activityReference.get();
            if (activity == null) {
                return;
            }

            messageAdapter.addToMessageList(result);
            int position = messageAdapter.getItemCount() - 1;
            messageAdapter.notifyItemInserted(position);

            //save message in cache
            boolean r = ((GlobalVariableHelper) activityReference.get().getApplication()).getDb().insertMessage(
                    result.getData(),
                    result.getUsername(),
                    result.getTimestamp(),
                    String.valueOf(result.getType()),
                    result.getChatroom(),
                    result.getPosition()
            );

            if(r) {
                Log.d("ChatActivity", "Message response inserted in cache.");
            } else {
                Log.d("ChatActivity", "Couldn't insert message in cache.");
            }

            Button sendButton = (Button) activity.findViewById(R.id.send_button);
            sendButton.setEnabled(true);

            try {
                channel.shutdown().awaitTermination(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
