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
 * Explicit join entity between Book and Author.
 * Used instead of @ManyToMany so the relationship can carry
 * additional domain data (e.g., primaryAuthor).
 */
@Entity
@Table(
        name = "book_authors",
        uniqueConstraints = @UniqueConstraint(columnNames = {"book_id", "author_id"})
)
public class BookAuthor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Book book;

    @ManyToOne(optional = false)
    private Author author;

    @Column(nullable = false)
    private boolean primaryAuthor;

    protected BookAuthor() {}

    public BookAuthor(Book book, Author author, boolean primaryAuthor) {
        if (book == null || author == null) {
            throw new IllegalArgumentException("Book and Author cannot be null");
        }

        this.book = book;
        this.author = author;
        this.primaryAuthor = primaryAuthor;
    }

    public Long getId() {
        return id;
    }

    public Book getBook() {
        return book;
    }

    public Author getAuthor() {
        return author;
    }

    public boolean isPrimaryAuthor() {
        return primaryAuthor;
    }

    public void setPrimaryAuthor(boolean primaryAuthor) {
        this.primaryAuthor = primaryAuthor;
    }

}