package com.colton.library_api.service;

import com.colton.library_api.dto.bookcopy.BookCopyRequest;
import com.colton.library_api.dto.bookcopy.BookCopyResponse;
import com.colton.library_api.exception.CopyHasLoanHistoryException;
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

    private BookCopyResponse mapToResponse(BookCopy copy) {
        return new BookCopyResponse(
                copy.getId(),
                copy.getCopyCode(),
                copy.getBook().getId(),
                copy.isExtant()
        );
    }

    private BookCopy findByIdEntity(Long id) {
        return bookCopyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book copy not found"));
    }

    public BookCopyResponse findById(Long id) {
        return mapToResponse(findByIdEntity(id));
    }

    private BookCopy findByCodeEntity(String copyCode) {
        return bookCopyRepository.findByCopyCode(copyCode)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Book copy not found with code: " + copyCode));
    }

    public BookCopyResponse findByCode(String copyCode) {
        return mapToResponse(findByCodeEntity(copyCode));
    }

    public void deleteCopy(Long copyId) {
        BookCopy copy = findByIdEntity(copyId);

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
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Book not found with id: " + request.bookId()
                        )
                );

        String copyCode = generateUniqueCopyCode();

        BookCopy copy = new BookCopy(book, copyCode);

        BookCopy saved = bookCopyRepository.save(copy);

        return mapToResponse(saved);
    }

    private String generateUniqueCopyCode() {
        String copyCode;
        do {
            copyCode = CodeGenerator.generateCopyCode();
        } while (bookCopyRepository.existsByCopyCode(copyCode));
        return copyCode;
    }

}
