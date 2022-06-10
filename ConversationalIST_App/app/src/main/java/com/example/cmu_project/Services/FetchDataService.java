package com.example.cmu_project.Services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.cmu_project.R;
import com.example.cmu_project.activities.ChatActivity;

public class FetchDataService extends Service {

    private int currentNotificationID = 0;
    private final String CHANNEL_ID = "ConversationalIST notification";


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        createNotificationChannel();

        //TODO
        //put the respective chat when creating the intent
        /*Intent notificationIntent = new Intent(this, ChatActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);*/

        pushNotification("My Title", "My Text", null);

        Log.d("FetchDataService", "Notification Push");

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void pushNotification(String Title, String Text, PendingIntent intent){
        NotificationCompat.Builder builder;


        builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(Title)
                .setContentText(Text)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ist_logo)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if(intent != null) {
            builder.setContentIntent(intent);
        }
        NotificationManagerCompat manager = NotificationManagerCompat.from(this);

        manager.notify(currentNotificationID, builder.build());
        currentNotificationID++;
    }

    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "ConversationalIST channel";
            String description = "App channel for notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
