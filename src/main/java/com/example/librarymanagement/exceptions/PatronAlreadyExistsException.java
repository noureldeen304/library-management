package com.example.librarymanagement.exceptions;

public class PatronAlreadyExistsException extends RuntimeException {
    public PatronAlreadyExistsException(String message) {
        super(message);
    }
}