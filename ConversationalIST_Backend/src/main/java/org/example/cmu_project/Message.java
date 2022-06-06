package org.example.cmu_project;

import java.sql.Timestamp;

public class Message {

    private final String username;
    private final Timestamp timestamp;
    private final String data;

    public Message(String username, Timestamp timestamp, String data){
            this.username = username;
            this.data = data;
            this.timestamp = timestamp;
    }

    public String getUsername() {
        return username;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getData() {
        return data;
    }
}
