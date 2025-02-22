package com.github.f442y.dispersion.core.orchestration;

public interface OrchestratorClientAPI<ORCHESTRATION_SERVICE extends OrchestrationServiceAPI> {
    PlayBatchOutput addTasksToClientNode(PlayBatchInput playRunInput);
}
