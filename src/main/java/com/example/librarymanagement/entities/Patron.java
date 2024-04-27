package com.example.librarymanagement.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "patrons")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Patron {
    @Id
    @SequenceGenerator(name = "patron_sequence", sequenceName = "patron_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patron_sequence")
    private Long id;

    @NotBlank
    private String name;

    @Column(name = "phone_number", unique = true)
    @NotBlank
    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    private String phoneNumber;

    @NotBlank
    @Email(message = "Email address must be valid")
    @Column(name = "email_address", unique = true)
    private String emailAddress;
}