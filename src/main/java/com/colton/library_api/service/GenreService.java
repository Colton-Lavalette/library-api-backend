package com.colton.library_api.service;

import com.colton.library_api.model.Genre;
import com.colton.library_api.repository.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreService {
    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public Genre createGenre(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name must not be null");
        }
        Genre genre = new Genre(name);
        return genreRepository.save(genre);
    }

    public Genre findById(Long id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre not found with id: " + id));
    }

    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    public Optional<Genre> findByName(String name) {
        return genreRepository.findByName(name);
    }


}
