package com.example.bankapplication.entity.enums;

public enum ClientStatus {
    REGULAR("REGULAR"),
    VIP("VIP"),
    IS_BLOCKED("IS_BLOCKED");
    private final String value;

    ClientStatus(String value) {
        this.value = value;
    }
}
