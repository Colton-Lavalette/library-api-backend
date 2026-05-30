package com.colton.library_api.controller;

import com.colton.library_api.dto.book.BookRequest;
import com.colton.library_api.dto.book.BookResponse;
import com.colton.library_api.dto.common.ApiResponse;
import com.colton.library_api.dto.common.ApiResponseFactory;
import com.colton.library_api.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookResponse>> getBook(@PathVariable Long id) {

        BookResponse book = bookService.findById(id);

        return ResponseEntity.ok(
                ApiResponseFactory.success(
                        HttpStatus.OK,
                        "Book retrieved successfully",
                        book,
                        "/books/" + id
                )
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BookResponse>> createBook(
            @Valid @RequestBody BookRequest request
    ) {

        BookResponse book = bookService.createBook(request);

        ApiResponse<BookResponse> response =
                ApiResponseFactory.success(
                        HttpStatus.CREATED,
                        "Book created successfully",
                        book,
                        "/books/" + book.id()
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}