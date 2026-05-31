package com.colton.library_api.repository;

import com.colton.library_api.model.Book;
import com.colton.library_api.model.BookAuthor;
import com.colton.library_api.model.BookGenre;
import com.colton.library_api.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookGenreRepository extends JpaRepository<BookGenre, Long> {
    boolean existsByBookAndGenre(Book book, Genre genre);
    boolean existsByBookAndPrimaryGenreTrue(Book book);
    boolean existsByGenreId(Long genreId);
    Optional<BookGenre> findByBookIdAndGenreId(Long bookId, Long genreId);
    Optional<BookGenre> findByBookAndGenre(Book book, Genre genre);

    @Modifying
    @Query("""
    UPDATE BookGenre bg
    SET bg.primaryGenre = false
    WHERE bg.book.id = :bookId
""")
    void clearPrimaryForBook(@Param("bookId") Long bookId);
}
