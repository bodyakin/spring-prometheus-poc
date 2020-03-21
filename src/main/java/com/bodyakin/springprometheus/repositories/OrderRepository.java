package com.bodyakin.springprometheus.repositories;

import com.bodyakin.springprometheus.entities.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String> {
}
