package com.colton.library_api.controller;

import com.colton.library_api.dto.book.*;
import com.colton.library_api.dto.common.ApiResponse;
import com.colton.library_api.dto.common.ApiResponseFactory;
import com.colton.library_api.service.BookAuthorService;
import com.colton.library_api.service.BookGenreService;
import com.colton.library_api.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookAuthorService bookAuthorService;
    private final BookService bookService;
    private final BookGenreService bookGenreService;

    public BookController(BookAuthorService bookAuthorService, BookService bookService, BookGenreService bookGenreService) {
        this.bookAuthorService = bookAuthorService;
        this.bookService = bookService;
        this.bookGenreService = bookGenreService;
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

    @GetMapping("/{bookId}/authors")
    public ResponseEntity<List<BookAuthorResponse>> getAuthorsForBook(
            @PathVariable Long bookId) {

        return ResponseEntity.ok(bookService.getAuthorsForBook(bookId));
    }

    @GetMapping("/{bookId}/genres")
    public ResponseEntity<List<BookGenreResponse>> getGenresForBook(
            @PathVariable Long bookId) {

        return ResponseEntity.ok(bookService.getGenresForBook(bookId));
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

    @PostMapping("/{bookId}/authors")
    public ResponseEntity<Void> addAuthor(
            @PathVariable Long bookId,
            @RequestBody AddAuthorRequest request) {

        bookAuthorService.addAuthor(
                bookId,
                request.authorId(),
                request.primaryAuthor());

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{bookId}/genres")
    public ResponseEntity<Void> addGenre(
            @PathVariable Long bookId,
            @RequestBody AddGenreRequest request) {

        bookGenreService.addGenre(
                bookId,
                request.genreId(),
                request.primaryGenre()
        );

        return ResponseEntity.noContent().build();
    }
}