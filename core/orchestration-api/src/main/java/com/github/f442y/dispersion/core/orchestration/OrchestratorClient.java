package com.github.f442y.dispersion.core.orchestration;

public interface OrchestratorClient<ORCHESTRATION_SERVICE extends OrchestrationService> {
    PlayBatchOutput addTasksToClientNode(PlayBatchInput playRunInput);
}
