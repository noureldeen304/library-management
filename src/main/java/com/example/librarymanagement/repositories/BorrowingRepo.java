package com.example.librarymanagement.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.librarymanagement.entities.Book;
import com.example.librarymanagement.entities.BorrowingRecord;
import com.example.librarymanagement.entities.Patron;

@Repository
public interface BorrowingRepo extends JpaRepository<BorrowingRecord, Long> {

    Optional<BorrowingRecord> findByBookAndPatron(Book book, Patron patron);

    boolean existsByBookAndPatron(Book book, Patron patron);

    
}
