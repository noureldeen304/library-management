package com.example.librarymanagement.services;

public interface BorrowingService {
    public void borrowBook(Long bookId, Long patronId);

    public void returnBook(Long bookId, Long patronId);
}
