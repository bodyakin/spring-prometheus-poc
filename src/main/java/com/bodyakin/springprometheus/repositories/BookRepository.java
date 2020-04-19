package com.bodyakin.springprometheus.repositories;

import com.bodyakin.springprometheus.entities.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepository extends MongoRepository<Book, String> {
}
