package com.example.LibraryManagementSystem.controller;

import com.example.LibraryManagementSystem.dto.BorrowingResponseDTO;
import com.example.LibraryManagementSystem.dto.ReturningResponseDTO;
import com.example.LibraryManagementSystem.model.BorrowingRecord;
import com.example.LibraryManagementSystem.service.BorrowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BorrowingController {

    @Autowired
    private final BorrowingService borrowingService;

    public BorrowingController(BorrowingService borrowingService) {
        this.borrowingService = borrowingService;
    }


    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingResponseDTO> borrowBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        BorrowingResponseDTO  borrowingRecord = borrowingService.borrowBook(bookId, patronId);
        return ResponseEntity.ok(borrowingRecord);
    }


    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<ReturningResponseDTO> returnBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        ReturningResponseDTO  borrowingRecord = borrowingService.returnBook(bookId, patronId);
        return ResponseEntity.ok(borrowingRecord);
    }
}
