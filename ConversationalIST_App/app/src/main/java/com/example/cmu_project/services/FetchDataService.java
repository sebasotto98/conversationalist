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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.cmu_project.contexts.MobDataContext;
import com.example.cmu_project.contexts.WifiContext;
import com.example.cmu_project.grpc_tasks.GetAllMessagesFromChatGrpcTask;
import com.example.cmu_project.grpc_tasks.GetLastNMessagesFromChatGrpcTask;
import com.example.cmu_project.grpc_tasks.GetRemainingMessagesGrpcTask;
import com.example.cmu_project.grpc_tasks.GetRemainingMessagesMobileDataGrpcTask;
import com.example.cmu_project.grpc_tasks.ListenToChatroomsGrpcTask;
import com.example.cmu_project.helpers.DBHelper;
import com.example.cmu_project.helpers.GlobalVariableHelper;

import java.util.ArrayList;
import java.util.List;

public class FetchDataService extends Service {

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
                    Log.d("FetchDataService", "Wifi 57");
                    if(listenGrpcTask != null) {
                        endTask();
                    }
                    listenGrpcTask = (ListenToChatroomsGrpcTask)
                            new ListenToChatroomsGrpcTask(currentChats,FetchDataService.this, false)
                                    .execute();
                } else if (MDContext.conforms(FetchDataService.this.getApplicationContext())) {
                    Log.d("FetchDataService", "Mobile data");
                    if(listenGrpcTask != null) {
                        endTask();
                    }
                    listenGrpcTask = (ListenToChatroomsGrpcTask)
                            new ListenToChatroomsGrpcTask(currentChats,FetchDataService.this, true)
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
        listenGrpcTask.complete();
        listenGrpcTask = null;
    }

    private final BroadcastReceiver chatListBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            List<String> chats = intent.getStringArrayListExtra("chats");
            Log.d("FetchDataService (chatListBroadcastReceiver)", "got chat list: " + chats);

            if(wifiContext.conforms(FetchDataService.this.getApplicationContext())){
                Log.d("FetchDataService (chatListBroadcastReceiver)", "Wifi");
                if(listenGrpcTask != null) {
                    endTask();
                }
                listenGrpcTask = (ListenToChatroomsGrpcTask)
                        new ListenToChatroomsGrpcTask(chats,FetchDataService.this, false)
                                .execute();
            } else if (MDContext.conforms(FetchDataService.this.getApplicationContext())) {
                Log.d("FetchDataService (chatListBroadcastReceiver)", "Mobile data");
                if(listenGrpcTask != null) {
                    endTask();
                }
                listenGrpcTask = (ListenToChatroomsGrpcTask)
                        new ListenToChatroomsGrpcTask(chats,FetchDataService.this, true)
                                .execute();
            } else {
                Log.d("FetchDataService", "Nothing");
            }

            checkNewMessages(chats);

            currentChats.addAll(chats);
        }
    };

    private void checkNewMessages(List<String> chats){

        DBHelper db = ((GlobalVariableHelper) getApplication()).getDb();

        if(wifiContext.conforms(FetchDataService.this.getApplicationContext())){
            for(String chat: chats){
                int position = db.getPositionLastMessageFromChat(chat);
                if(position == 0){
                    new GetAllMessagesFromChatGrpcTask(this)
                            .execute(chat);
                } else {
                    new GetRemainingMessagesGrpcTask(this)
                            .execute(position,
                                    chat);
                }
            }

        } else if (MDContext.conforms(FetchDataService.this.getApplicationContext())) {
            for(String chat: chats){
                int position = db.getPositionLastMessageFromChat(chat);
                if(position == 0){
                    new GetLastNMessagesFromChatGrpcTask(this)
                            .execute(chat);
                } else {
                    new GetRemainingMessagesMobileDataGrpcTask(this)
                            .execute(position,
                                    chat);
                }
            }
        } else {
            Log.d("FetchDataService", "No internet Connection");
            Toast.makeText(getApplicationContext(),
                    "No connection available. Please connect to the internet.", Toast.LENGTH_SHORT).show();
        }
    }

    private final BroadcastReceiver listenToNewChat = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String chat = intent.getStringExtra("chat");
            Log.d("FetchDataService (listenToNewChat)", "got new chat: " + chat);

            if(wifiContext.conforms(FetchDataService.this.getApplicationContext())){
                new GetAllMessagesFromChatGrpcTask(FetchDataService.this)
                        .execute(chat);
            } else if (MDContext.conforms(FetchDataService.this.getApplicationContext())) {
                new GetLastNMessagesFromChatGrpcTask(FetchDataService.this)
                        .execute(chat);
            } else {
                Log.d("FetchDataService", "Nothing");
            }

            listenGrpcTask.listenNewChat(chat);

            currentChats.add(chat);
        }
    };

}