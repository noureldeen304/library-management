package com.example.librarymanagement.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.librarymanagement.services.impl.BorrowingServiceImpl;

@SpringBootTest
public class BorrowingControllerTest {
    @Mock
    private BorrowingServiceImpl borrowingService;

    @InjectMocks
    private BorrowingController borrowingController;


    @Test
    void borrowBook_Success() {
        Long bookId = 1L;
        Long patronId = 1L;
        doNothing().when(borrowingService).borrowBook(bookId, patronId);

        ResponseEntity<String> responseEntity = borrowingController.borrowBook(bookId, patronId);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("Book borrowed successfully.", responseEntity.getBody());
        verify(borrowingService, times(1)).borrowBook(bookId, patronId);
    }

    @Test
    void returnBook_Success() {
        Long bookId = 1L;
        Long patronId = 1L;
        doNothing().when(borrowingService).returnBook(bookId, patronId);

        ResponseEntity<Void> responseEntity = borrowingController.returnBook(bookId, patronId);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(borrowingService, times(1)).returnBook(bookId, patronId);
    }
}
