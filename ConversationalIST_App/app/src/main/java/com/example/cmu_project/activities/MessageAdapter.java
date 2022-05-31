package com.example.cmu_project.activities;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cmu_project.R;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter{

    private Context context;
    private List<String> messageList;

    private final int MESSAGE_TEXT = 1;
    
    //change messageList from List<String> to List<Message> that can support text, image or location
    public MessageAdapter(Context context, List<String> messageList) {
        this.context = context;
        this.messageList = messageList;
    }

    public void addToMessageList(String message){
        messageList.add(message);
        Log.d("MessageAdapter", "added message: " + message);

    }

    //extend for identify image, location or text
    @Override
    public int getItemViewType(int position) {
        return MESSAGE_TEXT;

        /*if (message.getSender().getUserId().equals(SendBird.getCurrentUser().getUserId())) {
            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            // If some other user sent the message
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }*/
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    //complete for other message types
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == MESSAGE_TEXT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_text, parent, false);
            return new MessageTextHolder(view);
        } /*else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_received, parent, false);
            return new ReceivedMessageHolder(view);
        }*/
        return null;
    }

    //complete for other message types
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String message = messageList.get(position);

        switch (holder.getItemViewType()) {
            case MESSAGE_TEXT:
                ((MessageTextHolder) holder).bind(message);
                break;
        }
    }

    //add more private classes to support location and image
    private class MessageTextHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, nameText;

        MessageTextHolder(View itemView) {
            super(itemView);
            messageText = (TextView) itemView.findViewById(R.id.text_message);
            timeText = (TextView) itemView.findViewById(R.id.text_timestamp);
            nameText = (TextView) itemView.findViewById(R.id.text_user);
        }

        //extend for username and timestamp
        void bind(String message) {
            messageText.setText(message);
        }
    }

}


