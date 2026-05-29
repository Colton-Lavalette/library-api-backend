CREATE TABLE authors (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         first VARCHAR(255) NOT NULL,
                         middle VARCHAR(255),
                         last VARCHAR(255),
                         birth_year INT
);

CREATE TABLE members (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         member_code VARCHAR(255) NOT NULL UNIQUE,
                         first VARCHAR(255) NOT NULL,
                         middle VARCHAR(255),
                         last VARCHAR(255),
                         email VARCHAR(255) NOT NULL UNIQUE,
                         active BOOLEAN NOT NULL
);

CREATE TABLE genres (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE books (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       isbn VARCHAR(13) NOT NULL UNIQUE,
                       published_year INT
);

CREATE TABLE book_copies (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             copy_code VARCHAR(255) NOT NULL UNIQUE,
                             extant BOOLEAN NOT NULL,
                             book_id BIGINT NOT NULL,
                             CONSTRAINT fk_book_copies_book
                                 FOREIGN KEY (book_id) REFERENCES books(id)
);

CREATE TABLE book_authors (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              book_id BIGINT NOT NULL,
                              author_id BIGINT NOT NULL,
                              primary_author BOOLEAN NOT NULL,

                              CONSTRAINT fk_book_authors_book
                                  FOREIGN KEY (book_id) REFERENCES books(id),

                              CONSTRAINT fk_book_authors_author
                                  FOREIGN KEY (author_id) REFERENCES authors(id),

                              CONSTRAINT uq_book_author UNIQUE (book_id, author_id)
);

CREATE TABLE book_genres (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             book_id BIGINT NOT NULL,
                             genre_id BIGINT NOT NULL,
                             primary_genre BOOLEAN NOT NULL,

                             CONSTRAINT fk_book_genres_book
                                 FOREIGN KEY (book_id) REFERENCES books(id),

                             CONSTRAINT fk_book_genres_genre
                                 FOREIGN KEY (genre_id) REFERENCES genres(id),

                             CONSTRAINT uq_book_genre UNIQUE (book_id, genre_id)
);

CREATE TABLE loans (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       member_id BIGINT NOT NULL,
                       copy_id BIGINT NOT NULL,
                       loan_date DATE NOT NULL,
                       due_date DATE,
                       return_date DATE,

                       CONSTRAINT fk_loans_member
                           FOREIGN KEY (member_id) REFERENCES members(id),

                       CONSTRAINT fk_loans_copy
                           FOREIGN KEY (copy_id) REFERENCES book_copies(id)
);
