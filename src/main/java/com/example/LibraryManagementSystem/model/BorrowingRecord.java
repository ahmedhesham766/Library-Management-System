package com.example.LibraryManagementSystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "Borrowing_Record")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowingRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "patron_id", nullable = false)
    private Patron patron;

    @Column(name = "borrowing_date", nullable = false)
    private LocalDate borrowingDate;

    @Column(name = "return_date")
    private LocalDate returnDate;

    @PrePersist
    @PreUpdate
    private void validateDates() {
        if (returnDate != null && returnDate.isBefore(borrowingDate)) {
            throw new IllegalArgumentException("Return date cannot be before borrowing date.");
        }
    }

}