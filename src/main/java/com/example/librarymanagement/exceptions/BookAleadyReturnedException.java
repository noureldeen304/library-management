package com.example.librarymanagement.exceptions;

public class BookAleadyReturnedException extends RuntimeException {
    public BookAleadyReturnedException(String message) {
        super(message);
    }
}