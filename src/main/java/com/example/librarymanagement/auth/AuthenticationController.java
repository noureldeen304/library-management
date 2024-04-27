package com.example.librarymanagement.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService service;

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @Valid @RequestBody AuthenticationRequest request) {

        logger.info("Received registration request for username: {}", request.getUsername());
        service.register(request);
        logger.info("Registration successful for username: {}", request.getUsername());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @Valid @RequestBody AuthenticationRequest request) {
        logger.info("Received authentication request for user: {}", request.getUsername());
        AuthenticationResponse response = service.authenticate(request);
        if (response != null) {
            logger.info("User {} authenticated successfully", request.getUsername());
        } else {
            logger.warn("Authentication failed for user: {}", request.getUsername());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
