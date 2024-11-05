package com.github.f442y.dispersion.core.orchestration;

public interface OrchestratorServerAPI<ORCHESTRATION_SERVICE extends OrchestrationService> {
    PlayRunOutput distributeToClients(PlayRunInput playRunInput);
}
