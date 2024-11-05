package com.github.f442y.dispersion.samples.OrchestratedSMService;

import com.github.f442y.dispersion.core.orchestration.OrchestrationServiceDispatcher;
import com.github.f442y.dispersion.core.orchestration.OrchestratorClientNode;
import com.github.f442y.dispersion.core.orchestration.PlayBatchInput;
import com.github.f442y.dispersion.core.orchestration.PlayBatchOutput;
import com.github.f442y.dispersion.core.orchestration.PlayBatchOutputToken;
import com.github.f442y.dispersion.samples.TestStateMachineSpring.service.BufferedBlockingSpawningServiceImpl;
import com.github.f442y.dispersion.service.interfaces.SimpleOrchestrationService;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.function.Function;

@Slf4j
@Named
@Singleton
public class SimpleOrchestrationServiceClient extends OrchestratorClientNode<SimpleOrchestrationService> {
    private final BufferedBlockingSpawningServiceImpl bufferedBlockingSpawningService;
    private final OrchestrationServiceDispatcher orchestrationServiceDispatcher;

    private ConcurrentHashMap<String, String> batchMap = new ConcurrentHashMap<>();

    @Inject
    public SimpleOrchestrationServiceClient(BufferedBlockingSpawningServiceImpl bufferedBlockingSpawningService,
                                            OrchestrationServiceDispatcher orchestrationServiceDispatcher
    ) {
        this.bufferedBlockingSpawningService = bufferedBlockingSpawningService;
        this.orchestrationServiceDispatcher = orchestrationServiceDispatcher;
    }

    @Override
    public PlayBatchOutput addTasksToClientNode(PlayBatchInput playBatchInput) {
        log.info("running orch service on client node");
        Function<String, Future<Integer>> serviceFn = (string) -> {
            try {
                return bufferedBlockingSpawningService.dispatch().future();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        orchestrationServiceDispatcher.processBatch(serviceFn, 1_000_000);
        return new PlayBatchOutputToken();
    }
}
