package com.example.LibraryManagementSystem.dto;

import lombok.*;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@Setter
@Getter
public class BorrowingResponseDTO {
    private String message;
    private String patronName;
    private String bookTitle;
    private LocalDate borrowingDate;

    public BorrowingResponseDTO(String patronName, String bookTitle, LocalDate borrowingDate) {
        this.message = "Patron " + patronName + " borrows book " + bookTitle + " successfully.";
        this.patronName = patronName;
        this.bookTitle = bookTitle;
        this.borrowingDate = borrowingDate;
    }
}
