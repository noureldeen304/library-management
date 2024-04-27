package com.example.librarymanagement.security_config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.librarymanagement.entities.Librarian;
import com.example.librarymanagement.exceptions.LibrarianNotFoundException;
import com.example.librarymanagement.repositories.LibrarianRepo;

@Configuration
public class ApplicationConfig {

    @Autowired
    LibrarianRepo librarianRepo;

    @Bean
    UserDetailsService userDetailsService() {
        return username -> {
            Librarian user = librarianRepo.findByUsername(username)
                    .orElseThrow(() -> new LibrarianNotFoundException(
                            "Librarian with username {" + username + "} does not exists."));
            return user;
        };
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
