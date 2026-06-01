package com.colton.library_api.service;

import com.colton.library_api.dto.genre.GenreRequest;
import com.colton.library_api.dto.genre.GenreResponse;
import com.colton.library_api.exception.ResourceInUseException;
import com.colton.library_api.exception.ResourceNotFoundException;
import com.colton.library_api.model.Genre;
import com.colton.library_api.repository.BookGenreRepository;
import com.colton.library_api.repository.GenreRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GenreService {
    private final GenreRepository genreRepository;
    private final BookGenreRepository bookGenreRepository;

    public GenreService(GenreRepository genreRepository, BookGenreRepository bookGenreRepository) {
        this.genreRepository = genreRepository;
        this.bookGenreRepository = bookGenreRepository;
    }

    private GenreResponse mapToResponse(Genre genre) {
        return new GenreResponse(
                genre.getId(),
                genre.getName()
        );
    }

    public List<GenreResponse> findAll() {
        return genreRepository.findAll(Sort.by("id"))
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private Genre findByIdEntity(Long id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre not found with id: " + id));
    }

    public GenreResponse findById(Long id) {
        return mapToResponse(findByIdEntity(id));
    }

    private Genre findByNameEntity(String name) {
        return genreRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Genre not found: " + name));
    }

    public GenreResponse findByName(String name) {
        return mapToResponse(findByNameEntity(name));
    }

    public GenreResponse createGenre(GenreRequest genreRequest) {

        String name = genreRequest.name();

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name must not be null");
        }

        Genre genre = new Genre(name);
        return mapToResponse(genreRepository.save(genre));
    }

    @Transactional
    public GenreResponse updateGenreName(Long id, String name) {
        Genre genre = findByIdEntity(id);

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name must not be null");
        }

        genre.updateName(name);

        return mapToResponse(genre);
    }

    public void deleteGenre(Long id) {
        Genre genre = findByIdEntity(id);

        boolean inUse = bookGenreRepository.existsByGenreId(id);

        if (inUse) {
            throw new ResourceInUseException("Genre is linked to one or books and cannot be deleted");
        }

        genreRepository.delete(genre);
    }
}
