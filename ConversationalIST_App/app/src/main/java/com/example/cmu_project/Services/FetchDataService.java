package com.example.cmu_project.Services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.cmu_project.GlobalVariables;
import com.example.cmu_project.GrpcTasks.getAllMessagesFromChatGrpcTask;
import com.example.cmu_project.GrpcTasks.listenToChatroomsGrpcTask;
import com.example.cmu_project.R;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FetchDataService extends Service {

    private int currentNotificationID = 0;
    private listenToChatroomsGrpcTask listenGrpcTask;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //TODO -> Fetch chatrooms from DB or from server (which one?)
        List<String> chats = Collections.singletonList("CMU Chat");

        listenGrpcTask = (com.example.cmu_project.GrpcTasks.listenToChatroomsGrpcTask)
                new listenToChatroomsGrpcTask(chats,this, currentNotificationID)
                .execute(
                        "192.168.1.135",
                        "50051");

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

    public void endTask(){
        currentNotificationID = listenGrpcTask.complete();
    }
}
