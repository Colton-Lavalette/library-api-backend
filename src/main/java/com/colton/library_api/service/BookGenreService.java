package com.colton.library_api.service;

import com.colton.library_api.exception.GenreAlreadyLinkedException;
import com.colton.library_api.exception.ResourceNotFoundException;
import com.colton.library_api.model.Book;
import com.colton.library_api.model.BookGenre;
import com.colton.library_api.model.Genre;
import com.colton.library_api.repository.BookGenreRepository;
import com.colton.library_api.repository.BookRepository;
import com.colton.library_api.repository.GenreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookGenreService {

    private final BookGenreRepository bookGenreRepository;
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;

    public BookGenreService(BookRepository bookRepository,
                            GenreRepository genreRepository,
                            BookGenreRepository bookGenreRepository) {
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
        this.bookGenreRepository = bookGenreRepository;
    }

    @Transactional
    public void addGenre(Long bookId, Long genreId, boolean primary) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));

        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new ResourceNotFoundException("Genre not found with id: " + genreId));

        boolean exists = bookGenreRepository
                .existsByBookAndGenre(book, genre);

        if (exists) {
            throw new GenreAlreadyLinkedException(bookId, genreId);
        }

        if (primary && bookGenreRepository.existsByBookAndPrimaryGenreTrue(book)) {
            throw new IllegalStateException("Book already has a primary genre");
        }

        BookGenre link = new BookGenre(book, genre, primary);
        bookGenreRepository.save(link);
    }

    @Transactional
    public void removeGenre(Long bookId, Long genreId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));

        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new ResourceNotFoundException("Genre not found with id: " + genreId));

        BookGenre link = bookGenreRepository
                .findByBookAndGenre(book, genre)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Genre not linked to book"));

        bookGenreRepository.delete(link);
    }
}