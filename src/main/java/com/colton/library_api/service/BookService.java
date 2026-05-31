package com.colton.library_api.service;

import com.colton.library_api.dto.book.BookRequest;
import com.colton.library_api.dto.book.BookResponse;
import com.colton.library_api.exception.DuplicateIsbnException;
import com.colton.library_api.exception.ResourceNotFoundException;
import com.colton.library_api.model.Book;
import com.colton.library_api.repository.BookCopyRepository;
import com.colton.library_api.repository.BookRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BookCopyRepository bookCopyRepository;

    public BookService(BookRepository bookRepository,
                       BookCopyRepository bookCopyRepository) {
        this.bookRepository = bookRepository;
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

    public List<BookResponse> findAll() {
        return bookRepository.findAll()
                .stream()
                .map(this::mapToResponse)
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

    private Book findByEntityId(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
    }

    public BookResponse findById(Long id) {
        return mapToResponse(findByEntityId(id));
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
