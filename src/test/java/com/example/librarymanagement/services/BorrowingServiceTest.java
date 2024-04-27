package com.example.librarymanagement.services;

import com.example.librarymanagement.entities.Book;
import com.example.librarymanagement.entities.BorrowingRecord;
import com.example.librarymanagement.entities.Patron;
import com.example.librarymanagement.exceptions.*;
import com.example.librarymanagement.repositories.BookRepo;
import com.example.librarymanagement.repositories.BorrowingRepo;
import com.example.librarymanagement.repositories.PatronRepo;
import com.example.librarymanagement.services.impl.BorrowingServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BorrowingServiceTest {

    @Mock
    private BorrowingRepo borrowingRepo;

    @Mock
    private PatronRepo patronRepo;

    @Mock
    private BookRepo bookRepo;

    @Mock
    private BorrowingRecord borrowingRecord;

    @InjectMocks
    private BorrowingServiceImpl borrowingService;

    private Book book;
    private Patron patron;

    @BeforeEach
    void setUp() {
        borrowingRepo = mock(BorrowingRepo.class);
        patronRepo = mock(PatronRepo.class);
        bookRepo = mock(BookRepo.class);
        borrowingRecord = mock(BorrowingRecord.class);
        borrowingService = new BorrowingServiceImpl(borrowingRepo, patronRepo, bookRepo);

        book = new Book();
        book.setId(1L);
        patron = new Patron();
        patron.setId(1L);
    }

    @Test
    void borrowBook_Success() {
        when(bookRepo.findById(book.getId())).thenReturn(Optional.of(book));
        when(patronRepo.findById(patron.getId())).thenReturn(Optional.of(patron));
        when(borrowingRepo.existsByBookAndPatron(book, patron)).thenReturn(false);

        borrowingService.borrowBook(book.getId(), patron.getId());

        verify(borrowingRepo, times(1)).save(any(BorrowingRecord.class));
    }

    @Test
    void borrowBook_BookNotFoundException() {
        when(bookRepo.findById(book.getId())).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> borrowingService.borrowBook(book.getId(), patron.getId()));
    }

    @Test
    void borrowBook_PatronNotFoundException() {
        when(bookRepo.findById(book.getId())).thenReturn(Optional.of(book));
        when(patronRepo.findById(patron.getId())).thenReturn(Optional.empty());

        assertThrows(PatronNotFoundException.class, () -> borrowingService.borrowBook(book.getId(), patron.getId()));
    }

    @Test
    void borrowBook_BookAleadyBorrowedException() {
        when(bookRepo.findById(book.getId())).thenReturn(Optional.of(book));
        when(patronRepo.findById(patron.getId())).thenReturn(Optional.of(patron));
        when(borrowingRepo.existsByBookAndPatron(book, patron)).thenReturn(true);

        assertThrows(BookAleadyBorrowedException.class,
                () -> borrowingService.borrowBook(book.getId(), patron.getId()));
    }

    @Test
    void returnBook_Success() {
        BorrowingRecord borrowingRecord = new BorrowingRecord();
        when(bookRepo.findById(book.getId())).thenReturn(Optional.of(book));
        when(patronRepo.findById(patron.getId())).thenReturn(Optional.of(patron));
        when(borrowingRepo.findByBookAndPatron(book, patron)).thenReturn(Optional.of(borrowingRecord));

        borrowingService.returnBook(book.getId(), patron.getId());

        verify(borrowingRepo, times(1)).save(any(BorrowingRecord.class));
    }

    @Test
    void returnBook_BookNotFoundException() {
        when(bookRepo.findById(book.getId())).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> borrowingService.returnBook(book.getId(), patron.getId()));
    }

    @Test
    void returnBook_PatronNotFoundException() {
        when(bookRepo.findById(book.getId())).thenReturn(Optional.of(book));
        when(patronRepo.findById(patron.getId())).thenReturn(Optional.empty());

        assertThrows(PatronNotFoundException.class, () -> borrowingService.returnBook(book.getId(), patron.getId()));
    }

}
