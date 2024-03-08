package com.example.bankapplication.exceptions;

public class ManagerCreationException extends RuntimeException{
    public ManagerCreationException(String message) {
        super("Manager creation error: " + message);
    }
}
