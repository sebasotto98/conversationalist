package com.example.cmu_project;

import android.app.Application;

public class GlobalVariables extends Application {

    private String currentChatroomName = null;
    private String username = null;

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

}
