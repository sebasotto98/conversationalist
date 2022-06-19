package com.example.cmu_project.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.cmu_project.contexts.MobDataContext;
import com.example.cmu_project.contexts.WifiContext;
import com.example.cmu_project.grpc_tasks.ListenToChatroomsGrpcTask;
import com.example.cmu_project.helpers.GlobalVariableHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FetchDataService extends Service {

    private int currentNotificationID = 0;
    private ListenToChatroomsGrpcTask listenGrpcTask;
    List<String> currentChats = new ArrayList<>();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        /*TODO -> Difference between listening on data and on wifi (current version is just wifi)
        TODO listen to changes on network state and change the listening strategy*/

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
                WifiContext wifiContext = new WifiContext();
                MobDataContext MDContext = new MobDataContext();
                if(wifiContext.conforms(FetchDataService.this.getApplicationContext())){
                    Log.d("FetchDataService", "Wifi");
                } else if (MDContext.conforms(FetchDataService.this.getApplicationContext())) {
                    Log.d("FetchDataService", "Mobile data");
                } else {
                    Log.d("FetchDataService", "Nothing");
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

            listenGrpcTask = (ListenToChatroomsGrpcTask)
                    new ListenToChatroomsGrpcTask(chats,FetchDataService.this, currentNotificationID)
                            .execute();

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
