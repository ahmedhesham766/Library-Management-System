package com.example.LibraryManagementSystem.exception;

public class PatronHasActiveBorrowingsException extends RuntimeException {

    private final Long patronId;

    public PatronHasActiveBorrowingsException(Long patronId, String bookTitle) {
        super("Patron with ID " + patronId + " has active borrowings, currently borrowing: " + bookTitle);
        this.patronId = patronId;
    }

    public Long getPatronId() {
        return patronId;
    }
}