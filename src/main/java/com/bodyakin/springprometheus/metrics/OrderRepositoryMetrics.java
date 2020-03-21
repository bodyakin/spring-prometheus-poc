package com.bodyakin.springprometheus.metrics;

import com.bodyakin.springprometheus.repositories.OrderRepository;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderRepositoryMetrics {

    private final MeterRegistry meterRegistry;
    private final OrderRepository repository;

    @EventListener(classes = ApplicationReadyEvent.class)
    public void applicationReadyEvent() {
        Gauge.builder("orders.count",
            this, OrderRepositoryMetrics::getOrderCounts)
            .description("Order database size")
            .register(meterRegistry);
    }

    private double getOrderCounts() {
        return repository.count();
    }

}
