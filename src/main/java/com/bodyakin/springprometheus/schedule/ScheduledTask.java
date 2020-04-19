package com.bodyakin.springprometheus.schedule;

import com.bodyakin.springprometheus.entities.Book;
import com.bodyakin.springprometheus.repositories.BookRepository;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduledTask {

    private final BookRepository repository;
    private final MeterRegistry registry;
    private AtomicInteger finished;
    private AtomicInteger failed;

    @PostConstruct
    public void init() {
        this.finished = registry.gauge("book.processing.success", new AtomicInteger(0));
        this.failed = registry.gauge("book.processing.fail", new AtomicInteger(0));
    }

    @Scheduled(fixedDelay = 120_000)
    @Timed(value = "book.processing", longTask = true)
    public void run() {
        log.info("Start long task...");
        AtomicInteger successCount = new AtomicInteger();
        AtomicInteger failCount = new AtomicInteger();
        this.finished.set(0);
        this.failed.set(0);
        repository.findAll()
                .forEach(book -> process(book, successCount, failCount));
        this.finished.set(successCount.get());
        this.failed.set(failCount.get());
    }

    private void process(Book book, AtomicInteger successCount, AtomicInteger failCount) {
        log.info("Process book [" + book.getName() + "]...");
        try {
            doWorkThatCanBeFailed();
            successCount.getAndIncrement();
        } catch (Exception e) {
            log.error("Error during processing", e);
            failCount.getAndIncrement();
        }
    }

    private void doWorkThatCanBeFailed() throws InterruptedException {
        TimeUnit.SECONDS.sleep((long) (20 * Math.random()));
        if (Math.random() > 0.7) {
            throw new RuntimeException("Something bad happened.");
        }
    }
}
