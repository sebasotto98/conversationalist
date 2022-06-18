package com.example.cmu_project.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.cmu_project.grpc_tasks.GetAllUserChatsGrpcTask;
import com.example.cmu_project.grpc_tasks.GetAvailableChatsToJoinGrpcTask;
import com.example.cmu_project.grpc_tasks.JoinChatGrpcTask;
import com.example.cmu_project.helpers.GlobalVariableHelper;
import com.example.cmu_project.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import io.grpc.examples.backendserver.JoinChatReply;
import io.grpc.examples.backendserver.JoinChatRequest;
import io.grpc.examples.backendserver.JoinableChatsReply;
import io.grpc.examples.backendserver.JoinableChatsRequest;
import io.grpc.examples.backendserver.ServerGrpc;

public class JoinChatActivity extends AppCompatActivity {

    ListView chats_list;
    String current_chat_to_join;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_chat);

        AtomicReference<Double>[] user_location = getUserCoord();
        double user_latitude = 39.548666999999995;//user_location[0].get();
        double user_longitude = -8.979649199999999;//user_location[1].get();

        System.out.println("Location is: " + user_latitude + "/" + user_longitude);

        new GetAvailableChatsToJoinGrpcTask(this,user_latitude,user_longitude).execute(((GlobalVariableHelper) this.getApplication()).getUsername());

        chats_list = (ListView) findViewById(R.id.chats_list);

        chats_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                current_chat_to_join = chats_list.getItemAtPosition(i).toString();

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        AtomicReference<Double>[] user_location = getUserCoord();
        double user_latitude = 39.548666999999995;//user_location[0].get();
        double user_longitude = -8.979649199999999;//user_location[1].get();
        new GetAvailableChatsToJoinGrpcTask(this,user_latitude,user_longitude).execute(((GlobalVariableHelper) this.getApplication()).getUsername());
        chats_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                current_chat_to_join = chats_list.getItemAtPosition(i).toString();

            }
        });
    }

    public void join(View view) {

        new JoinChatGrpcTask(this,current_chat_to_join).execute(((GlobalVariableHelper) this.getApplication()).getUsername());

    }

    public AtomicReference<Double>[] getUserCoord() {

        FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        AtomicReference<Double> userX = new AtomicReference<>((double) 0);
        AtomicReference<Double> userY = new AtomicReference<>((double) 0);
        AtomicReference<Double>[] ret = new AtomicReference[2];
        ret[0] = userX;
        ret[1] = userY;


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return ret;
        }


        mFusedLocationClient.getLastLocation().addOnSuccessListener(this,location -> {
            if (location != null) {
                userX.set(location.getLatitude());
                userY.set(location.getLongitude());
            }
        });

        AtomicReference<Double>[] ret_coord = new AtomicReference[2];
        ret_coord[0] = userX;
        ret_coord[1] = userY;

        return ret_coord;

    }


}