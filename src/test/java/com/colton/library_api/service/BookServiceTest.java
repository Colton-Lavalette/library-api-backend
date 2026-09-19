package com.colton.library_api.service;

import com.colton.library_api.dto.book.BookResponse;
import com.colton.library_api.model.Book;
import com.colton.library_api.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void findAll_returnsAllBooks() {
        // Arrange
        Book book1 = new Book();
        Book book2 = new Book();

        when(bookRepository.findAll(Sort.by("id").ascending()))
                .thenReturn(List.of(book1, book2));

        // Act
        List<BookResponse> result = bookService.findAll();

        // Assert
        assertEquals(2, result.size());
        verify(bookRepository).findAll(Sort.by("id").ascending());
    }

    @Test
    void findById() {
    }

    @Test
    void getAuthorsForBook() {
    }

    @Test
    void getGenresForBook() {
    }

    @Test
    void createBook() {
    }

    @Test
    void updateBook() {
    }
}