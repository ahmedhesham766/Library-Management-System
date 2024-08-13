package com.example.LibraryManagementSystem.model;

import com.example.LibraryManagementSystem.Enum.Genre;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Book")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "publication_year")
    private int publicationYear;

    @Column(name = "isbn", unique = true, nullable = false)
    private String isbn;

    @Enumerated(EnumType.STRING)
    @Column(name = "genre", nullable = false)
    private Genre genre;

    @Column(name = "copies_available", nullable = false)
    private int copiesAvailable;

    @PrePersist
    @PreUpdate
    private void validateCopiesAvailable() {
        if (copiesAvailable < 0) {
            throw new IllegalArgumentException("Copies available cannot be negative.");
        }
    }

}
