package com.example.librarymanagement.services;

import java.util.List;

import com.example.librarymanagement.entities.Patron;

public interface PatronService {

    public List<Patron> getAllPatrons();

    public Patron getPatronById(Long id);

    public Patron addPatron(Patron patron);

    public Patron updatePatronById(Long id, Patron newPatron);

    public void deletePatronById(Long id);

}
