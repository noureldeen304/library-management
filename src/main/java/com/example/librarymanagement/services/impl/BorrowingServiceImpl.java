package com.example.librarymanagement.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.librarymanagement.entities.Book;
import com.example.librarymanagement.entities.BorrowingRecord;
import com.example.librarymanagement.entities.Patron;
import com.example.librarymanagement.exceptions.BookAleadyBorrowedException;
import com.example.librarymanagement.exceptions.BookAleadyReturnedException;
import com.example.librarymanagement.exceptions.BookNotFoundException;
import com.example.librarymanagement.exceptions.PatronNotFoundException;
import com.example.librarymanagement.exceptions.RecordNotFoundException;
import com.example.librarymanagement.repositories.BookRepo;
import com.example.librarymanagement.repositories.BorrowingRepo;
import com.example.librarymanagement.repositories.PatronRepo;
import com.example.librarymanagement.services.BorrowingService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class BorrowingServiceImpl implements BorrowingService{

        @Autowired
        private BorrowingRepo borrowingRepo;

        @Autowired
        private PatronRepo patronRepo;

        @Autowired
        private BookRepo bookRepo;

        public void borrowBook(Long bookId, Long patronId) {

                Book book = bookRepo.findById(bookId)
                                .orElseThrow(() -> new BookNotFoundException(
                                                "Book with id {" + bookId + "} does not exist."));

                Patron patron = patronRepo.findById(patronId)
                                .orElseThrow(() -> new PatronNotFoundException(
                                                "Patron with id {" + patronId + "} does not exist."));

                if (borrowingRepo.existsByBookAndPatron(book, patron)) {
                        throw new BookAleadyBorrowedException(
                                        "Patron with ID {" + patronId + "}" + " already borrowed the book with ID {"
                                                        + bookId + "}");
                }

                BorrowingRecord bRecord = BorrowingRecord.builder()
                                .book(book)
                                .patron(patron)
                                .borrowedDate(LocalDate.now())
                                .build();

                borrowingRepo.save(bRecord);
        }

        @Transactional
        public void returnBook(Long bookId, Long patronId) {
                Book book = bookRepo.findById(bookId)
                                .orElseThrow(() -> new BookNotFoundException(
                                                "Book with id {" + bookId + "} does not exist."));

                Patron patron = patronRepo.findById(patronId)
                                .orElseThrow(() -> new PatronNotFoundException(
                                                "Patron with id {" + patronId + "} does not exist."));

                BorrowingRecord borrowingRecord = borrowingRepo.findByBookAndPatron(book, patron)
                                .orElseThrow(() -> new RecordNotFoundException(
                                                "Patron with ID {" + patronId + "}"
                                                                + "did not borrowed the book with ID {" + bookId
                                                                + "}"));

                if (borrowingRecord.isReturned()) {
                        throw new BookAleadyReturnedException(
                                        "Patron with ID {" + patronId + "}" + " already returned the book with ID {"
                                                        + bookId
                                                        + "} to the library on " + borrowingRecord.getReturnDate());
                }

                borrowingRecord.setReturnDate(LocalDate.now());
                borrowingRepo.save(borrowingRecord);
        }

}