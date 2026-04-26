package com.colton.library_api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "genres")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    protected Genre() {}

    public Genre(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Genre name cannot be blank");
        }
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void updateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Genre name cannot be null or blank");
        }
        this.name = name;
    }
}
