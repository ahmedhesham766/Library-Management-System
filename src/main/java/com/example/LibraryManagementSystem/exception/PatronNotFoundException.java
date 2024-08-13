package com.example.LibraryManagementSystem.exception;

public class PatronNotFoundException extends RuntimeException {
    public PatronNotFoundException(Long id) {
        super("Patron not found with id: " + id);
    }
}
