package com.example.LibraryManagementSystem.exception;

public class BookCurrentlyBorrowedException extends RuntimeException {

    private final String patronName;

    public BookCurrentlyBorrowedException(Long bookId, String patronName) {
        super("Book with ID " + bookId + " is currently borrowed by patron: " + patronName);
        this.patronName = patronName;
    }

    public String getPatronName() {
        return patronName;
    }
}