package com.example.cmu_project.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.cmu_project.contexts.MobDataContext;
import com.example.cmu_project.contexts.WifiContext;
import com.example.cmu_project.grpc_tasks.ListenToChatroomsGrpcTask;

import java.util.ArrayList;
import java.util.List;

public class FetchDataService extends Service {

    private int currentNotificationID = 0;
    private ListenToChatroomsGrpcTask listenGrpcTask = null;
    List<String> currentChats = new ArrayList<>();
    WifiContext wifiContext = new WifiContext();
    MobDataContext MDContext = new MobDataContext();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("FetchDataService", "Started");

        //listen to this events
        LocalBroadcastManager.getInstance(this).registerReceiver(chatListBroadcastReceiver,
                new IntentFilter("chats"));

        LocalBroadcastManager.getInstance(this).registerReceiver(listenToNewChat,
                new IntentFilter("NEWchat"));

        ConnectivityManager connectivityManager = getSystemService(ConnectivityManager.class);
        connectivityManager.registerDefaultNetworkCallback(new ConnectivityManager.NetworkCallback() {

            @Override
            public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
                Log.d("FetchDataService", "New connection");

                if(wifiContext.conforms(FetchDataService.this.getApplicationContext())){
                    Log.d("FetchDataService", "Wifi");
                    if(listenGrpcTask != null) {
                        endTask();
                    }
                    listenGrpcTask = (ListenToChatroomsGrpcTask)
                            new ListenToChatroomsGrpcTask(currentChats,FetchDataService.this, currentNotificationID, false)
                                    .execute();
                } else if (MDContext.conforms(FetchDataService.this.getApplicationContext())) {
                    Log.d("FetchDataService", "Mobile data");
                    if(listenGrpcTask != null) {
                        endTask();
                    }
                    listenGrpcTask = (ListenToChatroomsGrpcTask)
                            new ListenToChatroomsGrpcTask(currentChats,FetchDataService.this, currentNotificationID, true)
                                    .execute();
                } else {
                    Log.d("FetchDataService", "Nothing");
                    if(listenGrpcTask != null){
                        endTask();
                    }
                }
            }
        });

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(chatListBroadcastReceiver);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void endTask(){
        currentNotificationID = listenGrpcTask.complete();
    }

    private final BroadcastReceiver chatListBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            List<String> chats = intent.getStringArrayListExtra("chats");
            Log.d("FetchDataService (chatListBroadcastReceiver)", "got chat list: " + chats);

            if(wifiContext.conforms(FetchDataService.this.getApplicationContext())){
                Log.d("FetchDataService", "Wifi");
                if(listenGrpcTask != null) {
                    endTask();
                }
                listenGrpcTask = (ListenToChatroomsGrpcTask)
                        new ListenToChatroomsGrpcTask(currentChats,FetchDataService.this, currentNotificationID, false)
                                .execute();
            } else if (MDContext.conforms(FetchDataService.this.getApplicationContext())) {
                Log.d("FetchDataService", "Mobile data");
                if(listenGrpcTask != null) {
                    endTask();
                }
                listenGrpcTask = (ListenToChatroomsGrpcTask)
                        new ListenToChatroomsGrpcTask(currentChats,FetchDataService.this, currentNotificationID, true)
                                .execute();
            } else {
                Log.d("FetchDataService", "Nothing");
            }

            currentChats.addAll(chats);
        }
    };

    private final BroadcastReceiver listenToNewChat = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String chat = intent.getStringExtra("chat");
            Log.d("FetchDataService (listenToNewChat)", "got new chat: " + chat);

            listenGrpcTask.listenNewChat(chat);

            currentChats.add(chat);
        }
    };

}