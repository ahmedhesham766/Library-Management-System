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
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class BorrowingServiceImpl implements BorrowingService {


    private final BorrowingRecordRepository recordRepository;


    private final BookRepository bookRepository;


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
        int newCopiesAvailable = book.getCopiesAvailable() - 1;
        updateBookAvailability(book, newCopiesAvailable);

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


    private void updateBookAvailability(Book book ,int newCopiesAvailable) {
        bookRepository.updateCopiesAvailable(book.getId() ,newCopiesAvailable );
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

    @Override
    public boolean isBookBorrowed(Long bookId) {
        return !recordRepository.findByBookIdAndReturnDateIsNull(bookId).isEmpty();
    }

    public boolean isPatronBorrowing(Long patronId) {
        return !recordRepository.findByPatronIdAndReturnDateIsNull(patronId).isEmpty();
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

        int newCopiesAvailable = book.getCopiesAvailable() + 1;
        updateBookAvailability(book, newCopiesAvailable);
        recordRepository.save(borrowingRecord);
    }


    private BorrowingRecord getActiveBorrowingRecord(Function<BorrowingRecordRepository, List<BorrowingRecord>> finder) {
        List<BorrowingRecord> records = finder.apply(recordRepository);
        if (!records.isEmpty()) {
            return records.get(0);
        }
        return null;
    }

    public BorrowingRecord getActiveBorrowingRecordByBookId(Long bookId) {
        return getActiveBorrowingRecord(repo -> repo.findByBookIdAndReturnDateIsNull(bookId));
    }

    public BorrowingRecord getActiveBorrowingRecordByPatronId(Long patronId) {
        return getActiveBorrowingRecord(repo -> repo.findByPatronIdAndReturnDateIsNull(patronId));
    }

}
