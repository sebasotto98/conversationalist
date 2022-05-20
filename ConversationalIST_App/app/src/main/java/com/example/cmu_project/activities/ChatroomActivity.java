package com.example.cmu_project.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cmu_project.R;

public class ChatroomActivity extends AppCompatActivity {

    TextView tx1;
    Button chatIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);

        chatIcon = (Button) findViewById(R.id.chat_icon);

        tx1 = (TextView) findViewById(R.id.username);
        String username = getIntent().getExtras().getString("username");
        String welcomeMessage = "Welcome " + username + "!";
        tx1.setText(welcomeMessage);
        tx1.setVisibility(View.VISIBLE);
    }

    public void enterChat(View view) {
        Intent myIntent = new Intent(ChatroomActivity.this, ChatActivity.class);
        ChatroomActivity.this.startActivity(myIntent);
    }

}
