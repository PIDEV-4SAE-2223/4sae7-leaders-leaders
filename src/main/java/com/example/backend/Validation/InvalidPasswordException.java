package com.example.backend.Validation;

public class InvalidPasswordException extends Throwable {
    public InvalidPasswordException(String invalidPassword) {
        super(invalidPassword);

    }
}
