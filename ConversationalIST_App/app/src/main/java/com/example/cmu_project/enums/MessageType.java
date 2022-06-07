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
}