package com.example.LibraryManagementSystem.repo;

import com.example.LibraryManagementSystem.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BookRepository  extends JpaRepository<Book, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Book b SET b.copiesAvailable = :copiesAvailable WHERE b.id = :id")
    void updateCopiesAvailable(@Param("id") Long id, @Param("copiesAvailable") int copiesAvailable);
}
