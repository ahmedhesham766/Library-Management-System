package com.example.LibraryManagementSystem.service.Impl;

import com.example.LibraryManagementSystem.exception.BookCurrentlyBorrowedException;
import com.example.LibraryManagementSystem.exception.BookNotFoundException;
import com.example.LibraryManagementSystem.model.Book;
import com.example.LibraryManagementSystem.model.BorrowingRecord;
import com.example.LibraryManagementSystem.repo.BookRepository;
import com.example.LibraryManagementSystem.service.BookService;
import com.example.LibraryManagementSystem.service.BorrowingService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);


    private final BookRepository bookRepository;
    private final BorrowingService borrowService;

    public BookServiceImpl(BookRepository bookRepository, BorrowingService borrowService) {
        this.bookRepository = bookRepository;
        this.borrowService = borrowService;
    }


    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    @Cacheable("books")
    public Book getBookById(Long id) {
        System.out.println("Fetching book with ID {} from the database"+ id);
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
    }

    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    @CachePut(value = "books", key = "#id")
    public Book updateBook(Long id, Book bookDetails) {
        if (borrowService.isBookBorrowed(id)) {
            BorrowingRecord record = borrowService.getActiveBorrowingRecordByBookId(id);
            throw new BookCurrentlyBorrowedException(id, record.getPatron().getName());
        }


        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        existingBook.setTitle(bookDetails.getTitle());
        existingBook.setAuthor(bookDetails.getAuthor());
        existingBook.setPublicationYear(bookDetails.getPublicationYear());
        existingBook.setIsbn(bookDetails.getIsbn());
        existingBook.setGenre(bookDetails.getGenre());
        existingBook.setCopiesAvailable(bookDetails.getCopiesAvailable());

        return bookRepository.save(existingBook);
    }

    @Override
    @CacheEvict(value = "books", key = "#id")
    public void deleteBook(Long id) {
        if (borrowService.isBookBorrowed(id)) {
            BorrowingRecord record = borrowService.getActiveBorrowingRecordByBookId(id);
            throw new BookCurrentlyBorrowedException(id, record.getPatron().getName());
        }
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        bookRepository.delete(existingBook);

    }
}
