package com.example.cmu_project.activities;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
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

    TextView userNameTextView;
    ListView userChatsList;
    String username;
    Menu menu = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);

        userNameTextView = findViewById(R.id.username);
        username = getIntent().getExtras().getString("username");
        String welcomeMessage = "Welcome " + username + "!";
        userNameTextView.setText(welcomeMessage);
        userNameTextView.setVisibility(View.VISIBLE);

        ((GlobalVariableHelper) this.getApplication()).setUsername(username);

        userChatsList = findViewById(R.id.my_chat_list);

        //only start service if it is not running
        if (!isMyServiceRunning(FetchDataService.class)) {
            startService(new Intent(this, FetchDataService.class));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        new GetAllUserChatsGrpcTask(this, userChatsList, username).execute();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_options, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.language) {
            Intent myIntent = new Intent(ChatroomActivity.this, LanguageActivity.class);
            ChatroomActivity.this.startActivity(myIntent);
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ChatroomActivity.this);
        String current_language = prefs.getString("language", "English");
        if (current_language.equals("Português")) {
            setPortuguese();
        } else if (current_language.equals("English")) {
            setEnglish();
        }
        return super.onPrepareOptionsMenu(menu);
    }

    private void setEnglish() {
        TextView titleTextView = findViewById(R.id.activity_title);
        titleTextView.setText(R.string.chatrooms);
        Button joinNewChatroomButton = findViewById(R.id.join_new_chatroom_button);
        joinNewChatroomButton.setText(R.string.join_new_chatroom);
        Button createNewChatroomButton = findViewById(R.id.create_new_chatroom_button);
        createNewChatroomButton.setText(R.string.create_new_chatroom);
        (menu.findItem(R.id.language)).setTitle(R.string.language);

    }

    private void setPortuguese() {
        TextView titleTextView = findViewById(R.id.activity_title);
        titleTextView.setText(R.string.salas_de_conversacao);
        Button joinNewChatroomButton = findViewById(R.id.join_new_chatroom_button);
        joinNewChatroomButton.setText(R.string.entrar_em_nova_sala_de_conversacao);
        Button createNewChatroomButton = findViewById(R.id.create_new_chatroom_button);
        createNewChatroomButton.setText(R.string.criar_nova_sala_de_conversacao);
        (menu.findItem(R.id.language)).setTitle(R.string.linguagem);
    }
}