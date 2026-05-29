package com.colton.library_api.controller;

import com.colton.library_api.dto.book.BookResponse;
import com.colton.library_api.dto.common.ApiResponse;
import com.colton.library_api.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

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

        ApiResponse<BookResponse> response = new ApiResponse<>(
                200,
                "OK",
                book,
                "/books/" + id,
                Instant.now().toString()
        );

        return ResponseEntity.ok(response);
    }
}