package com.example.LibraryManagementSystem.service.Impl;

import com.example.LibraryManagementSystem.dto.BorrowingResponseDTO;
import com.example.LibraryManagementSystem.dto.ReturningResponseDTO;
import com.example.LibraryManagementSystem.exception.*;
import com.example.LibraryManagementSystem.model.Book;
import com.example.LibraryManagementSystem.model.BorrowingRecord;
import com.example.LibraryManagementSystem.model.Patron;
import com.example.LibraryManagementSystem.repo.BookRepository;
import com.example.LibraryManagementSystem.repo.BorrowingRecordRepository;
import com.example.LibraryManagementSystem.repo.PatronRepository;
import com.example.LibraryManagementSystem.service.BorrowingService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

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

    @Transactional
    @Override
    public BorrowingResponseDTO borrowBook(Long bookId, Long patronId) {
        Book book = findBookById(bookId);
        Patron patron = findPatronById(patronId);

        validateBorrowing(book, patron);

        BorrowingRecord borrowingRecord = createBorrowingRecord(book, patron);
        updateBookAvailability(book);

        return new BorrowingResponseDTO(patron.getName(), book.getTitle(), borrowingRecord.getBorrowingDate());
    }

    private Book findBookById(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));
    }

    private Patron findPatronById(Long patronId) {
        return patronRepository.findById(patronId)
                .orElseThrow(() -> new PatronNotFoundException(patronId));
    }

    private void validateBorrowing(Book book, Patron patron) {
        boolean alreadyBorrowed = recordRepository
                .findByBookAndPatronAndReturnDateIsNull(book, patron)
                .isPresent();

        if (alreadyBorrowed) {
            throw new BorrowingNotAllowedException(patron, book);
        }

        if (book.getCopiesAvailable() <= 0) {
            throw new NoAvailableCopiesException(book);
        }
    }

    private BorrowingRecord createBorrowingRecord(Book book, Patron patron) {
        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowingDate(LocalDate.now());
        return recordRepository.save(borrowingRecord);
    }

    private void updateBookAvailability(Book book) {
        book.setCopiesAvailable(book.getCopiesAvailable() - 1);
        bookRepository.save(book);
    }

    @Transactional
    @Override
    public ReturningResponseDTO returnBook(Long bookId, Long patronId) {
        Book book = findBookById(bookId);
        Patron patron = findPatronById(patronId);

        BorrowingRecord borrowingRecord = findActiveBorrowingRecord(book, patron);
        processBookReturn(borrowingRecord, book);

        return new ReturningResponseDTO(patron.getName(), book.getTitle(), borrowingRecord.getReturnDate());
    }

    private BorrowingRecord findActiveBorrowingRecord(Book book, Patron patron) {
        return recordRepository.findByBookAndPatronAndReturnDateIsNull(book, patron)
                .orElseThrow(() -> {
                    Optional<BorrowingRecord> lastBorrowingRecord = recordRepository.findTopByBookAndPatronOrderByReturnDateDesc(book, patron);
                    if (lastBorrowingRecord.isPresent()) {
                        LocalDate returnDate = lastBorrowingRecord.get().getReturnDate();
                        return new BookAlreadyReturnedException(book, patron, returnDate);
                    } else {
                        return new BorrowingRecordNotFoundException(book.getId(), patron.getId());
                    }
                });
    }

    private void processBookReturn(BorrowingRecord borrowingRecord, Book book) {

        borrowingRecord.setReturnDate(LocalDate.now());

        book.setCopiesAvailable(book.getCopiesAvailable() + 1);
        bookRepository.save(book);
        recordRepository.save(borrowingRecord);
    }

}
