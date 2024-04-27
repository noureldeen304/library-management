package com.example.librarymanagement.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.librarymanagement.entities.Patron;
import com.example.librarymanagement.services.impl.PatronServiceImpl;

@SpringBootTest
public class PatronControllerTest {
    @Mock
    private PatronServiceImpl patronService;

    @InjectMocks
    private PatronController patronController;

    private Patron patron;

    @BeforeEach
    void setUp() {
        patron = new Patron(1L, "John Doe", "1234567890", "john.jan5@gmail.com");
    }

    @Test
    void getAllPatrons() {

        List<Patron> patrons = Arrays.asList(patron);
        when(patronService.getAllPatrons()).thenReturn(patrons);

        ResponseEntity<List<Patron>> response = patronController.getAllPatrons();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(patrons, response.getBody());
    }

    @Test
    void getPatronById() {

        Long id = 1L;
        when(patronService.getPatronById(id)).thenReturn(patron);

        ResponseEntity<Patron> response = patronController.getPatronById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(patron, response.getBody());
    }

    @Test
    void addPatron() {

        when(patronService.addPatron(patron)).thenReturn(patron);

        ResponseEntity<Patron> response = patronController.addPatron(patron);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(patron, response.getBody());
    }

    @Test
    void updatePatronById() {

        Long id = 1L;
        when(patronService.updatePatronById(id, patron)).thenReturn(patron);

        ResponseEntity<Patron> response = patronController.updatePatronById(id, patron);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(patron, response.getBody());
    }

    @Test
    void deletePatron() {

        Long id = 1L;
        doNothing().when(patronService).deletePatronById(id);

        ResponseEntity<Void> response = patronController.deletePatron(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
