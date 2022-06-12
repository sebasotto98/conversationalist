package com.example.cmu_project.enums;

public enum MessageType {
        TEXT(0), PHOTO(1), GEOLOCATION(2);

    private final int value;

    MessageType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static MessageType getByValue(int value) {
        MessageType[] messageTypes = MessageType.values();

        for (MessageType m : messageTypes) {
            if(m.getValue() == value) {
                return m;
            }
        }

        return null;
    }
}