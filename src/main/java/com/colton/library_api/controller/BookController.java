package com.colton.library_api.controller;

import com.colton.library_api.dto.book.*;
import com.colton.library_api.dto.common.ApiResponse;
import com.colton.library_api.service.BookAuthorService;
import com.colton.library_api.service.BookGenreService;
import com.colton.library_api.service.BookService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController extends BaseController {

    private final BookAuthorService bookAuthorService;
    private final BookService bookService;
    private final BookGenreService bookGenreService;

    public BookController(BookAuthorService bookAuthorService,
                          BookService bookService,
                          BookGenreService bookGenreService) {
        this.bookAuthorService = bookAuthorService;
        this.bookService = bookService;
        this.bookGenreService = bookGenreService;
    }


    @GetMapping
    public ResponseEntity<ApiResponse<List<BookResponse>>> getAllBooks(HttpServletRequest httpServletRequest) {

        List<BookResponse> books = bookService.findAll();

        return ok(
                "Books retrieved successfully",
                books,
                httpServletRequest
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookResponse>> getBook(
            @PathVariable Long id,
            HttpServletRequest httpServletRequest
    ) {
        BookResponse book = bookService.findById(id);

        return ok(
                "Book retrieved successfully",
                book,
                httpServletRequest
        );
    }

    @GetMapping("/{bookId}/authors")
    public ResponseEntity<ApiResponse<List<BookAuthorResponse>>> getAuthorsForBook(
            @PathVariable Long bookId,
            HttpServletRequest httpServletRequest
    ) {
        return ok(
                "Authors retrieved successfully",
                bookService.getAuthorsForBook(bookId),
                httpServletRequest
        );
    }

    @GetMapping("/{bookId}/genres")
    public ResponseEntity<ApiResponse<List<BookGenreResponse>>> getGenresForBook(
            @PathVariable Long bookId,
            HttpServletRequest httpServletRequest
    ) {
        return ok(
                "Genres retrieved successfully",
                bookService.getGenresForBook(bookId),
                httpServletRequest
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BookResponse>> createBook(
            @Valid @RequestBody BookRequest bookRequest
    ) {

        BookResponse book = bookService.createBook(bookRequest);

        return created(
                book,
                "/books/" + book.id()
        );
    }

    @PostMapping("/{bookId}/authors")
    public ResponseEntity<Void> addAuthor(
            @PathVariable Long bookId,
            @RequestBody AddAuthorRequest addAuthorRequest
    ) {
        bookAuthorService.addAuthor(
                bookId,
                addAuthorRequest.authorId(),
                addAuthorRequest.primaryAuthor()
        );

        return noContent();
    }

    @PostMapping("/{bookId}/genres")
    public ResponseEntity<Void> addGenre(
            @PathVariable Long bookId,
            @RequestBody AddGenreRequest addGenreRequest
    ) {
        bookGenreService.addGenre(
                bookId,
                addGenreRequest.genreId(),
                addGenreRequest.primaryGenre()
        );

        return noContent();
    }

    @DeleteMapping("/{bookId}/authors/{authorId}")
    public ResponseEntity<Void> removeAuthor(
            @PathVariable Long bookId,
            @PathVariable Long authorId
    ) {
        bookAuthorService.removeAuthor(bookId, authorId);
        return noContent();
    }

    @DeleteMapping("/{bookId}/genres/{genreId}")
    public ResponseEntity<Void> removeGenre(
            @PathVariable Long bookId,
            @PathVariable Long genreId
    ) {
        bookGenreService.removeGenre(bookId, genreId);
        return noContent();
    }
}