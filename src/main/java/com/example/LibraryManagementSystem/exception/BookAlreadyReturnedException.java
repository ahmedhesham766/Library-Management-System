package com.example.LibraryManagementSystem.exception;

import com.example.LibraryManagementSystem.model.Book;
import com.example.LibraryManagementSystem.model.Patron;

import java.time.LocalDate;

public class BookAlreadyReturnedException extends RuntimeException {
    public BookAlreadyReturnedException(Book book, Patron patron, LocalDate returnDate) {
        super(buildMessage(book, patron, returnDate));
    }

    private static String buildMessage(Book book, Patron patron, LocalDate returnDate) {
        return String.format("The book '%s' has already been returned by patron '%s' on %s",
                book.getTitle(), patron.getName(), returnDate);
    }
}
