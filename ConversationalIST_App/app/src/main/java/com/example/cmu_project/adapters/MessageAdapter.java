package com.example.cmu_project.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cmu_project.R;

import java.util.List;
import io.grpc.examples.backendserver.messageResponse;

public class MessageAdapter extends RecyclerView.Adapter {

    private List<messageResponse> messageList;
    private Context context;

    private final int MESSAGE_TEXT = 0;
    private final int MESSAGE_PHOTO = 1;
    private final int MESSAGE_GEOLOCATION = 2;

    //change messageList from List<String> to List<Message> that can support text, image or location
    public MessageAdapter(Context context, List<messageResponse> messageList) {
        this.context = context;
        this.messageList = messageList;
    }

    public void addToMessageList(messageResponse message){
        messageList.add(message);
        Log.d("MessageAdapter", "added message: " + message);

    }

    @Override
    public int getItemViewType(int position) {
        messageResponse message = messageList.get(position);

        return message.getType();

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
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (viewType == MESSAGE_TEXT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_text, parent, false);
            return new MessageTextHolder(view);
        } else if (viewType == MESSAGE_PHOTO) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_photo, parent, false);
            return new MessageTextHolder(view);
        } else if (viewType == MESSAGE_GEOLOCATION) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_geolocation, parent, false);
            return new MessageTextHolder(view);
        } else {
            throw new IllegalArgumentException();
        }
    }

    //complete for other message types
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        messageResponse message = messageList.get(position);

        switch (holder.getItemViewType()) {
            case MESSAGE_TEXT:
                ((MessageTextHolder) holder).bind(message);
                break;
            case MESSAGE_PHOTO:
                ((MessagePhotoHolder) holder).bind(message);
                break;
            case MESSAGE_GEOLOCATION:
                ((MessageGeolocationHolder) holder).bind(message);
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    private static class MessageTextHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, nameText;

        MessageTextHolder(View itemView) {
            super(itemView);
            messageText = (TextView) itemView.findViewById(R.id.text_message);
            timeText = (TextView) itemView.findViewById(R.id.text_timestamp);
            nameText = (TextView) itemView.findViewById(R.id.text_user);
        }

        void bind(messageResponse message) {
            messageText.setText(message.getData());
            timeText.setText(message.getTimestamp());
            nameText.setText(message.getUsername());
        }
    }

    private static class MessagePhotoHolder extends RecyclerView.ViewHolder {
        ImageView messagePhoto;
        TextView timePhoto, namePhoto;

        MessagePhotoHolder(View itemView) {
            super(itemView);
            messagePhoto = (ImageView) itemView.findViewById(R.id.photo_message);
            timePhoto = (TextView) itemView.findViewById(R.id.photo_timestamp);
            namePhoto = (TextView) itemView.findViewById(R.id.photo_user);
        }

        void bind(messageResponse message) {
            int imageResource = findImageResourceByName(message.getData());
            messagePhoto.setImageResource(imageResource);
            timePhoto.setText(message.getTimestamp());
            namePhoto.setText(message.getUsername());
        }

        private int findImageResourceByName(String message) {
            //TODO: Search image resource path by image name in message
            return -1;
        }
    }

    private static class MessageGeolocationHolder extends RecyclerView.ViewHolder {
        TextView messageGeolocation, timeGeolocation, nameGeolocation;

        MessageGeolocationHolder(View itemView) {
            super(itemView);
            messageGeolocation = (TextView) itemView.findViewById(R.id.geolocation_message);
            timeGeolocation = (TextView) itemView.findViewById(R.id.geolocation_timestamp);
            nameGeolocation = (TextView) itemView.findViewById(R.id.geolocation_user);
        }

        void bind(messageResponse message) {
            messageGeolocation.setText(message.getData());
            timeGeolocation.setText(message.getTimestamp());
            nameGeolocation.setText(message.getUsername());
        }
    }

}


