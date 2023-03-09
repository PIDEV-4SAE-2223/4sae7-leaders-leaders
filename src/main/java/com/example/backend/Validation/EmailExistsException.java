package com.example.backend.Validation;

public class EmailExistsException extends Exception{
    public EmailExistsException(String message) {
        super(message);
    }
}
