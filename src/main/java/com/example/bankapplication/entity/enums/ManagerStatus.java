package com.example.bankapplication.entity.enums;

public enum ManagerStatus {
    WORKING ("WORKING"),
    FIRED ("FIRED");

    private final String value;

    ManagerStatus(String value) {
        this.value = value;
    }
}
