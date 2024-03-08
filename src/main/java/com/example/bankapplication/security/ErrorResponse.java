package com.example.bankapplication.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@Setter
public class ErrorResponse {
    HttpStatus httpStatus;
    String message;
}
