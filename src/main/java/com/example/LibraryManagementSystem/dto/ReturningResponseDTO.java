package com.example.LibraryManagementSystem.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Setter
@Getter
public class ReturningResponseDTO {
    private String message;
    private String patronName;
    private String bookTitle;
    private LocalDate returnDate;

    public ReturningResponseDTO(String patronName, String bookTitle, LocalDate returnDate) {
        this.message = "Patron " + patronName + " returned book " + bookTitle + " successfully.";
        this.patronName = patronName;
        this.bookTitle = bookTitle;
        this.returnDate = returnDate;
    }
}
