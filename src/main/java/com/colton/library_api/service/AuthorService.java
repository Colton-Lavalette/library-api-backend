package com.colton.library_api.service;

import com.colton.library_api.dto.author.AuthorRequest;
import com.colton.library_api.dto.author.AuthorResponse;
import com.colton.library_api.exception.ResourceNotFoundException;
import com.colton.library_api.model.Author;
import com.colton.library_api.model.Name;
import com.colton.library_api.repository.AuthorRepository;
import com.colton.library_api.specification.AuthorSpecification;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.domain.Specification;
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

    public AuthorResponse createAuthor(AuthorRequest authorRequest) {
        if (authorRequest == null) {
            throw new IllegalArgumentException("Request must not be null");
        }

        Name name = new Name(
                authorRequest.first(),
                authorRequest.middle(),
                authorRequest.last()
        );

        Author author = new Author(name, authorRequest.birthYear());
        authorRepository.save(author);
        return mapToResponse(author);
    }

    public List<AuthorResponse> searchAuthors(String first, String last, Integer birthYear) {
        Specification<Author> spec = (root, query, cb) -> cb.conjunction();

        if (first != null) spec = spec.and(AuthorSpecification.hasFirstName(first));
        if (last != null) spec = spec.and(AuthorSpecification.hasLastName(last));
        if (birthYear != null) spec = spec.and(AuthorSpecification.hasBirthYear(birthYear));

        return authorRepository.findAll(spec)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private Author findByIdEntity(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
    }

    public AuthorResponse findById(Long id) {
        return mapToResponse(findByIdEntity(id));
    }

    @Transactional
    public AuthorResponse updateAuthor(Long id, AuthorRequest authorRequest) {
        Author author = findByIdEntity(id);

        if (authorRequest.first() != null || authorRequest.last() != null || authorRequest.middle() != null) {
            Name current = author.getName();
            Name updatedName = new Name(
                    authorRequest.first() != null ? authorRequest.first() : current.getFirst(),
                    authorRequest.middle() != null ? authorRequest.middle() : current.getMiddle(),
                    authorRequest.last() != null ? authorRequest.last() : current.getLast()
            );
            author.updateName(updatedName);
        }

        if (authorRequest.birthYear() != null) {
            author.updateBirthYear(authorRequest.birthYear());
        }

        return mapToResponse(author);
    }

    public void deleteAuthor(Long id) {
        Author author = findByIdEntity(id);
        authorRepository.delete(author);
    }
}
