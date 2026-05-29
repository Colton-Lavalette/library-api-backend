package com.colton.library_api.exception;

public class AuthorAlreadyLinkedException extends RuntimeException {
    public AuthorAlreadyLinkedException(Long bookId, Long authorId) {
        super("Author " + authorId + " is already linked to book " + bookId);
    }
}