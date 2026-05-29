package com.colton.library_api.service;

import com.colton.library_api.dto.bookcopy.BookCopyRequest;
import com.colton.library_api.dto.bookcopy.BookCopyResponse;
import com.colton.library_api.exception.CopyHasLoanHistoryException;
import com.colton.library_api.exception.DuplicateCopyCodeException;
import com.colton.library_api.exception.ResourceNotFoundException;
import com.colton.library_api.model.Book;
import com.colton.library_api.model.BookCopy;
import com.colton.library_api.repository.BookCopyRepository;
import com.colton.library_api.repository.BookRepository;
import com.colton.library_api.repository.LoanRepository;
import com.colton.library_api.util.CodeGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookCopyService {
    private final BookCopyRepository bookCopyRepository;
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;

    public BookCopyService(BookCopyRepository bookCopyRepository,
                           LoanRepository loanRepository,
                           BookRepository bookRepository) {
        this.bookCopyRepository = bookCopyRepository;
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
    }

    public BookCopy findById(Long id) {
        return bookCopyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book Copy not found"));
    }

    public void deleteCopy(Long copyId) {
        BookCopy copy = bookCopyRepository.findById(copyId)
                .orElseThrow(() -> new ResourceNotFoundException("Book copy not found with id: " + copyId));

        if (loanRepository.existsByCopyId(copyId)) {
            throw new CopyHasLoanHistoryException("Cannot delete copy with loan history");
        }

        bookCopyRepository.delete(copy);
    }

    @Transactional
    public BookCopyResponse createBookCopy(BookCopyRequest request) {

        if (request == null || request.bookId() == null) {
            throw new IllegalArgumentException("BookId must not be null");
        }

        Book book = bookRepository.findById(request.bookId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Book not found with id: " + request.bookId()
                ));

        String copyCode = request.copyCode();

        if (copyCode == null || copyCode.isBlank()) {
            copyCode = generateUniqueCopyCode();
        } else if (bookCopyRepository.existsByCopyCode(copyCode)) {
            throw new DuplicateCopyCodeException("Copy code already exists: " + copyCode);
        }

        BookCopy copy = new BookCopy(book, copyCode);

        BookCopy saved = bookCopyRepository.save(copy);

        return new BookCopyResponse(
                saved.getId(),
                saved.getCopyCode(),
                book.getId(),
                saved.isExtant()
        );
    }

    private String generateUniqueCopyCode() {
        String copyCode;
        do {
            copyCode = CodeGenerator.generateCopyCode();
        } while (bookCopyRepository.existsByCopyCode(copyCode));
        return copyCode;
    }

}
