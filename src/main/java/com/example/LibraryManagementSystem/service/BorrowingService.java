package com.example.LibraryManagementSystem.service;

import com.example.LibraryManagementSystem.dto.BorrowingResponseDTO;
import com.example.LibraryManagementSystem.dto.ReturningResponseDTO;
import com.example.LibraryManagementSystem.model.BorrowingRecord;


public interface BorrowingService {
    BorrowingResponseDTO borrowBook(Long bookId, Long patronId);
    ReturningResponseDTO returnBook(Long bookId, Long patronId);
     boolean isBookBorrowed(Long bookId);
     BorrowingRecord getActiveBorrowingRecordByBookId(Long bookId);
     BorrowingRecord getActiveBorrowingRecordByPatronId(Long patronId);
     boolean isPatronBorrowing(Long patronId);

}
