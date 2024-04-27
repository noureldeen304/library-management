package com.example.librarymanagement.services.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.librarymanagement.entities.Book;
import com.example.librarymanagement.exceptions.BookAlreayExistsException;
import com.example.librarymanagement.exceptions.BookNotFoundException;
import com.example.librarymanagement.repositories.BookRepo;
import com.example.librarymanagement.services.BookService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.util.List;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepo bookRepo;

    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }

    public Book getBookById(Long id) {
        Book book = bookRepo.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with id {" + id + "} does not exist."));
        return book;
    }

    public Book addBook(Book book) {
        if (bookRepo.existsByIsbn(book.getIsbn())) {
            throw new BookAlreayExistsException("Book with ISBN {" + book.getIsbn() + "} already exists.");
        }
        Book addedBook = bookRepo.save(book);
        return addedBook;
    }

    @Transactional
    public Book updateBookById(Long id, Book newBook) {
        Book book = bookRepo.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with id {" + id + "} does not exist."));

        BeanUtils.copyProperties(newBook, book, "id");
        return book;
    }

    public void deleteBookById(Long id) {
        if (!bookRepo.existsById(id)) {
            throw new BookNotFoundException("Book with id {" + id + "} does not exist.");
        }
        bookRepo.deleteById(id);
    }
}
