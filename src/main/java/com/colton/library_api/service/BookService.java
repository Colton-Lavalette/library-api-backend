package com.colton.library_api.service;

import com.colton.library_api.model.Book;
import com.colton.library_api.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book createBook(String title, String isbn, Integer publishedYear) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title must not be empty");
        }
        if (isbn == null || isbn.isBlank()) {
            throw new IllegalArgumentException("ISBN must not be empty");
        }
        if (bookRepository.findByIsbn(isbn).isPresent()) {
            throw new DuplicateResourceException("Book already exists with isbn: " + isbn);
        }
        return bookRepository.save(new Book(title, isbn, publishedYear));
    }

    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
    }

    public Book findByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with isbn: " + isbn));
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public List<Book> findByPublishedYear(Integer publishedYear) {
        return bookRepository.findByPublishedYear(publishedYear);
    }

    public Book updateBook(Long id, String title, Integer publishedYear) {
        Book book = findById(id);
        if (title != null && !title.isBlank()) book.updateTitle(title);
        if (publishedYear != null) book.updatePublishedYear(publishedYear);
        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }
}
