package com.colton.library_api.controller;

import com.colton.library_api.dto.bookcopy.BookCopyRequest;
import com.colton.library_api.dto.bookcopy.BookCopyResponse;
import com.colton.library_api.dto.common.ApiResponse;
import com.colton.library_api.dto.common.ApiResponseFactory;
import com.colton.library_api.service.BookCopyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book-copies")
public class BookCopyController {
    private final BookCopyService bookCopyService;

    public BookCopyController(BookCopyService bookCopyService) {
        this.bookCopyService = bookCopyService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookCopyResponse>> getBookCopyById(@PathVariable Long id) {

        BookCopyResponse bookCopy = bookCopyService.findById(id);

        return ResponseEntity.ok(
                ApiResponseFactory.success(
                        HttpStatus.OK,
                        "Book copy retrieved successfully",
                        bookCopy,
                        "/book-copies/" + id
                )
        );
    }

    @GetMapping("/code/{copyCode}")
    public ResponseEntity<ApiResponse<BookCopyResponse>> getBookCopyByCode(@PathVariable String copyCode) {

        BookCopyResponse bookCopy = bookCopyService.findByCode(copyCode);

        return ResponseEntity.ok(
                ApiResponseFactory.success(
                        HttpStatus.OK,
                        "Book copy retrieved successfully",
                        bookCopy,
                        "/book-copies/code/" + copyCode
                )
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BookCopyResponse>> createBookCopy(
            @Valid @RequestBody BookCopyRequest request
    ) {

        BookCopyResponse bookCopy = bookCopyService.createBookCopy(request);

        ApiResponse<BookCopyResponse> response =
                ApiResponseFactory.success(
                        HttpStatus.CREATED,
                        "Copy created successfully",
                        bookCopy,
                        "/book-copies/" + bookCopy.id()
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

}
