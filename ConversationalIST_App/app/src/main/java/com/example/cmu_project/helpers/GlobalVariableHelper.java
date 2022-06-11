package com.example.cmu_project.helpers;

import android.app.Application;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.examples.backendserver.ServerGrpc;

public class GlobalVariableHelper extends Application {

    private String currentChatroomName = null;
    private String username = null;
    private final ManagedChannel channel = ManagedChannelBuilder.forAddress("192.168.1.135",50051).usePlaintext().build();
    private final ServerGrpc.ServerBlockingStub ServerBlockingStub = ServerGrpc.newBlockingStub(channel);
    private final  ServerGrpc.ServerStub nonBlockingStub = ServerGrpc.newStub(channel);

    private final DBHelper db = new DBHelper(this);

    public String getCurrentChatroomName() {
        return currentChatroomName;
    }

    public void setCurrentChatroomName(String currentChatroomName) {
        this.currentChatroomName = currentChatroomName;
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

    public ServerGrpc.ServerBlockingStub getServerBlockingStub() {
        return ServerBlockingStub;
    }

    public ServerGrpc.ServerStub getNonBlockingStub() {
        return nonBlockingStub;
    }
}