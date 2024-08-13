package com.example.LibraryManagementSystem.service;

import com.example.LibraryManagementSystem.model.Book;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();
    Book getBookById(Long id);
    Book saveBook(Book book);
    Book updateBook(Long id,Book book);
    void deleteBook(Long id);
}
