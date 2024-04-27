package com.example.librarymanagement.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.BeanUtils;

import com.example.librarymanagement.entities.Patron;
import com.example.librarymanagement.exceptions.PatronAlreadyExistsException;
import com.example.librarymanagement.exceptions.PatronNotFoundException;
import com.example.librarymanagement.repositories.PatronRepo;
import com.example.librarymanagement.services.impl.PatronServiceImpl;

public class PatronServiceTest {
    @Mock
    private PatronRepo patronRepo;

    @InjectMocks
    private PatronServiceImpl patronService;

    private Patron patron;

    @BeforeEach
    void setUp() {
        patronRepo = mock(PatronRepo.class);
        patronService = new PatronServiceImpl(patronRepo);
        patron = new Patron(1L, "John Doe", "1234567890", "john.jan5@gmail.com");
    }

    @Test
    void getAllPatrons() {
        List<Patron> expectedPatrons = Arrays.asList(patron);
        when(patronRepo.findAll()).thenReturn(expectedPatrons);

        List<Patron> actualPatrons = patronService.getAllPatrons();

        assertEquals(expectedPatrons, actualPatrons);
    }

    @Test
    void getPatronById_PatronExists() {
        Long id = 1L;
        Patron expectedPatron = patron;
        when(patronRepo.findById(id)).thenReturn(Optional.of(patron));

        Patron actualPatron = patronService.getPatronById(id);

        assertEquals(expectedPatron, actualPatron);
    }

    @Test
    void getPatronById_PatronNotFound() {
        Long id = 1L;
        when(patronRepo.findById(id)).thenReturn(Optional.empty());

        assertThrows(PatronNotFoundException.class, () -> patronService.getPatronById(id));
    }

    @Test
    void addPatron_PatronDoesNotExist() {
        Patron newPatron = new Patron(2L, "Alice Smith", "9876543210", "alice.smith@example.com");
        when(patronRepo.existsByPhoneNumber(newPatron.getPhoneNumber())).thenReturn(false);
        when(patronRepo.existsByEmailAddress(newPatron.getEmailAddress())).thenReturn(false);
        when(patronRepo.save(newPatron)).thenReturn(newPatron);

        Patron savedPatron = patronService.addPatron(newPatron);

        assertEquals(newPatron, savedPatron);
    }

    @Test
    void addPatron_PatronExistsByPhoneNumber() {
        Patron newPatron = new Patron(2L, "Alice Smith", "9876543210", "alice.smith@example.com");
        when(patronRepo.existsByPhoneNumber(newPatron.getPhoneNumber())).thenReturn(true);

        assertThrows(PatronAlreadyExistsException.class, () -> patronService.addPatron(newPatron));
        verify(patronRepo, never()).existsByEmailAddress(anyString());
    }

    @Test
    void addPatron_PatronExistsByEmailAddress() {
        Patron newPatron = new Patron(2L, "Alice Smith", "9876543210", "alice.smith@example.com");
        when(patronRepo.existsByPhoneNumber(newPatron.getPhoneNumber())).thenReturn(false);
        when(patronRepo.existsByEmailAddress(newPatron.getEmailAddress())).thenReturn(true);

        assertThrows(PatronAlreadyExistsException.class, () -> patronService.addPatron(newPatron));
        verify(patronRepo, times(1)).existsByPhoneNumber(anyString());
    }

    @Test
    void updatePatronById_PatronExists() {
        Long id = 1L;
        Patron updatedPatron = new Patron();
        BeanUtils.copyProperties(patron, updatedPatron);
        updatedPatron.setId(id);
        when(patronRepo.findById(id)).thenReturn(Optional.of(patron));
        when(patronRepo.existsByPhoneNumberForOtherPatron(updatedPatron.getPhoneNumber(), id)).thenReturn(false);
        when(patronRepo.existsByEmailAddressForOtherPatron(updatedPatron.getEmailAddress(), id)).thenReturn(false);

        Patron result = patronService.updatePatronById(id, patron);

        assertEquals(updatedPatron, result);
    }

    @Test
    void updatePatronById_PatronNotFound() {
        Long id = 1L;
        when(patronRepo.findById(id)).thenReturn(Optional.empty());

        assertThrows(PatronNotFoundException.class, () -> patronService.updatePatronById(id, patron));
    }

    @Test
    void updatePatronById_PatronExistsWithSamePhoneNumber() {
        Long id = 1L;
        when(patronRepo.findById(id)).thenReturn(Optional.of(patron));
        when(patronRepo.existsByPhoneNumberForOtherPatron(patron.getPhoneNumber(), id)).thenReturn(true);

        assertThrows(PatronAlreadyExistsException.class, () -> patronService.updatePatronById(id, patron));
        verify(patronRepo, never()).existsByEmailAddressForOtherPatron(anyString(), anyLong());
    }

    @Test
    void updatePatronById_PatronExistsWithSameEmailAddress() {
        Long id = 1L;
        when(patronRepo.findById(id)).thenReturn(Optional.of(patron));
        when(patronRepo.existsByPhoneNumberForOtherPatron(patron.getPhoneNumber(), id)).thenReturn(false);
        when(patronRepo.existsByEmailAddressForOtherPatron(patron.getEmailAddress(), id)).thenReturn(true);

        assertThrows(PatronAlreadyExistsException.class, () -> patronService.updatePatronById(id, patron));
        verify(patronRepo, times(1)).existsByPhoneNumberForOtherPatron(anyString(), anyLong());
    }

    @Test
    void deletePatronById_PatronExists() {
        Long id = 1L;
        when(patronRepo.existsById(id)).thenReturn(true);

        patronService.deletePatronById(id);

        verify(patronRepo, times(1)).deleteById(id);
    }

    @Test
    void deletePatronById_PatronNotFound() {
        Long id = 1L;
        when(patronRepo.existsById(id)).thenReturn(false);

        assertThrows(PatronNotFoundException.class, () -> patronService.deletePatronById(id));
        verify(patronRepo, never()).deleteById(anyLong());
    }
}
