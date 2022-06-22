package com.example.cmu_project.helpers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.cmu_project.R;

import java.lang.ref.WeakReference;

public class NotificationsHelper {
    private final String CHANNEL_ID = "ConversationalIST notification";
    private final WeakReference<Context> context;
    private int currentNotificationID = 0;

    public NotificationsHelper(Context context) {
        this.context = new WeakReference<>(context);
    }

    public void pushNotification(String Title, String Text, PendingIntent intent){
        NotificationCompat.Builder builder;

        createNotificationChannel();

        builder = new NotificationCompat.Builder(context.get(), CHANNEL_ID)
                .setContentTitle(Title)
                .setContentText(Text)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ist_logo)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if(intent != null) {
            builder.setContentIntent(intent);
        }
        NotificationManagerCompat manager = NotificationManagerCompat.from(context.get());

        manager.notify(currentNotificationID, builder.build());
        currentNotificationID++;

        Log.d("listenToChatroomsGrpcTask", "Notification Push");
    }

    public void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "ConversationalIST channel";
            String description = "App channel for notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.get().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
