package com.example.librarymanagement.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "borrowing_records", uniqueConstraints = @UniqueConstraint(columnNames = { "book_id", "patron_id" }))
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BorrowingRecord {
    @Id
    @SequenceGenerator(name = "record_sequence", sequenceName = "record_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "record_sequence")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "patron_id", referencedColumnName = "id")
    private Patron patron;

    @Column(name = "borrowed_date")
    @Temporal(TemporalType.DATE)
    @PastOrPresent(message = "Publication year must be in the past or present")
    private LocalDate borrowedDate;

    @Column(name = "return_date")
    @Temporal(TemporalType.DATE)
    @PastOrPresent(message = "Publication year must be in the past or present")
    private LocalDate returnDate;

    public Boolean isReturned() {
        return returnDate != null;
    }
}
