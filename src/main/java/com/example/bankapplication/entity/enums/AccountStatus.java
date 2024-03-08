package com.example.bankapplication.entity.enums;

public enum AccountStatus {
    ACTIVE("REGULAR"),
    VIP("VIP"),
    IS_BLOCKED("IS_BLOCKED"),
    FOR_DELETION("FOR_DELETION");
    private final String value;

    AccountStatus(String value) {
        this.value = value;
    }
}
