package com.colton.library_api.service;

import com.colton.library_api.dto.author.AuthorRequest;
import com.colton.library_api.dto.author.AuthorResponse;
import com.colton.library_api.model.Author;
import com.colton.library_api.model.Name;
import com.colton.library_api.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    private AuthorResponse mapToResponse(Author author) {
        return new AuthorResponse(
                author.getId(),
                author.getName().getFirst(),
                author.getName().getMiddle(),
                author.getName().getLast(),
                author.getBirthYear()
        );
    }

    public List<AuthorResponse> findAll() {
        return authorRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public AuthorResponse createAuthor(AuthorRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request must not be null");
        }

        Name name = new Name(
                request.first(),
                request.middle(),
                request.last()
        );

        Author author = new Author(name, request.birthYear());
        Author saved = authorRepository.save(author);

        return mapToResponse(saved);
    }

    public List<AuthorResponse> searchAuthors(String first, String last, Integer birthYear) {

        if (first != null && last != null) {
            return authorRepository.findByNameFirstAndNameLast(first, last)
                    .stream().map(this::mapToResponse).toList();
        }

        if (first != null) {
            return authorRepository.findByNameFirst(first)
                    .stream().map(this::mapToResponse).toList();
        }

        if (last != null) {
            return authorRepository.findByNameLast(last)
                    .stream().map(this::mapToResponse).toList();
        }

        if (birthYear != null) {
            return authorRepository.findByBirthYear(birthYear)
                    .stream().map(this::mapToResponse).toList();
        }

        return findAll();
    }

    public AuthorResponse updateAuthor(Long id, AuthorRequest request) {
        Author author = findByIdEntity(id);

        if (request.first() != null || request.last() != null || request.middle() != null) {
            Name current = author.getName();
            Name updatedName = new Name(
                    request.first() != null ? request.first() : current.getFirst(),
                    request.middle() != null ? request.middle() : current.getMiddle(),
                    request.last() != null ? request.last() : current.getLast()
            );
            author.updateName(updatedName);
        }

        if (request.birthYear() != null) {
            author.updateBirthYear(request.birthYear());
        }

        return mapToResponse(authorRepository.save(author));
    }

    private Author findByIdEntity(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
    }

    public void deleteAuthor(Long id) {
        Author author = findByIdEntity(id);
        authorRepository.delete(author);
    }
}
