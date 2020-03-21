package com.bodyakin.springprometheus.services;

import com.bodyakin.springprometheus.entities.Order;
import com.bodyakin.springprometheus.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;

    public String saveOrder(Order order) {
        return repository.save(order)
            .getId();
    }

    public Order getOrderById(String id) {
        return repository.findById(id).orElseThrow(IllegalArgumentException::new);
    }
}
