package com.github.f442y.dispersion.core.orchestration;

import java.util.Collection;

public interface OrchestrationEntityProvider<ORCHESTRATION_ENTITY extends OrchestrationEntity> {
    Collection<ORCHESTRATION_ENTITY> getEntities();
}
