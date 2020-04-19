package com.bodyakin.springprometheus.controllers;

import com.bodyakin.springprometheus.entities.Book;
import com.bodyakin.springprometheus.services.BookService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequestMapping(path = "/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService service;
    private final MeterRegistry meterRegistry;
    private Counter createBookCounter;

    @PostConstruct
    public void init() {
        this.createBookCounter = meterRegistry.counter("book_created");
    }

    @PostMapping
    public String createBook(@RequestBody Book book) {
        String saved = service.saveBook(book);
        createBookCounter.increment();
        return saved;
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable String id) {
        return service.getBookById(id);
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return service.getAllBooks();
    }

}
