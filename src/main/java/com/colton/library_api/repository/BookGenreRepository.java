package com.colton.library_api.repository;

import com.colton.library_api.model.Book;
import com.colton.library_api.model.BookGenre;
import com.colton.library_api.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookGenreRepository extends JpaRepository<BookGenre, Long> {
    boolean existsByBookAndGenre(Book book, Genre genre);
    boolean existsByBookAndPrimaryGenreTrue(Book book);
    Optional<BookGenre> findByBookAndGenre(Book book, Genre genre);
}
