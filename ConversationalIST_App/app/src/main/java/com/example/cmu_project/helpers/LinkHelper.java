package com.example.cmu_project.helpers;

public class LinkHelper {

    private boolean flag;
    private String chat_to_go;

    public LinkHelper() {
        this.flag = false;
        this.chat_to_go = null;
    }

    public boolean getFlag() {
        return this.flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getChat_to_go() {
        return this.chat_to_go;
    }

    public void setChat_to_go(String chat_to_go) {
        this.chat_to_go = chat_to_go;
    }

    public void setToEmpty() {
        this.flag = false;
        this.chat_to_go = null;
    }

}