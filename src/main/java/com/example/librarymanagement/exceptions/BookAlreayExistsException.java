package com.example.librarymanagement.exceptions;

public class BookAlreayExistsException extends RuntimeException {
    public BookAlreayExistsException(String message) {
        super(message);
    }
}