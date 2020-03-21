package com.bodyakin.springprometheus.controllers;

import com.bodyakin.springprometheus.entities.Order;
import com.bodyakin.springprometheus.services.OrderService;
import io.micrometer.core.annotation.Counted;
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

@RestController
@RequestMapping(path = "/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;
    private final MeterRegistry meterRegistry;
    private Counter createOrderCounter;

    @PostConstruct
    public void init() {
        this.createOrderCounter = meterRegistry.counter("order.created");
    }

    @PostMapping
    public String createOrder(@RequestBody Order order) {
        String saved = service.saveOrder(order);
        createOrderCounter.increment();
        return saved;
    }

    @GetMapping("/{id}")
    @Counted(value = "Get order by id")
    public Order getOrderById(@PathVariable String id) throws InterruptedException {
        Thread.sleep((long) (Math.random() * 1000));
        return service.getOrderById(id);
    }


}
