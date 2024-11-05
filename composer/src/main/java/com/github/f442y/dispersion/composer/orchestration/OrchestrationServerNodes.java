package com.github.f442y.dispersion.composer.orchestration;

import com.github.f442y.dispersion.service.interfaces.AnotherSimpleOrchestrationService;
import com.github.f442y.dispersion.service.interfaces.SimpleOrchestrationService;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

public class OrchestrationServerNodes {
    @Named
    @Singleton
    public static class SimpleOrchestrationServiceOrchestratorServerNode
            extends OrchestratorServerNode<SimpleOrchestrationService> {}

    @Named
    @Singleton
    public static class AnotherSimpleOrchestrationServiceOrchestratorServerNode
            extends OrchestratorServerNode<AnotherSimpleOrchestrationService> {}
}
