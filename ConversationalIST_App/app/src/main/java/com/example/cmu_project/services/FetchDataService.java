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

import java.util.Collections;
import java.util.List;

public class FetchDataService extends Service {

    private int currentNotificationID = 0;
    private ListenToChatroomsGrpcTask listenGrpcTask;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //TODO -> Fetch chatrooms from DB or from server (which one?)
        /*TODO -> Difference between listening on data and on wifi (current version is just wifi)
        TODO listen to changes on network state and change the listening strategy*/
        List<String> chats = Collections.singletonList("sala1");

        listenGrpcTask = (ListenToChatroomsGrpcTask)
                new ListenToChatroomsGrpcTask(chats,this, currentNotificationID)
                .execute();

        LocalBroadcastManager.getInstance(this).registerReceiver(networkChangeBroadcastReceiver,
                new IntentFilter(ConnectivityManager.EXTRA_NO_CONNECTIVITY));

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
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void endTask(){
        currentNotificationID = listenGrpcTask.complete();
    }

    private final BroadcastReceiver networkChangeBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            Toast.makeText(FetchDataService.this.getApplicationContext(), "NetWork Changed",
                    Toast.LENGTH_SHORT).show();
        }
    };


}
