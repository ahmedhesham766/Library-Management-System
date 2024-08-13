package com.example.LibraryManagementSystem.exception;


import com.example.LibraryManagementSystem.model.Book;

public class NoAvailableCopiesException extends RuntimeException {
    public NoAvailableCopiesException(Book book) {
        super("No copies available for book: " + book.getTitle());
    }
}
