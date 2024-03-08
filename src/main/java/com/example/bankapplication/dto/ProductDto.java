package com.example.bankapplication.dto;

import lombok.Value;

@Value
public class ProductDto {
    String id;
    String name;
    String status;
    String currencyCode;
}
