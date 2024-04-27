package com.example.librarymanagement.services.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.librarymanagement.entities.Patron;
import com.example.librarymanagement.exceptions.PatronAlreadyExistsException;
import com.example.librarymanagement.exceptions.PatronNotFoundException;
import com.example.librarymanagement.repositories.PatronRepo;
import com.example.librarymanagement.services.PatronService;

import lombok.AllArgsConstructor;

import java.util.List;

@Service
@AllArgsConstructor
public class PatronServiceImpl implements PatronService{

    @Autowired
    private PatronRepo patronRepo;

    public List<Patron> getAllPatrons() {
        return patronRepo.findAll();
    }

    public Patron getPatronById(Long id) {
        return patronRepo.findById(id)
                .orElseThrow(() -> new PatronNotFoundException("Patron with id {" + id + "} does not exist."));
    }

    public Patron addPatron(Patron patron) {
        if (patronRepo.existsByPhoneNumber(patron.getPhoneNumber())) {
            throw new PatronAlreadyExistsException(
                    "Patron with phone number  {" + patron.getPhoneNumber() + "} already exists.");
        }
        if (patronRepo.existsByEmailAddress(patron.getEmailAddress())) {
            throw new PatronAlreadyExistsException(
                    "Patron with email address  {" + patron.getEmailAddress() + "} already exists.");
        }
        return patronRepo.save(patron);
    }

    @Transactional
    public Patron updatePatronById(Long id, Patron newPatron) {
        Patron patron = patronRepo.findById(id)
                .orElseThrow(() -> new PatronNotFoundException("Patron with id {" + id + "} does not exist."));

        if (patronRepo.existsByPhoneNumberForOtherPatron(newPatron.getPhoneNumber(),
                id)) {
            throw new PatronAlreadyExistsException(
                    "Patron with phone number  {" + newPatron.getPhoneNumber() + "} already exists for someone else.");
        }

        if (patronRepo.existsByEmailAddressForOtherPatron(newPatron.getEmailAddress(),
                id)) {
            throw new PatronAlreadyExistsException(
                    "Patron with phone number  {" + newPatron.getEmailAddress() + "} already exists for someone else.");
        }

        BeanUtils.copyProperties(newPatron, patron, "id");
        return patron;
    }

    public void deletePatronById(Long id) {
        if (!patronRepo.existsById(id)) {
            throw new PatronNotFoundException("Patron with id {" + id + "} does not exist.");
        }
        patronRepo.deleteById(id);
    }
}
