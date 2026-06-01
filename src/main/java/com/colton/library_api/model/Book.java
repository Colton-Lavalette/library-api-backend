package com.colton.library_api.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 13, unique = true, nullable = false)
    private String isbn;

    private Integer publishedYear;

    @OneToMany(mappedBy = "book")
    @OrderBy("primaryAuthor DESC, author.id ASC")
    private final List<BookAuthor> authors = new ArrayList<>();

    @OneToMany(mappedBy = "book")
    @OrderBy("primaryGenre DESC, genre.id ASC")
    private final List<BookGenre> genres = new ArrayList<>();

    @OneToMany(mappedBy = "book")
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
        return Collections.unmodifiableList(authors);
    }

    public List<BookGenre> getGenres() {
        return Collections.unmodifiableList(genres);
    }

    public List<BookCopy> getCopies() {
        return Collections.unmodifiableList(copies);
    }

    public void updateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be blank");
        }
        this.title = title;
    }

    public void updateIsbn(String isbn) {
        if (isbn == null || isbn.isBlank()) {
            throw new IllegalArgumentException("ISBN cannot be blank");
        }
        this.isbn = isbn;
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

    public BookCopy addCopy(String copyCode) {
        if (copyCode == null || copyCode.isBlank()) {
            throw new IllegalArgumentException("Copy code cannot be blank");
        }
        BookCopy copy = new BookCopy(this, copyCode);
        copies.add(copy);
        return copy;
    }

}