package com.example.cmu_project.Listeners;

import android.app.Activity;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cmu_project.GlobalVariables;
import com.example.cmu_project.adapters.MessageAdapter;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.examples.backendserver.*;

public class MyOnScrollListener extends RecyclerView.OnScrollListener {
    private boolean loadingMoreMessages = false;
    private boolean gotAllChatMessages = false;
    private final LinearLayoutManager linearLayoutManager;
    private final WeakReference<Activity> activityReference;
    private final MessageAdapter messageAdapter;
    private final RecyclerView messageRecycler;

    public MyOnScrollListener(Activity activity, RecyclerView messageRecycler){
        this.messageRecycler = messageRecycler;
        this.linearLayoutManager = (LinearLayoutManager) messageRecycler.getLayoutManager();
        this.messageAdapter = (MessageAdapter) messageRecycler.getAdapter();
        this.activityReference = new WeakReference<>(activity);
    }

    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {

        //scrolling up...
        if (!gotAllChatMessages && !loadingMoreMessages && dy < 0) {
            //Toast.makeText(getApplicationContext(),"scrolling up...", Toast.LENGTH_SHORT).show();
            int firstVisibleItemIndex = linearLayoutManager.findFirstVisibleItemPosition();

            //reached top of list
            if(firstVisibleItemIndex == 0){
                //Toast.makeText(getApplicationContext(),"On top", Toast.LENGTH_SHORT).show();
                loadingMoreMessages = true;

                Log.d("MyOnScrollListener", "Loading more messages from server.");

                List<messageResponse> messageList = messageAdapter.getMessageList();

                int position = (messageList.get(0)).getPosition();
                if(position == 0){
                    gotAllChatMessages = true;
                } else {
                    int numberOfMessages = 10;
                    new MyOnScrollListener.getMessagesBetweenPositionsMobileDataGrpcTask(activityReference.get(), messageRecycler, this)
                            .execute(
                                    "192.168.1.135",
                                    "50051",
                                    position,
                                    numberOfMessages,
                                    ((GlobalVariables) activityReference.get().getApplication()).getCurrentChatroomName());
                }
            }
        }
    }

    public void setGotAllChatMessages(boolean gotAllChatMessages) {
        this.gotAllChatMessages = gotAllChatMessages;
    }

    public void setLoadingMoreMessages(boolean loadingMoreMessages){
        this.loadingMoreMessages = loadingMoreMessages;
    }

    private static class getMessagesBetweenPositionsMobileDataGrpcTask extends AsyncTask<Object, Void, Iterator<messageResponse>> {
        private final WeakReference<Activity> activityReference;
        private ManagedChannel channel;
        private final MessageAdapter messageAdapter;
        private final MyOnScrollListener scrollListener;

        private getMessagesBetweenPositionsMobileDataGrpcTask(Activity activity, RecyclerView messageRecycler, MyOnScrollListener scrollListener) {
            this.activityReference = new WeakReference<>(activity);
            this.messageAdapter = (MessageAdapter) messageRecycler.getAdapter();
            this.scrollListener = scrollListener;
        }

        @Override
        protected Iterator<messageResponse> doInBackground(Object... params) {
            String host = (String) params[0];
            String portStr = (String) params[1];
            int lastMessagePosition = (int) params[2];
            int numberOfMessages = (int) params[3];
            String chatroom = (String) params[4];
            int port = TextUtils.isEmpty(portStr) ? 0 : Integer.parseInt(portStr);
            try {
                channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
                ServerGrpc.ServerBlockingStub stub = ServerGrpc.newBlockingStub(channel);

                messagesBetweenPosition request = messagesBetweenPosition.newBuilder()
                        .setChatroom(chatroom)
                        .setNumberOfMessages(numberOfMessages) //10 but this can be variable
                        .setPositionOfLastMessage(lastMessagePosition - 1)
                        .build();

                return stub.getMessagesBetweenPositionsMobileData(request);
            } catch (Exception e) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                pw.flush();
                Log.d( "MyOnScrollListener",sw.toString());
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

                List<messageResponse> messageList = messageAdapter.getMessageList();
                List<messageResponse> newMessageList = new ArrayList<>();

                while (messages.hasNext()) {
                    messageResponse nextMessage = messages.next();
                    newMessageList.add(nextMessage);

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
                        Log.d("MyOnScrollListener", "Message response inserted in cache.");
                    } else {
                        Log.d("MyOnScrollListener", "Couldn't insert message in cache.");
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

                try {
                    channel.shutdown().awaitTermination(1, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
