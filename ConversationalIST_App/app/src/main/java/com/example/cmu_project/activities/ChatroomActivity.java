package com.example.cmu_project.activities;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cmu_project.grpc_tasks.GetAllUserChatsGrpcTask;
import com.example.cmu_project.helpers.GlobalVariableHelper;
import com.example.cmu_project.R;
import com.example.cmu_project.services.FetchDataService;

public class ChatroomActivity extends AppCompatActivity {

    TextView tx1;
    Button chatIcon;
    ListView my_chats_list;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);

        //chatIcon = (Button) findViewById(R.id.chat_icon);

        tx1 = (TextView) findViewById(R.id.username);
        username = getIntent().getExtras().getString("username");
        String welcomeMessage = "Welcome " + username + "!";
        tx1.setText(welcomeMessage);
        tx1.setVisibility(View.VISIBLE);

        ((GlobalVariableHelper) this.getApplication()).setUsername(username);

        my_chats_list = (ListView) findViewById(R.id.my_chat_list);
        //new GetAllUserChatsGrpcTask(this,my_chats_list,username).execute();

        //only start service if it is not running
        if(!isMyServiceRunning(FetchDataService.class)){
            startService(new Intent(this, FetchDataService.class));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        new GetAllUserChatsGrpcTask(this,my_chats_list,username).execute();
    }

    public void create_chatroom(View view) {
        Intent myIntent = new Intent(ChatroomActivity.this, CreateChatActivity.class);
        ChatroomActivity.this.startActivity(myIntent);
    }

    public void join_chatroom(View view) {
        Intent myIntent = new Intent(ChatroomActivity.this, JoinChatActivity.class);
        ChatroomActivity.this.startActivity(myIntent);
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
