package com.github.f442y.dispersion.core.orchestration;

public interface OrchestratorServer<ORCHESTRATION_SERVICE extends OrchestrationService> {
    PlayRunOutput distributeToClients(PlayRunInput playRunInput);
}
