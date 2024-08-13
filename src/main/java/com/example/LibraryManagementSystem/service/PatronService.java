package com.example.LibraryManagementSystem.service;

import com.example.LibraryManagementSystem.model.Patron;

import java.util.List;

public interface PatronService {
    List<Patron> getAllPatrons();
    Patron getPatronById(Long id);
    Patron createPatron(Patron patron);
    Patron updatePatron(Long id, Patron patronDetails);
    void deletePatron(Long id);
}
