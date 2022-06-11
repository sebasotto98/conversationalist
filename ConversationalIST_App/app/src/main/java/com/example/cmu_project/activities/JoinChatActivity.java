package com.example.cmu_project.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmu_project.GlobalVariables;
import com.example.cmu_project.R;

import java.util.List;

import io.grpc.examples.backendserver.JoinChatReply;
import io.grpc.examples.backendserver.JoinChatRequest;
import io.grpc.examples.backendserver.JoinableChatsReply;
import io.grpc.examples.backendserver.JoinableChatsRequest;
import io.grpc.examples.backendserver.ServerGrpc;

public class JoinChatActivity extends AppCompatActivity {

    ListView chats_list;
    List<String> current_chats;
    String current_chat_to_join;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_chat);

        current_chats = this.getAvailableChats();

        chats_list = (ListView) findViewById(R.id.chats_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,current_chats);
        chats_list.setAdapter(adapter);
        chats_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                current_chat_to_join = chats_list.getItemAtPosition(i).toString();

            }
        });

    }



    public void join(View view) {

        ServerGrpc.ServerBlockingStub ServerBlockingStub = ((GlobalVariables) this.getApplication()).getStub();
        JoinChatRequest request = JoinChatRequest.newBuilder().setUser(((GlobalVariables) this.getApplication()).getUsername()).setChatName(current_chat_to_join).build();
        JoinChatReply reply = ServerBlockingStub.joinChat(request);

        System.out.println(reply.getAck());

        //jump to the chat activity
        ((GlobalVariables) this.getApplication()).setCurrentChatroomName(current_chat_to_join);
        Intent myIntent = new Intent(JoinChatActivity.this, ChatActivity.class);
        JoinChatActivity.this.startActivity(myIntent);

    }

    private List<String> getAvailableChats() {

        List<String> ret_list;
        ServerGrpc.ServerBlockingStub ServerBlockingStub = ((GlobalVariables) this.getApplication()).getStub();

        //send request and get response
        JoinableChatsRequest request = JoinableChatsRequest.newBuilder().setUser(((GlobalVariables) this.getApplication()).getUsername()).build();
        JoinableChatsReply response = ServerBlockingStub.getJoinableChats(request);
        ret_list = response.getChatsList();


        return ret_list;
    }

}