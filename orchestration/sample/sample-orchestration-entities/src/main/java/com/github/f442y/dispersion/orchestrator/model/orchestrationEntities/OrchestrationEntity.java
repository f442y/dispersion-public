package com.github.f442y.dispersion.orchestrator.model.orchestrationEntities;

import com.github.f442y.dispersion.core.orchestration.OrchestrationEntityAPI;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class OrchestrationEntity implements OrchestrationEntityAPI {

    @Id
    @Column(name = "ID", nullable = false)
    private String id;

    public OrchestrationEntity() {}

    public OrchestrationEntity(String id) {
        this.id = id;
    }

    @Override
    public String getOrchestrationId() {
        return id;
    }

    @Override
    public void setOrchestrationId(String id) {
        this.id = id;
    }
}
