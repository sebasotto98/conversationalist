package com.example.cmu_project;

import android.app.Application;

public class GlobalVariables extends Application {

    private String currentChatroomName = null;


    public String getCurrentChatroomName() {
        return currentChatroomName;
    }

    public void setCurrentChatroomName(String currentChatroomName) {
        this.currentChatroomName = currentChatroomName;
    }
}
