package com.example.LibraryManagementSystem.repo;

import com.example.LibraryManagementSystem.model.Patron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatronRepository  extends JpaRepository<Patron, Long> {
}
