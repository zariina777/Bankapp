package com.example.bankapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorDto {
    private final String statusHTTP;
    private final String errorMessage;
}
