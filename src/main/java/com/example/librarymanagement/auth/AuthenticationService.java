package com.example.librarymanagement.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.librarymanagement.entities.Librarian;
import com.example.librarymanagement.exceptions.LibrarianNotFoundException;
import com.example.librarymanagement.repositories.LibrarianRepo;
import com.example.librarymanagement.security_config.JwtService;

@Service
public class AuthenticationService {

        @Autowired
        private JwtService jwtService;
        @Autowired
        private AuthenticationManager authenticationManager;
        @Autowired
        private PasswordEncoder passwordEncoder;
        @Autowired
        private LibrarianRepo librarianRepo;

        public void register(AuthenticationRequest request) {
                Librarian librarian = Librarian.builder()
                                .username(request.getUsername())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .build();
                librarianRepo.save(librarian);
        }

        public AuthenticationResponse authenticate(AuthenticationRequest request) {
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(request.getUsername(),
                                                request.getPassword()));

                Librarian librarian = librarianRepo.findByUsername(request.getUsername())
                                .orElseThrow(() -> new LibrarianNotFoundException(
                                                "Librarian with username {" + request.getUsername()
                                                                + "} does not exists."));

                String jwtToken = jwtService.generateToken(librarian);
                return AuthenticationResponse.builder()
                                .accessToken(jwtToken)
                                .build();
        }
}
