package com.example.LibraryManagementSystem.exception;

public class BorrowingRecordNotFoundException extends RuntimeException {
    public BorrowingRecordNotFoundException(Long bookId, Long patronId) {
        super("Borrowing record not found for book ID " + bookId + " and patron ID " + patronId);
    }
}
