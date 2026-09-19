-- Authors
INSERT INTO authors (first, middle, last, birth_year) VALUES
                                                          ('George',  null,    'Orwell',      1903),
                                                          ('Harper',  null,    'Lee',         1926),
                                                          ('Frank',   null,    'Herbert',     1920),
                                                          ('F.',      'Scott', 'Fitzgerald',  1896),
                                                          ('Aldous',  null,    'Huxley',      1894);

-- Genres
INSERT INTO genres (name) VALUES
                              ('Dystopian Fiction'),
                              ('Literary Fiction'),
                              ('Science Fiction'),
                              ('Mystery'),
                              ('Fantasy');

-- Books
INSERT INTO books (title, isbn, published_year) VALUES
                                                    ('1984',                      '9780451524935', 1949),
                                                    ('To Kill a Mockingbird',     '9780061743528', 1960),
                                                    ('Dune',                      '9780441013593', 1965),
                                                    ('The Great Gatsby',          '9780743273565', 1925),
                                                    ('Brave New World',           '9780060850524', 1932);

-- Book Authors
INSERT INTO book_authors (book_id, author_id, primary_author)
VALUES
    (
        (SELECT id FROM books WHERE isbn='9780451524935'),
        (SELECT id FROM authors WHERE first='George' AND last='Orwell'),
        true
    ),
    (
        (SELECT id FROM books WHERE isbn='9780061743528'),
        (SELECT id FROM authors WHERE first='Harper' AND last='Lee'),
        true
    ),
    (
        (SELECT id FROM books WHERE isbn='9780441013593'),
        (SELECT id FROM authors WHERE first='Frank' AND last='Herbert'),
        true
    ),
    (
        (SELECT id FROM books WHERE isbn='9780743273565'),
        (SELECT id FROM authors WHERE first='F.' AND last='Fitzgerald'),
        true
    ),
    (
        (SELECT id FROM books WHERE isbn='9780060850524'),
        (SELECT id FROM authors WHERE first='Aldous' AND last='Huxley'),
        true
    );

-- Book Genres
INSERT INTO book_genres (book_id, genre_id, primary_genre)
VALUES
    ((SELECT id FROM books WHERE isbn='9780451524935'),
     (SELECT id FROM genres WHERE name='Dystopian Fiction'),
     true),

    ((SELECT id FROM books WHERE isbn='9780451524935'),
     (SELECT id FROM genres WHERE name='Science Fiction'),
     false);