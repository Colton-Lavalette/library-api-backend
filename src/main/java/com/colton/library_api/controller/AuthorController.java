package com.colton.library_api.controller;


import com.colton.library_api.dto.author.AuthorRequest;
import com.colton.library_api.dto.author.AuthorResponse;
import com.colton.library_api.dto.common.ApiResponse;
import com.colton.library_api.dto.common.ApiResponseFactory;
import com.colton.library_api.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AuthorResponse>> getAuthor(@PathVariable Long id) {

        AuthorResponse author = authorService.findById(id);

        return ResponseEntity.ok(
                ApiResponseFactory.success(
                        HttpStatus.OK,
                        "Author retrieved successfully",
                        author,
                        "/authors/" + id
                )
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AuthorResponse>> createAuthor(
            @Valid @RequestBody AuthorRequest request
    ) {

        AuthorResponse author = authorService.createAuthor(request);

        ApiResponse<AuthorResponse> response =
                ApiResponseFactory.success(
                        HttpStatus.CREATED,
                        "Author created successfully",
                        author,
                        "/authors/" + author.id()
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
