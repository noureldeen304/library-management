package com.example.librarymanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.librarymanagement.entities.Librarian;
import java.util.Optional;


@Repository
public interface LibrarianRepo extends JpaRepository<Librarian, String> {
    Optional<Librarian> findByUsername(String username);
}
