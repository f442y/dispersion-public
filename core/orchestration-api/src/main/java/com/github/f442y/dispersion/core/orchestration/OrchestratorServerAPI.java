package com.github.f442y.dispersion.core.orchestration;

public interface OrchestratorServerAPI<ORCHESTRATION_SERVICE extends OrchestrationServiceAPI> {
    PlayRunOutput distributeToClients(PlayRunInput playRunInput);
}
