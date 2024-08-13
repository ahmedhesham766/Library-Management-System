package com.example.LibraryManagementSystem.exception;


import com.example.LibraryManagementSystem.model.Book;
import com.example.LibraryManagementSystem.model.Patron;

public class BorrowingNotAllowedException extends RuntimeException {
    public BorrowingNotAllowedException(Patron patron , Book book) {
        super("Patron " + patron.getName() + " has already borrowed book " + book.getTitle());
    }
}
