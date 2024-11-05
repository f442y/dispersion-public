package com.github.f442y.dispersion.composer.playbook;

import com.github.f442y.dispersion.core.orchestration.OrchestrationEntityProvider;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import java.util.Collection;
import java.util.Set;

@Named
@Singleton
public class SimpleOrchestrationEntityProvider implements OrchestrationEntityProvider<SimpleOrchestrationEntity> {

    public static final SimpleOrchestrationEntity SIMPLE_ORCHESTRATION_ENTITY_ONE = new SimpleOrchestrationEntity();
    public static final SimpleOrchestrationEntity SIMPLE_ORCHESTRATION_ENTITY_TWO = new SimpleOrchestrationEntity();
    private final Collection<SimpleOrchestrationEntity> entities;

    public SimpleOrchestrationEntityProvider() {
        this.entities = Set.of(SIMPLE_ORCHESTRATION_ENTITY_ONE, SIMPLE_ORCHESTRATION_ENTITY_TWO);
    }

    @Override
    public Collection<SimpleOrchestrationEntity> getEntities() {
        return entities;
    }
}
