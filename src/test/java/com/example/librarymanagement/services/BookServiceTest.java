package com.example.librarymanagement.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.BeanUtils;
import com.example.librarymanagement.entities.Book;
import com.example.librarymanagement.exceptions.BookAlreayExistsException;
import com.example.librarymanagement.exceptions.BookNotFoundException;
import com.example.librarymanagement.repositories.BookRepo;
import com.example.librarymanagement.services.impl.BookServiceImpl;

public class BookServiceTest {
    @Mock
    private BookRepo bookRepo;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book book;

    @BeforeEach
    void setUp() {
        bookRepo = mock(BookRepo.class);
        bookService = new BookServiceImpl(bookRepo);
        book = Book.builder()
                .id(1L)
                .title("Test Book")
                .author("Test Author")
                .isbn("978-3-16-148410-0")
                .publicationYear(LocalDate.now())
                .build();
    }

    @Test
    void getAllBooks() {
        List<Book> books = Arrays.asList(book);
        when(bookRepo.findAll()).thenReturn(books);

        List<Book> result = bookService.getAllBooks();

        assertEquals(books, result);
    }

    @Test
    void getBookById_Success() {
        Long id = 1L;
        when(bookRepo.findById(id)).thenReturn(Optional.of(book));

        Book result = bookService.getBookById(id);

        assertEquals(book, result);
    }

    @Test
    void getBookById_BookNotFound() {
        Long id = 1L;
        when(bookRepo.findById(id)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.getBookById(id));
    }

    @Test
    void addBook_Success() {
        when(bookRepo.existsByIsbn(book.getIsbn())).thenReturn(false);
        when(bookRepo.save(book)).thenReturn(book);

        Book result = bookService.addBook(book);

        assertEquals(book, result);
    }

    @Test
    void addBook_BookAlreadyExists() {
        when(bookRepo.existsByIsbn(book.getIsbn())).thenReturn(true);

        assertThrows(BookAlreayExistsException.class, () -> bookService.addBook(book));
    }

    @Test
    void updateBookById_Success() {
        Long id = 1L;
        Book newBook = new Book();
        BeanUtils.copyProperties(book, newBook);
        newBook.setTitle("New Title");
        when(bookRepo.findById(id)).thenReturn(Optional.of(book));
        when(bookRepo.save(newBook)).thenReturn(newBook);

        Book result = bookService.updateBookById(id, newBook);

        assertEquals(newBook.getTitle(), result.getTitle());
    }

    @Test
    void updateBookById_BookNotFound() {
        Long id = 1L;
        Book newBook = new Book();
        when(bookRepo.findById(id)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.updateBookById(id, newBook));
    }

    @Test
    void deleteBookById_Success() {
        Long id = 1L;
        when(bookRepo.existsById(id)).thenReturn(true);

        assertDoesNotThrow(() -> bookService.deleteBookById(id));
    }

    @Test
    void deleteBookById_BookNotFound() {
        Long id = 1L;
        when(bookRepo.existsById(id)).thenReturn(false);

        assertThrows(BookNotFoundException.class, () -> bookService.deleteBookById(id));
    }
}
