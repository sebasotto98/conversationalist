package com.example.cmu_project.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.cmu_project.R;
import com.example.cmu_project.grpc_tasks.GetChatMembersGrpcTask;

public class ManageChatActivity extends AppCompatActivity {

    ListView chat_members;
    String chat_name;
    String user_to_remove;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_chat);

        chat_name = getIntent().getExtras().getString("chat_name");

        chat_members = findViewById(R.id.chat_members_list);

        new GetChatMembersGrpcTask(this,chat_members).execute(chat_name);

        chat_members.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                user_to_remove = chat_members.getItemAtPosition(i).toString();

            }
        });

    }

    public void add_user(View view) {

    }


}