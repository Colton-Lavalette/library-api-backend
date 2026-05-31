package com.colton.library_api.repository;

import com.colton.library_api.model.BookAuthor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookAuthorRepository extends JpaRepository<BookAuthor, Long> {
    Optional<BookAuthor> findByBookIdAndAuthorId(Long bookId, Long authorId);
    boolean existsByBookIdAndAuthorId(Long bookId, Long authorId);
    boolean existsByAuthorId(Long authorId);
    boolean existsByBookIdAndPrimaryAuthorTrue(Long bookId);

    @Modifying
    @Query("""
    UPDATE BookAuthor ba
    SET ba.primaryAuthor = false
    WHERE ba.book.id = :bookId
""")
    void clearPrimaryForBook(@Param("bookId") Long bookId);
}
