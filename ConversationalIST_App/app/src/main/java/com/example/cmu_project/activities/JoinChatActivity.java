package com.example.cmu_project.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.cmu_project.grpc_tasks.GetAvailableChatsToJoinGrpcTask;
import com.example.cmu_project.grpc_tasks.JoinChatGrpcTask;
import com.example.cmu_project.helpers.GlobalVariableHelper;
import com.example.cmu_project.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.concurrent.atomic.AtomicReference;

public class JoinChatActivity extends AppCompatActivity {

    ListView chatsList;
    String currentChatToJoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_chat);

        Double[] user_location = getUserCoord();
        double user_latitude = user_location[0];
        double user_longitude = user_location[1];

        System.out.println("Location is: " + user_latitude + "/" + user_longitude);

        new GetAvailableChatsToJoinGrpcTask(this, user_latitude, user_longitude).execute(((GlobalVariableHelper) this.getApplication()).getUsername());

        chatsList = findViewById(R.id.chats_list);

        chatsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                currentChatToJoin = chatsList.getItemAtPosition(i).toString();

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Double[] user_location = getUserCoord();
        double user_latitude = user_location[0];
        double user_longitude = user_location[1];
        new GetAvailableChatsToJoinGrpcTask(this, user_latitude, user_longitude).execute(((GlobalVariableHelper) this.getApplication()).getUsername());
        chatsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                currentChatToJoin = chatsList.getItemAtPosition(i).toString();

            }
        });

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(JoinChatActivity.this);
        String language = prefs.getString("language", "English");

        if (language.equals("Português")) {
            setPortuguese();
        } else if (language.equals("English")) {
            setEnglish();
        }
    }

    public void join(View view) {

        new JoinChatGrpcTask(this, currentChatToJoin).execute(((GlobalVariableHelper) this.getApplication()).getUsername());

    }

    public Double[] getUserCoord() {

        FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        AtomicReference<Double> userX = new AtomicReference<>((double) 0);
        AtomicReference<Double> userY = new AtomicReference<>((double) 0);
        Double[] userCoordinates = new Double[2];
        userCoordinates[0] = userX.get();
        userCoordinates[1] = userY.get();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return userCoordinates;
        }

        mFusedLocationClient.getLastLocation().addOnCompleteListener(location -> {
            if (location.getResult() != null) {
                userX.set(location.getResult().getLatitude());
                userY.set(location.getResult().getLongitude());

                userCoordinates[0] = userX.get();
                userCoordinates[1] = userY.get();
            }
        });

        return userCoordinates;
    }

    private void setEnglish() {
        Button join_chat = findViewById(R.id.join_chat);
        join_chat.setText(R.string.join);
    }

    private void setPortuguese() {
        Button join_chat = findViewById(R.id.join_chat);
        join_chat.setText(R.string.juntar);
    }

}