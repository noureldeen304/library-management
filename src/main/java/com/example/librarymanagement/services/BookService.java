package com.example.librarymanagement.services;

import java.util.List;

import com.example.librarymanagement.entities.Book;

public interface BookService {
    public List<Book> getAllBooks();

    public Book getBookById(Long id);

    public Book addBook(Book book);

    public Book updateBookById(Long id, Book newBook);

    public void deleteBookById(Long id);
}
