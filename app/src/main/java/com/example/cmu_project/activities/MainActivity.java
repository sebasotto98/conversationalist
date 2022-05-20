package com.example.cmu_project.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cmu_project.R;

public class MainActivity extends AppCompatActivity {

    TextView tx1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tx1 = (TextView) findViewById(R.id.username);
        String username = getIntent().getExtras().getString("username");
        String welcomeMessage = "Welcome " + username + "!";
        tx1.setText(welcomeMessage);
        tx1.setVisibility(View.VISIBLE);
    }

}
