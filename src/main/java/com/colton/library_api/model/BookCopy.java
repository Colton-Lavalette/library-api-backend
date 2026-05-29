package com.colton.library_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "book_copies")
public class BookCopy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private String copyCode;

    @Column(nullable = false)
    private boolean extant;

    @ManyToOne(optional = false)
    private Book book;

    protected BookCopy() {}

    public BookCopy(Book book, String copyCode) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        if (copyCode == null || copyCode.isBlank()) {
            throw new IllegalArgumentException("Copy code cannot be blank");
        }
        this.book = book;
        this.copyCode = copyCode;
        this.extant = true;
    }

    public Long getId() {
        return id;
    }

    public String getCopyCode() {
        return copyCode;
    }

    public Book getBook() {
        return book;
    }

    public boolean isExtant() {
        return extant;
    }

    public void markLost() {
        if (!this.extant) return;
        this.extant = false;
    }

    public void markExtant() {
        if (this.extant) return;
        this.extant = true;
    }
}