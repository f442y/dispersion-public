package com.github.f442y.dispersion.composer.playbook;

import com.github.f442y.dispersion.core.orchestration.OrchestrationEntityProvider;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

@Component
public class SimpleOrchestrationEntityProvider implements OrchestrationEntityProvider<SimpleOrchestrationEntity> {

    private final Collection<SimpleOrchestrationEntity> entities;
    private static final SimpleOrchestrationEntity SIMPLE_ORCHESTRATION_ENTITY = new SimpleOrchestrationEntity();

    public SimpleOrchestrationEntityProvider() {
        this.entities = Collections.nCopies(1, SIMPLE_ORCHESTRATION_ENTITY);
    }

    @Override
    public Collection<SimpleOrchestrationEntity> getEntities() {
        return entities;
    }
}
