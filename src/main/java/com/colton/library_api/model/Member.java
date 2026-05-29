package com.colton.library_api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private String memberCode;

    @Embedded
    private Name name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private boolean active;

    protected Member() {}

    public Member(String memberCode, Name name, String email) {
        if (memberCode == null || memberCode.isBlank()) {
            throw new IllegalArgumentException("Member code cannot be null or blank");
        }
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or blank");
        }

        this.memberCode = memberCode;
        this.name = name;
        this.email = email;
        this.active = true;
    }

    public Long getId() {
        return id;
    }

    public String getMemberCode() {
        return memberCode;
    }

    public Name getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void updateName(Name name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        this.name = name;
    }

    public void updateEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or blank");
        }
        this.email = email;
    }

    public String getDisplayName() {
        StringBuilder fullName = new StringBuilder();

        fullName.append(name.getFirst());

        if (name.getMiddle() != null && !name.getMiddle().isBlank()) {
            fullName.append(" ").append(name.getMiddle());
        }

        fullName.append(" ").append(name.getLast());

        return fullName.toString();
    }

    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        if (!this.active) return;
        this.active = false;
    }

    public void activate() {
        this.active = true;
    }
}