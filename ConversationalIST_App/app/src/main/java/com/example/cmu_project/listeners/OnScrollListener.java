package com.example.cmu_project.listeners;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cmu_project.helpers.GlobalVariableHelper;
import com.example.cmu_project.adapters.MessageAdapter;

import java.lang.ref.WeakReference;
import java.util.List;

import com.example.cmu_project.grpc_tasks.*;
import io.grpc.examples.backendserver.*;

public class OnScrollListener extends RecyclerView.OnScrollListener {
    private boolean loadingMoreMessages = false;
    private boolean gotAllChatMessages = false;
    private final LinearLayoutManager linearLayoutManager;
    private final WeakReference<Activity> activityReference;
    private final MessageAdapter messageAdapter;
    private final RecyclerView messageRecycler;

    public OnScrollListener(Activity activity, RecyclerView messageRecycler){
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

                Log.d("OnScrollListener", "Loading more messages from server.");

                List<messageResponse> messageList = messageAdapter.getMessageList();

                int position = (messageList.get(0)).getPosition();
                if(position == 0){
                    gotAllChatMessages = true;
                } else {
                    int numberOfMessages = 10;
                    new GetMessagesBetweenPositionsMobileDataGrpcTask(activityReference.get(), messageRecycler, this)
                            .execute(position,
                                    numberOfMessages,
                                    ((GlobalVariableHelper) activityReference.get().getApplication()).getCurrentChatroomName());
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
}
