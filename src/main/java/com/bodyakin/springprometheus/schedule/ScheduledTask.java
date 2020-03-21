package com.bodyakin.springprometheus.schedule;

import com.bodyakin.springprometheus.repositories.OrderRepository;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduledTask {

    private final OrderRepository repository;
    private final MeterRegistry registry;
    private AtomicInteger finished;
    private AtomicInteger failed;

    @PostConstruct
    public void init() {
        this.finished = registry.gauge("current.finished.orders", new AtomicInteger(0));
        this.failed = registry.gauge("current.failed.orders", new AtomicInteger(0));
    }

    @Scheduled(fixedRate = 20000)
    public void run() {
        log.info("Start task...");
        AtomicInteger finish = new AtomicInteger();
        AtomicInteger fail = new AtomicInteger();
        repository.findAll()
                .forEach(order -> {
                    try {
                        doWorkThatCanBeFailed();
                    } catch (Exception e) {
                        e.printStackTrace();
                        fail.getAndIncrement();
                    }
                    finish.getAndIncrement();
                    log.info("Process order [" + order.getId() + "]...");
                });
        this.finished.set(finish.get());
        this.failed.set(fail.get());
    }

    private void doWorkThatCanBeFailed() throws InterruptedException {
        Thread.sleep((long) (Math.random() * 500));
        if (Math.random() > 0.5) {
            throw new RuntimeException();
        }
    }
}
