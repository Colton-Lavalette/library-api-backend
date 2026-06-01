package com.colton.library_api.service;

import com.colton.library_api.dto.author.AuthorResponse;
import com.colton.library_api.dto.book.BookAuthorResponse;
import com.colton.library_api.dto.book.BookGenreResponse;
import com.colton.library_api.dto.book.BookRequest;
import com.colton.library_api.dto.book.BookResponse;
import com.colton.library_api.dto.genre.GenreResponse;
import com.colton.library_api.exception.DuplicateIsbnException;
import com.colton.library_api.exception.ResourceNotFoundException;
import com.colton.library_api.model.*;
import com.colton.library_api.repository.BookAuthorRepository;
import com.colton.library_api.repository.BookCopyRepository;
import com.colton.library_api.repository.BookGenreRepository;
import com.colton.library_api.repository.BookRepository;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class BookService {
    private final BookAuthorRepository bookAuthorRepository;
    private final BookRepository bookRepository;
    private final BookCopyRepository bookCopyRepository;
    private final BookGenreRepository bookGenreRepository;

    public BookService(BookAuthorRepository bookAuthorRepository,
                       BookRepository bookRepository,
                       BookCopyRepository bookCopyRepository,
                       BookGenreRepository bookGenreRepository) {
        this.bookAuthorRepository = bookAuthorRepository;
        this.bookRepository = bookRepository;
        this.bookGenreRepository = bookGenreRepository;
        this.bookCopyRepository = bookCopyRepository;
    }

    private BookResponse mapToResponse(Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getIsbn(),
                book.getPublishedYear()
        );
    }

    private AuthorResponse mapToAuthorResponse(Author author) {
        return new AuthorResponse(
                author.getId(),
                author.getName().getFirst(),
                author.getName().getMiddle(),
                author.getName().getLast(),
                author.getBirthYear()
        );
    }

    private GenreResponse mapToGenreResponse(Genre genre) {
        return new GenreResponse(
                genre.getId(),
                genre.getName()
        );
    }

    public List<BookResponse> findAll() {
        return bookRepository.findAll(Sort.by("id"))
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private Book findByEntityId(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
    }

    public BookResponse findById(Long id) {
        return mapToResponse(findByEntityId(id));
    }

    public List<BookAuthorResponse> getAuthorsForBook(Long bookId) {
        Book book = findByEntityId(bookId);

        return book.getAuthors().stream()
                .map(ba -> new BookAuthorResponse(
                        mapToAuthorResponse(ba.getAuthor()),
                        ba.isPrimaryAuthor()
                ))
                .toList();
    }

    public List<BookGenreResponse> getGenresForBook(Long bookId) {
        Book book = findByEntityId(bookId);

        return book.getGenres().stream()
                .map(bg -> new BookGenreResponse(
                        mapToGenreResponse(bg.getGenre()),
                        bg.isPrimaryGenre()
                ))
                .toList();
    }

    public BookResponse createBook(BookRequest bookRequest) {
        if (bookRequest == null) {
            throw new IllegalArgumentException("Request must not be null");
        }

        if (bookRepository.existsByIsbn(bookRequest.isbn())) {
            throw new DuplicateIsbnException(bookRequest.isbn());
        }

        Book book = new Book(
                bookRequest.title(),
                bookRequest.isbn(),
                bookRequest.publishedYear()
        );

        Book saved = bookRepository.save(book);

        return mapToResponse(saved);
    }

    @Transactional
    public BookResponse updateBook(Long id, BookRequest bookRequest) {
        if (bookRequest == null) {
            throw new IllegalArgumentException("Request must not be null");
        }

        Book book = findByEntityId(id);

        if (bookRequest.title() != null) {
            book.updateTitle(bookRequest.title());
        }

        if (bookRequest.isbn() != null && !bookRequest.isbn().equals(book.getIsbn())) {
            if (bookRepository.existsByIsbn(bookRequest.isbn())) {
                throw new DuplicateIsbnException(bookRequest.isbn());
            }
            book.updateIsbn(bookRequest.isbn());
        }

        if (bookRequest.publishedYear() != null) {
            book.updatePublishedYear(bookRequest.publishedYear());
        }

        return mapToResponse(book);
    }

}
