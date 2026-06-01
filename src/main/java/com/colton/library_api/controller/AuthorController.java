package com.colton.library_api.controller;


import com.colton.library_api.dto.author.AuthorRequest;
import com.colton.library_api.dto.author.AuthorResponse;
import com.colton.library_api.dto.common.ApiResponse;
import com.colton.library_api.service.AuthorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController extends BaseController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AuthorResponse>>> getAllAuthors(HttpServletRequest httpServletRequest) {

        List<AuthorResponse> authors = authorService.findAll();

        return ok(
                "Authors retrieved successfully",
                authors,
                httpServletRequest
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AuthorResponse>> getAuthor(
            @PathVariable Long id,
            HttpServletRequest httpServletRequest
    ) {
        AuthorResponse author = authorService.findById(id);

        return ok(
                "Author retrieved successfully",
                author,
                httpServletRequest
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AuthorResponse>> createAuthor(
            @Valid @RequestBody AuthorRequest authorRequest
    ) {

        AuthorResponse author = authorService.createAuthor(authorRequest);

        return created(
                author,
                "/authors/" + author.id()
        );
    }

    @DeleteMapping("/authors/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return noContent();
    }
}
