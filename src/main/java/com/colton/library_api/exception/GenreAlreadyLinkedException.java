package com.colton.library_api.exception;

public class GenreAlreadyLinkedException extends RuntimeException {
    public GenreAlreadyLinkedException(Long bookId, Long genreId) {
        super("Genre " + genreId + " is already linked to book " + bookId);
    }
}
