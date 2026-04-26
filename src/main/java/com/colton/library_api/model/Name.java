package com.colton.library_api.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Name {

    private String first;
    private String middle;
    private String last;

    protected Name() {}

    public Name(String first, String middle, String last) {
        this.first = requireNonBlank(first, "First name cannot be blank");
        this.middle = middle;
        this.last = requireNonBlank(last, "Last name cannot be blank");
    }

    private String requireNonBlank(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    public String getFirst() {
        return first;
    }

    public String getMiddle() {
        return middle;
    }

    public String getLast() {
        return last;
    }

}