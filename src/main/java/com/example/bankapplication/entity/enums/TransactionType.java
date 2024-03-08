package com.example.bankapplication.entity.enums;

public enum TransactionType {
    TRANSFER("TRANSFER"),
    PAYMENT("PAYMENT"),
    CASH("CASH"),
    DEPOSIT("DEPOSIT");

    private final String value;
    TransactionType(String value) {
        this.value = value;
    }
}
