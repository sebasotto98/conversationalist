package com.example.cmu_project.adapters;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cmu_project.R;
import com.example.cmu_project.activities.ChatActivity;
import com.example.cmu_project.enums.MessageType;
import com.example.cmu_project.grpc_tasks.GetMessageAtPositionGrpcTask;
import com.example.cmu_project.helpers.GlobalVariableHelper;
import com.example.cmu_project.listeners.OnSwipeTouchListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

import io.grpc.examples.backendserver.messageResponse;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final Logger logger = Logger.getLogger(ChatActivity.class.getName());

    private List<messageResponse> messageList;
    private final Context context;

    private RecyclerView myRecyclerView;
    private final MessageAdapter messageAdapter = this;

    public MessageAdapter(Context context, List<messageResponse> messageList) {
        this.context = context;
        this.messageList = messageList;
    }

    public int changeInMessageList(messageResponse message) {
        Log.d("MessageAdapter", String.valueOf(message));
        int i = 0;
        for (int j = 0; j < getItemCount(); j++) {
            if (messageList.get(j).getPosition() == message.getPosition()) {
                i = j;
                break;
            }
        }

        messageList.set(i, message);
        return i;
    }

    public void addToMessageList(messageResponse message) {
        messageList.add(message);
        Log.d("MessageAdapter", "added message: " + message);

        smoothScroll();
    }

    public List<messageResponse> getMessageList() {
        return messageList;
    }

    private void smoothScroll() {
        myRecyclerView.post(() -> {
            // Call smooth scroll
            myRecyclerView.smoothScrollToPosition(getItemCount());
        });
    }

    public void setScrollPosition(int position) {
        myRecyclerView.post(() -> {
            // Call smooth scroll
            myRecyclerView.scrollToPosition(position);
        });
    }

    public void setMessageList(List<messageResponse> newList) {
        messageList = newList;
        this.notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        myRecyclerView = recyclerView;
    }

    @Override
    public int getItemViewType(int position) {
        messageResponse message = messageList.get(position);
        return message.getType();
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (viewType == MessageType.TEXT.getValue()) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_text, parent, false);
            return new MessageTextHolder(view);
        } else if (viewType == MessageType.PHOTO.getValue()) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_photo, parent, false);
            return new MessagePhotoHolder(view);
        } else if (viewType == MessageType.GEOLOCATION.getValue()) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_geolocation, parent, false);
            return new MessageGeolocationHolder(view);
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        messageResponse message = messageList.get(position);

        MessageType type = MessageType.getByValue(holder.getItemViewType());

        if (type != null) {
            switch (type) {
                case TEXT:
                    ((MessageTextHolder) holder).bind(message);
                    break;
                case PHOTO:
                    ((MessagePhotoHolder) holder).bind(message);
                    break;
                case GEOLOCATION:
                    try {
                        ((MessageGeolocationHolder) holder).bind(message);
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    private class MessageTextHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, nameText;

        MessageTextHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.text_message);
            timeText = itemView.findViewById(R.id.text_timestamp);
            nameText = itemView.findViewById(R.id.text_user);
        }

        @SuppressLint("ClickableViewAccessibility")
        void bind(messageResponse message) {
            messageText.setText(message.getData());
            timeText.setText(message.getTimestamp());
            nameText.setText(message.getUsername());

            messageText.setOnTouchListener(new OnSwipeTouchListener(context) {
                @Override
                public void onLongClick() {
                    super.onLongClick();
                    if (!message.getData().isEmpty()) {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, message.getData());
                        sendIntent.setType("text/plain");

                        Intent shareIntent = Intent.createChooser(sendIntent, "Share text");
                        context.startActivity(shareIntent);
                    }
                }
            });
        }
    }

    private class MessagePhotoHolder extends RecyclerView.ViewHolder {
        ImageView messagePhoto;
        TextView timePhoto, namePhoto;

        MessagePhotoHolder(View itemView) {
            super(itemView);
            messagePhoto = itemView.findViewById(R.id.photo_message);
            timePhoto = itemView.findViewById(R.id.photo_timestamp);
            namePhoto = itemView.findViewById(R.id.photo_user);
        }

        @SuppressLint("ClickableViewAccessibility")
        void bind(messageResponse message) {
            if (!message.getData().isEmpty()) {
                messagePhoto.setImageBitmap(stringToBitMap(message.getData()));
            }
            timePhoto.setText(message.getTimestamp());
            namePhoto.setText(message.getUsername());

            messagePhoto.setOnClickListener(v -> {
                Log.d("MessageAdapter", "Click Listener");
                if (message.getData().isEmpty()) {

                    new GetMessageAtPositionGrpcTask(context, messageAdapter)
                            .execute(
                                    message.getPosition(),
                                    ((GlobalVariableHelper) context.getApplicationContext()).getCurrentChatroomName()
                            );
                }
            });

            messagePhoto.setOnTouchListener(new OnSwipeTouchListener(context) {
                @Override
                public void onLongClick() {
                    super.onLongClick();
                    if (!message.getData().isEmpty()) {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);

                        Bitmap photo = stringToBitMap(message.getData());
                        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                        photo.compress(Bitmap.CompressFormat.PNG, 100, bytes);

                        sendIntent.putExtra("BMP", bytes.toByteArray());
                        sendIntent.setType("image/png");

                        Intent shareIntent = Intent.createChooser(sendIntent, "Share photo");
                        context.startActivity(shareIntent);
                    }
                }
            });
        }
    }

    private class MessageGeolocationHolder extends RecyclerView.ViewHolder {
        ImageView messageGeolocation;
        TextView timeGeolocation, nameGeolocation;

        MessageGeolocationHolder(View itemView) {
            super(itemView);
            messageGeolocation = itemView.findViewById(R.id.geolocation_message);
            timeGeolocation = itemView.findViewById(R.id.geolocation_timestamp);
            nameGeolocation = itemView.findViewById(R.id.geolocation_user);
        }

        @SuppressLint("ClickableViewAccessibility")
        void bind(messageResponse message) throws PackageManager.NameNotFoundException {

            String[] coordinates = message.getData().split("/");
            String geolocationX = coordinates[0];
            String geolocationY = coordinates[1];

            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = applicationInfo.metaData;
            String apiKey = bundle.getString("com.google.android.geo.API_KEY");

            String url = "https://maps.google.com/maps/api/staticmap?center=" + geolocationX + "," + geolocationY + "&zoom=15&size=640x480&scale=2&maptype=hybrid&key=" + apiKey;

            final Bitmap[] geolocationImage = {null};
            Picasso.with(context)
                    .load(url)
                    .into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            geolocationImage[0] = bitmap;

                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    });

            messageGeolocation.setImageBitmap(geolocationImage[0]);

            FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);

            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            AtomicReference<Double> userX = new AtomicReference<>((double) 0);
            AtomicReference<Double> userY = new AtomicReference<>((double) 0);

            mFusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
                if (location != null) {
                    userX.set(location.getLatitude());
                    userY.set(location.getLongitude());
                }
            });

            messageGeolocation.setOnTouchListener(new OnSwipeTouchListener(context) {
                @Override
                public void onClick() {
                    super.onClick();
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://maps.google.com/maps?saddr=" + userX + "," + userY + "&daddr=" + geolocationX + "," + geolocationY));
                    context.startActivity(intent);
                }

                @Override
                public void onLongClick() {
                    super.onLongClick();
                    if (!message.getData().isEmpty()) {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);

                        Bitmap geolocation = geolocationImage[0];
                        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                        geolocation.compress(Bitmap.CompressFormat.JPEG, 50, bytes);

                        sendIntent.putExtra("BMP", bytes.toByteArray());
                        sendIntent.setType("image/jpg");

                        Intent shareIntent = Intent.createChooser(sendIntent, "Share geolocation");
                        context.startActivity(shareIntent);
                    }
                }
            });

            timeGeolocation.setText(message.getTimestamp());
            nameGeolocation.setText(message.getUsername());
        }
    }

    public Bitmap stringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        } catch (Exception e) {
            logger.warning(e.getMessage());
            return null;
        }
    }
}