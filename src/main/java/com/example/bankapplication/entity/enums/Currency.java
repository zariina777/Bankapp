package com.example.bankapplication.entity.enums;

public enum Currency {
    EUR("EUR"),
    USD("USD"),
    PLN("PLN");
    private final String value;

    Currency(String value) {
        this.value = value;
    }
}
