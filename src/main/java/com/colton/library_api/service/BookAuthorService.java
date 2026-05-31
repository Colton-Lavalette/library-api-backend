package com.colton.library_api.service;

import com.colton.library_api.exception.ResourceNotFoundException;
import com.colton.library_api.model.Author;
import com.colton.library_api.model.Book;
import com.colton.library_api.model.BookAuthor;
import com.colton.library_api.repository.AuthorRepository;
import com.colton.library_api.repository.BookAuthorRepository;
import com.colton.library_api.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookAuthorService {

    private final BookAuthorRepository bookAuthorRepository;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookAuthorService(BookRepository bookRepository,
                             AuthorRepository authorRepository,
                             BookAuthorRepository bookAuthorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.bookAuthorRepository = bookAuthorRepository;
    }

    @Transactional
    public void addAuthor(Long bookId, Long authorId, boolean primary) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));

        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + authorId));

        BookAuthor link = bookAuthorRepository
                .findByBookIdAndAuthorId(bookId, authorId)
                .orElse(null);

        if (primary) {
            bookAuthorRepository.clearPrimaryForBook(bookId);
        }

        if (link == null) {
            link = new BookAuthor(book, author, primary);
        } else {
            link.setPrimaryAuthor(primary);
        }

        bookAuthorRepository.save(link);
    }

    @Transactional
    public void removeAuthor(Long bookId, Long authorId) {

        BookAuthor link = bookAuthorRepository
                .findByBookIdAndAuthorId(bookId, authorId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Author not linked to book"));

        bookAuthorRepository.delete(link);
    }
}