package com.bodyakin.springprometheus.metrics;

import com.bodyakin.springprometheus.repositories.BookRepository;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookRepositoryMetrics {

    private final MeterRegistry meterRegistry;
    private final BookRepository repository;

    @EventListener(classes = ApplicationReadyEvent.class)
    public void applicationReadyEvent() {
        Gauge.builder("books.count",
                this, BookRepositoryMetrics::getBookCounts)
                .description("Books collections size")
                .register(meterRegistry);
    }

    private double getBookCounts() {
        return repository.count();
    }

}
