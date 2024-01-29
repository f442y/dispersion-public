package com.github.f442y.dispersion.composer.orchestration;

import com.github.f442y.dispersion.service.interfaces.AnotherSimpleOrchestrationService;
import com.github.f442y.dispersion.service.interfaces.SimpleOrchestrationService;
import org.springframework.stereotype.Component;

public class OrchestrationServerNodes {
    @Component
    public static class SimpleOrchestrationServiceOrchestratorServerNode
            extends OrchestratorServerNode<SimpleOrchestrationService> {}

    @Component
    public static class AnotherSimpleOrchestrationServiceOrchestratorServerNode
            extends OrchestratorServerNode<AnotherSimpleOrchestrationService> {}
}
