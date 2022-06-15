package com.example.cmu_project.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import java.util.List;

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


        new GetAvailableChatsToJoinGrpcTask(this).execute(((GlobalVariableHelper) this.getApplication()).getUsername());

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
        new GetAvailableChatsToJoinGrpcTask(this).execute(((GlobalVariableHelper) this.getApplication()).getUsername());
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


}