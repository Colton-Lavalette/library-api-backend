package com.colton.library_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

/**
 * Explicit join entity between Book and Genre.
 * Used instead of @ManyToMany so the relationship can carry
 * additional domain data (e.g., primaryGenre).
 */
@Entity
@Table(
        name = "book_genres",
        uniqueConstraints = @UniqueConstraint(columnNames = {"book_id", "genre_id"})
)
public class BookGenre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Book book;

    @ManyToOne(optional = false)
    private Genre genre;

    @Column(nullable = false)
    private boolean primaryGenre;

    protected BookGenre() {}

    public BookGenre(Book book, Genre genre, boolean primaryGenre) {
        if (book == null || genre == null) {
            throw new IllegalArgumentException("Book and Genre cannot be null");
        }

        this.book = book;
        this.genre = genre;
        this.primaryGenre = primaryGenre;
    }

    public Long getId() {
        return id;
    }

    public Book getBook() {
        return book;
    }

    public Genre getGenre() {
        return genre;
    }

    public boolean isPrimaryGenre() {
        return primaryGenre;
    }

    public void setPrimaryGenre(boolean primaryGenre) {
        this.primaryGenre = primaryGenre;
    }

}