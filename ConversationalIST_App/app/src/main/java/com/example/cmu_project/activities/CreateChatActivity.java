package com.example.cmu_project.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.cmu_project.R;

import io.grpc.ManagedChannelBuilder;
import io.grpc.examples.backendserver.CreateChatReply;
import io.grpc.examples.backendserver.CreateChatRequest;
import io.grpc.examples.backendserver.ServerGrpc;

public class CreateChatActivity extends AppCompatActivity {

    Button sendRequest;
    EditText chat_name, type_of_chat;

    private ServerGrpc.ServerBlockingStub ServerBlockingStub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_chat);

        //Buttons
        sendRequest = (Button) findViewById(R.id.create_chatroom);

        //TextFields
        chat_name = (EditText) findViewById(R.id.chat_name);
        type_of_chat = (EditText) findViewById(R.id.type_of_chat);

    }

    public void create(View view) {


        ServerBlockingStub = ServerGrpc.newBlockingStub(ManagedChannelBuilder.forAddress("192.168.1.135", 50051).usePlaintext().build());

        //Send Requests
        CreateChatRequest request = CreateChatRequest.newBuilder().setChatroomName("AndroidRoom").setUser("testerAndroid").build();
        CreateChatReply reply = ServerBlockingStub.createChat(request);
        System.out.println(reply.getAck());





        //send the request to the server and jump to the chat activity
    }




}