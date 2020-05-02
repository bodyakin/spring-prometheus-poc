package com.bodyakin.springprometheus.services;

import com.bodyakin.springprometheus.entities.Book;
import com.bodyakin.springprometheus.repositories.BookRepository;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    private final BookRepository repository;

    public String saveBook(Book book) {
        return repository.save(book)
                .getId();
    }

    @Counted(value = "book_get")
    public Book getBookById(String id) {
        return repository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
    }


    @Timed(value = "book_get_all", histogram = true)
    public List<Book> getAllBooks() {
        randomDelay();
        return repository.findAll();
    }

    private void randomDelay() {
        try {
            Thread.sleep((long) (Math.random() * 1000));
        } catch (InterruptedException e) {
            log.error("Thread was interrupted", e);
        }
    }
}
