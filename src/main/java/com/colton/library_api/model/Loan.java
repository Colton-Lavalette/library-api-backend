package com.colton.library_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "loans")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Member member;

    @ManyToOne(optional = false)
    private BookCopy copy;

    @Column(nullable = false)
    private LocalDate loanDate;

    private LocalDate dueDate;

    private LocalDate returnDate;

    protected Loan() {}

    public Loan(Member member, BookCopy copy, LocalDate loanDate, LocalDate dueDate) {
        if (member == null) {
            throw new IllegalArgumentException("Member cannot be null");
        }
        if (copy == null) {
            throw new IllegalArgumentException("BookCopy cannot be null");
        }
        if (loanDate == null) {
            throw new IllegalArgumentException("Loan date cannot be null");
        }
        this.member = member;
        this.copy = copy;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public BookCopy getCopy() {
        return copy;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public boolean isActive() {
        return returnDate == null;
    }

    public void returnBook(LocalDate returnDate) {
        if (returnDate == null) {
            throw new IllegalArgumentException("Return date cannot be null");
        }

        if (this.returnDate != null) {
            throw new IllegalStateException("Book already returned");
        }

        if (returnDate.isBefore(loanDate)) {
            throw new IllegalArgumentException("Return date cannot be before loan date");
        }
        this.returnDate = returnDate;
    }
}