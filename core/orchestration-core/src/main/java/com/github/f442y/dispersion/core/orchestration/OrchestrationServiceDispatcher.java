package com.github.f442y.dispersion.core.orchestration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;

@Component
@Slf4j
public class OrchestrationServiceDispatcher {
    private final ExecutorService executorService;

    public OrchestrationServiceDispatcher() {
        this.executorService =
                Executors.newThreadPerTaskExecutor(Thread.ofVirtual().name("orchestrator-client-vt").factory());
    }

    public void processBatch(Function<String, Future<Integer>> serviceRun, int batchSize) {
        executorService.submit(() -> {
            for (int i = 0; i < batchSize; i++) {
                serviceRun.apply("");
            }
            log.info("batch complete");
        });}
}
