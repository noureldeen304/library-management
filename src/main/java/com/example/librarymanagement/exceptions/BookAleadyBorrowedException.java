package com.example.librarymanagement.exceptions;

public class BookAleadyBorrowedException extends RuntimeException {
    public BookAleadyBorrowedException(String message) {
        super(message);
    }
}