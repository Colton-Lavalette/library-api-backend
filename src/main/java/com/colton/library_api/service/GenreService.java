package com.colton.library_api.service;

import com.colton.library_api.dto.genre.GenreRequest;
import com.colton.library_api.dto.genre.GenreResponse;
import com.colton.library_api.exception.ResourceNotFoundException;
import com.colton.library_api.model.Genre;
import com.colton.library_api.repository.GenreRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {
    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    private GenreResponse mapToResponse(Genre genre) {
        return new GenreResponse(
                genre.getId(),
                genre.getName()
        );
    }

    public GenreResponse createGenre(GenreRequest genreRequest) {

        String name = genreRequest.name();

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name must not be null");
        }

        Genre genre = new Genre(name);
        return mapToResponse(genreRepository.save(genre));
    }

    public GenreResponse findById(Long id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre not found with id: " + id));

        return mapToResponse(genre);
    }

    public List<GenreResponse> findAll() {
        return genreRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public GenreResponse findByName(String name) {
        return mapToResponse(genreRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Genre not found with name: " + name)));
    }

    @Transactional
    public GenreResponse updateGenreName(Long id, String name) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre not found with id: " + id));
        genre.updateName(name);
        return mapToResponse(genre);
    }
}
