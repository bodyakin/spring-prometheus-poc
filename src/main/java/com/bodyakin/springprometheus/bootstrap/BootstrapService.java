package com.bodyakin.springprometheus.bootstrap;

import com.bodyakin.springprometheus.entities.Book;
import com.bodyakin.springprometheus.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BootstrapService implements CommandLineRunner {

    private final BookRepository repository;

    @Override
    public void run(String... args) {
        repository.deleteAll();
        repository.saveAll(books());
    }

    private List<Book> books() {
        return List.of(
                new Book().setName("Spring Microservices in Action")
                        .setAuthor("JOHN CARNELL"),
                new Book().setName("Prometheus: Up & Running Infrastructure and Application Performance Monitoring")
                        .setAuthor("Brian Brazil"),
                new Book().setName("Implementing Domain-Driven Design")
                        .setAuthor("Vaughn Vernon"),
                new Book().setName("Release It!")
                        .setAuthor("Michael T. Nygard"),
                new Book().setName("Clean Code")
                        .setAuthor("Robert C. Martin"));
    }
}
