package com.example.LibraryManagementSystem.controller;

import com.example.LibraryManagementSystem.model.Patron;
import com.example.LibraryManagementSystem.service.PatronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patrons")
public class PatronController {

    @Autowired
    private final PatronService patronService;

    public PatronController(PatronService patronService) {
        this.patronService = patronService;
    }

    @GetMapping
    public List<Patron> getAllPatrons() {
        return patronService.getAllPatrons();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Patron> getPatronById(@PathVariable Long id) {
        Patron patron = patronService.getPatronById(id);
        return ResponseEntity.ok(patron);
    }


    @PostMapping
    public ResponseEntity<Patron> createPatron(@RequestBody Patron patron) {
        Patron newPatron = patronService.createPatron(patron);
        return ResponseEntity.ok(newPatron);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Patron> updatePatron(@PathVariable Long id, @RequestBody Patron patronDetails) {
        Patron updatedPatron = patronService.updatePatron(id, patronDetails);
        return ResponseEntity.ok(updatedPatron);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatron(@PathVariable Long id) {
        patronService.deletePatron(id);
        return ResponseEntity.noContent().build();
    }
}
