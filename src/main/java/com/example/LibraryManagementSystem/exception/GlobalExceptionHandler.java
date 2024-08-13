package com.example.LibraryManagementSystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PatronNotFoundException.class)
    public ResponseEntity<Object> handlePatronNotFoundException(PatronNotFoundException ex, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Patron not found");
        body.put("message", ex.getMessage());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("timestamp", System.currentTimeMillis());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<Object> handleBookNotFoundException(BookNotFoundException ex, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Book not found");
        body.put("message", ex.getMessage());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("timestamp", System.currentTimeMillis());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
}
