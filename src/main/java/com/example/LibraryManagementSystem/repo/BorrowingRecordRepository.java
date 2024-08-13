package com.example.LibraryManagementSystem.repo;

import com.example.LibraryManagementSystem.model.Book;
import com.example.LibraryManagementSystem.model.BorrowingRecord;
import com.example.LibraryManagementSystem.model.Patron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {
    Optional<BorrowingRecord> findByBookAndPatronAndReturnDateIsNull(Book book, Patron patron);
}
