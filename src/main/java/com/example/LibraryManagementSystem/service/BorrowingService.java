package com.example.LibraryManagementSystem.service;

import com.example.LibraryManagementSystem.model.BorrowingRecord;

public interface BorrowingService {
    BorrowingRecord borrowBook(Long bookId, Long patronId);
    BorrowingRecord returnBook(Long bookId, Long patronId);
}
