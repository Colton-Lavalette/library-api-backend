package com.colton.library_api.controller;

import com.colton.library_api.dto.bookcopy.BookCopyResponse;
import com.colton.library_api.dto.common.ApiResponse;
import com.colton.library_api.service.BookCopyService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookCopyController extends BaseController {
    private final BookCopyService bookCopyService;

    public BookCopyController(BookCopyService bookCopyService) {
        this.bookCopyService = bookCopyService;
    }

    @GetMapping("/book-copies/{id}")
    public ResponseEntity<ApiResponse<BookCopyResponse>> getBookCopyById(
            @PathVariable Long id,
            HttpServletRequest httpServletRequest
    ) {

        BookCopyResponse bookCopy = bookCopyService.findById(id);

        return ok(
                "Book copy retrieved successfully",
                bookCopy,
                httpServletRequest
        );
    }

    @GetMapping("/book-copies/code/{copyCode}")
    public ResponseEntity<ApiResponse<BookCopyResponse>> getBookCopyByCode(
            @PathVariable String copyCode,
            HttpServletRequest httpServletRequest
    ) {

        BookCopyResponse bookCopy = bookCopyService.findByCode(copyCode);

        return ok(
                "Book copy retrieved successfully",
                bookCopy,
                httpServletRequest
        );
    }

    @GetMapping("/books/{bookId}/copies")
    public ResponseEntity<ApiResponse<List<BookCopyResponse>>> getCopiesForBook(
            @PathVariable Long bookId,
            HttpServletRequest httpServletRequest
    ) {
        List<BookCopyResponse> bookCopies = bookCopyService.findByBookId(bookId);

        return ok(
                "Book copies retrieved successfully",
                bookCopies,
                httpServletRequest
        );
    }

    @PostMapping("/books/{bookId}/copies")
    public ResponseEntity<ApiResponse<BookCopyResponse>> createBookCopy(
            @PathVariable Long bookId
    ) {
        BookCopyResponse bookCopy =
                bookCopyService.createBookCopy(bookId);

        return created(
                bookCopy,
                "/book-copies/" + bookCopy.id()
        );
    }
}
