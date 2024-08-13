package com.example.LibraryManagementSystem.service.Impl;

import com.example.LibraryManagementSystem.exception.BookNotFoundException;
import com.example.LibraryManagementSystem.exception.PatronNotFoundException;
import com.example.LibraryManagementSystem.model.Book;
import com.example.LibraryManagementSystem.model.BorrowingRecord;
import com.example.LibraryManagementSystem.model.Patron;
import com.example.LibraryManagementSystem.repo.BookRepository;
import com.example.LibraryManagementSystem.repo.BorrowingRecordRepository;
import com.example.LibraryManagementSystem.repo.PatronRepository;
import com.example.LibraryManagementSystem.service.BorrowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BorrowingServiceImpl implements BorrowingService {

    @Autowired
    private final BorrowingRecordRepository recordRepository;


    @Autowired
    private final BookRepository bookRepository;

    @Autowired
    private final PatronRepository patronRepository;

    public BorrowingServiceImpl(BorrowingRecordRepository recordRepository, BookRepository bookRepository, PatronRepository patronRepository) {
        this.recordRepository = recordRepository;
        this.bookRepository = bookRepository;
        this.patronRepository = patronRepository;
    }

    @Override
    public BorrowingRecord borrowBook(Long bookId, Long patronId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));

        Patron patron = patronRepository.findById(patronId)
                .orElseThrow(() -> new PatronNotFoundException(patronId));

        if (book.getCopiesAvailable() <= 0) {
            throw new RuntimeException("No copies available for book: " + book.getTitle());
        }

        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowingDate(LocalDate.now());

        book.setCopiesAvailable(book.getCopiesAvailable() - 1);
        bookRepository.save(book);

        return recordRepository.save(borrowingRecord);
    }

    @Override
    public BorrowingRecord returnBook(Long bookId, Long patronId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));

        Patron patron = patronRepository.findById(patronId)
                .orElseThrow(() -> new PatronNotFoundException(patronId));

        // Find the active borrowing record for the book and patron
        BorrowingRecord borrowingRecord = recordRepository.findByBookAndPatronAndReturnDateIsNull(book, patron)
                .orElseThrow(() -> new RuntimeException("Borrowing record not found for book ID " + bookId + " and patron ID " + patronId));

        // Set the return date
        borrowingRecord.setReturnDate(LocalDate.now());

        // Increase the available copies of the book
        book.setCopiesAvailable(book.getCopiesAvailable() + 1);
        bookRepository.save(book);

        // Save the updated borrowing record
        return recordRepository.save(borrowingRecord);
    }
}
