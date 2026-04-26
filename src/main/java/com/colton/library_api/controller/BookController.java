package com.colton.library_api.controller;

import com.colton.library_api.model.Book;
import com.colton.library_api.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public Book createBook(@RequestParam String title,
                           @RequestParam String isbn,
                           @RequestParam(required = false) Integer publishedYear) {
        return bookService.createBook(title, isbn, publishedYear);
    }

    @GetMapping("/{id}")
    public Book getById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @GetMapping
    public List<Book> getAll() {
        return bookService.findAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
}