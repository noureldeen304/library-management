package com.example.librarymanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.librarymanagement.entities.Patron;

import java.util.Optional;

@Repository
public interface PatronRepo extends JpaRepository<Patron, Long> {
    Optional<Patron> findById(Long id);

    boolean existsByPhoneNumber(String phoneNumber);

    @Query(value = "SELECT EXISTS (SELECT 1 FROM patrons "
            + "WHERE phone_number = :phoneNumber AND id != :id);", nativeQuery = true)
    boolean existsByPhoneNumberForOtherPatron(String phoneNumber, Long id);

    @Query(value = "SELECT EXISTS (SELECT 1 FROM patrons "
            + "WHERE email_address = :emailAddress AND id != :id);", nativeQuery = true)
    boolean existsByEmailAddressForOtherPatron(String emailAddress, Long id);

    boolean existsByEmailAddress(String emailAddress);
}
