package com.example.bankapplication.exceptions;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String message) {
        super("Entity " + message + " is not found.");
    }
}
