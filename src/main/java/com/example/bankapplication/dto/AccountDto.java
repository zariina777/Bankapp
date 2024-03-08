package com.example.bankapplication.dto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class AccountDto {
    String id;
    ClientDto client;
    String name;
    String type;
    String status;
    BigDecimal balance;
    String currencyCode;
    String createdAt;
    String updatedAt;
}
