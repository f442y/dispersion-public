package com.github.f442y.dispersion.composer.playbook;

import com.github.f442y.dispersion.core.orchestration.OrchestrationEntityProvider;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;

@Component
public class SimpleOrchestrationEntityProvider implements OrchestrationEntityProvider<SimpleOrchestrationEntity> {

    private final Collection<SimpleOrchestrationEntity> entities;
    public static final SimpleOrchestrationEntity SIMPLE_ORCHESTRATION_ENTITY_ONE = new SimpleOrchestrationEntity();
    public static final SimpleOrchestrationEntity SIMPLE_ORCHESTRATION_ENTITY_TWO = new SimpleOrchestrationEntity();

    public SimpleOrchestrationEntityProvider() {
        this.entities = Set.of(SIMPLE_ORCHESTRATION_ENTITY_ONE, SIMPLE_ORCHESTRATION_ENTITY_TWO);
    }

    @Override
    public Collection<SimpleOrchestrationEntity> getEntities() {
        return entities;
    }
}
