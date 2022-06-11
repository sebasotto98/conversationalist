package com.example.cmu_project.helpers;

import android.app.Application;

import io.grpc.ManagedChannelBuilder;
import io.grpc.examples.backendserver.ServerGrpc;

public class GlobalVariableHelper extends Application {

    private String currentChatroomName = null;
    private String username = null;
    private ServerGrpc.ServerBlockingStub ServerBlockingStub = ServerGrpc.newBlockingStub(ManagedChannelBuilder.forAddress("192.168.56.1",50051).usePlaintext().build());

    private final DBHelper db = new DBHelper(this);

    public String getCurrentChatroomName() {
        return currentChatroomName;
    }

    public void setCurrentChatroomName(String currentChatroomName) {
        this.currentChatroomName = currentChatroomName;
    }

    public ServerGrpc.ServerBlockingStub getStub() {
        return this.ServerBlockingStub;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public DBHelper getDb() {
        return db;
    }

}