package com.example.backend.Validation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class InvalidPasswordException extends Throwable {
    public InvalidPasswordException(String invalidPassword) {
        super(invalidPassword);

    }
}
