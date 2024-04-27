package com.example.librarymanagement.exceptions;

public class LibrarianNotFoundException extends RuntimeException {
    public LibrarianNotFoundException(String message) {
        super(message);
    }
}