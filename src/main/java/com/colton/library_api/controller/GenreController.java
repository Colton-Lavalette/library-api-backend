package com.colton.library_api.controller;

import com.colton.library_api.dto.common.ApiResponse;
import com.colton.library_api.dto.common.ApiResponseFactory;
import com.colton.library_api.dto.genre.GenreRequest;
import com.colton.library_api.dto.genre.GenreResponse;
import com.colton.library_api.service.GenreService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/genres")
public class GenreController {
    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<GenreResponse>> getGenre(@PathVariable Long id) {

        GenreResponse genre = genreService.findById(id);

        return ResponseEntity.ok(
                ApiResponseFactory.success(
                        HttpStatus.OK,
                        "Genre retrieved successfully",
                        genre,
                        "/genres/" + id
                )
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<GenreResponse>> createGenre(
            @Valid @RequestBody GenreRequest request
    ) {

        GenreResponse genre = genreService.createGenre(request);

        ApiResponse<GenreResponse> response =
                ApiResponseFactory.success(
                        HttpStatus.CREATED,
                        "Genre created successfully",
                        genre,
                        "/genres/" + genre.id()
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

}
