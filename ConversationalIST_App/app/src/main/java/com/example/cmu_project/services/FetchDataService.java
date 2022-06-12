package com.example.cmu_project.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.cmu_project.grpc_tasks.ListenToChatroomsGrpcTask;

import java.util.Collections;
import java.util.List;

public class FetchDataService extends Service {

    private int currentNotificationID = 0;
    private ListenToChatroomsGrpcTask listenGrpcTask;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //TODO -> Fetch chatrooms from DB or from server (which one?)
        List<String> chats = Collections.singletonList("sala1");

        listenGrpcTask = (ListenToChatroomsGrpcTask)
                new ListenToChatroomsGrpcTask(chats,this, currentNotificationID)
                .execute();

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
