package com.colton.library_api.controller;

import com.colton.library_api.dto.common.ApiResponse;
import com.colton.library_api.dto.common.ApiResponseFactory;
import com.colton.library_api.dto.genre.GenreRequest;
import com.colton.library_api.dto.genre.GenreResponse;
import com.colton.library_api.service.GenreService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenreController extends BaseController{
    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<GenreResponse>>> getAllGenres(HttpServletRequest httpServletRequest) {

        List<GenreResponse> genres = genreService.findAll();

        return ok(
                "Genres retrieved successfully",
                genres,
                httpServletRequest
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<GenreResponse>> getGenre(
            @PathVariable Long id,
            HttpServletRequest httpServletRequest
    ) {

        GenreResponse genre = genreService.findById(id);

        return ok(
                "Genre retrieved successfully",
                genre,
                httpServletRequest
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<GenreResponse>> createGenre(
            @Valid @RequestBody GenreRequest request
    ) {

        GenreResponse genre = genreService.createGenre(request);

        return created(
                genre,
                "/genres/" + genre.id()
        );
    }

    @DeleteMapping("/genres/{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable Long id) {
        genreService.deleteGenre(id);
        return noContent();
    }
}
