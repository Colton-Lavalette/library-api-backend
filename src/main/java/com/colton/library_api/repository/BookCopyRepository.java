package com.colton.library_api.repository;

import com.colton.library_api.model.BookCopy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {
    boolean existsByCopyCode(String copyCode);
    Optional<BookCopy> findByCopyCode(String copyCode);
    List<BookCopy> findByBookId(Long bookId);
}