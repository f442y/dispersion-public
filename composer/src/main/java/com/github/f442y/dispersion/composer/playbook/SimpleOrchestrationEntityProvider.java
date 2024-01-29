package com.github.f442y.dispersion.composer.playbook;

import com.github.f442y.dispersion.core.orchestration.OrchestrationEntityProvider;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

@Component
public class SimpleOrchestrationEntityProvider implements OrchestrationEntityProvider<SimpleOrchestrationEntity> {

    private final Collection<SimpleOrchestrationEntity> entities;

    public SimpleOrchestrationEntityProvider() {
        this.entities = Collections.nCopies(100, new SimpleOrchestrationEntity());
    }

    @Override
    public Collection<SimpleOrchestrationEntity> getEntities() {
        return entities;
    }
}
