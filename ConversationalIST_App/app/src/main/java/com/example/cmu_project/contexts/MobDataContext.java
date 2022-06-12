package com.example.cmu_project.contexts;

import android.content.Context;
import android.net.ConnectivityManager;

import androidx.core.net.ConnectivityManagerCompat;

public class MobDataContext extends BandwidthContext{
    @Override
    public boolean conforms(Object... args) {
        Context context = (Context) args[0];

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected()) {

            //isActiveNetworkMetered returns true if connected to data/metered connection
            //                               false if connected to wifi
            return ConnectivityManagerCompat.isActiveNetworkMetered(cm);
        }
        return false;
    }
}