package com.colton.library_api.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 13, unique = true, nullable = false, updatable = false)
    private String isbn;

    private Integer publishedYear;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<BookAuthor> authors = new ArrayList<>();

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<BookGenre> genres = new ArrayList<>();

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<BookCopy> copies = new ArrayList<>();

    public Book() {}

    public Book(String title, String isbn, Integer publishedYear) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be blank");
        }
        if (isbn == null || isbn.isBlank()) {
            throw new IllegalArgumentException("ISBN cannot be blank");
        }
        this.title = title;
        this.isbn = isbn;
        this.publishedYear = publishedYear;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public Integer getPublishedYear() {
        return publishedYear;
    }

    public List<BookAuthor> getAuthors() {
        return authors;
    }

    public List<BookGenre> getGenres() {
        return genres;
    }

    public List<BookCopy> getCopies() {
        return copies;
    }

    public void updateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be blank");
        }
        this.title = title;
    }

    public void updatePublishedYear(Integer publishedYear) {
        this.publishedYear = publishedYear;
    }

    public void addAuthor(Author author, boolean primary) {
        BookAuthor link = new BookAuthor(this, author, primary);
        authors.add(link);
    }

    public void addGenre(Genre genre, boolean primary) {
        BookGenre link = new BookGenre(this, genre, primary);
        genres.add(link);
    }

    public BookCopy addCopy() {
        BookCopy copy = new BookCopy(this);
        copies.add(copy);
        return copy;
    }
}