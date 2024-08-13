package com.example.LibraryManagementSystem.exception;

public class BorrowingNotAllowedException extends RuntimeException {
    public BorrowingNotAllowedException(String message) {
        super(message);
    }
}
