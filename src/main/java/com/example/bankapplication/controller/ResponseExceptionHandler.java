package com.example.bankapplication.controller;

import com.example.bankapplication.dto.ErrorDto;
import com.example.bankapplication.exceptions.EntityNotFoundException;
import com.example.bankapplication.exceptions.ManagerCreationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ResponseExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDto> handleRuntimeException(RuntimeException exception) {
        ErrorDto errorDto = new ErrorDto(
                HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                exception.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ManagerCreationException.class)
    public ResponseEntity<ErrorDto> handleManagerCreationException(ManagerCreationException exception) {
        ErrorDto errorDto = new ErrorDto(
                HttpStatus.BAD_REQUEST.toString(),
                exception.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDto> handleManagerNotFoundException(EntityNotFoundException exception) {
        ErrorDto errorDto = new ErrorDto(
                HttpStatus.BAD_REQUEST.toString(),
                exception.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

}
