package com.github.f442y.dispersion.orchestrator.model.orchestrationEntities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "SAMPLE_ORCHESTRATION_ENTITY")
public class SampleOrchestrationEntity extends OrchestrationEntity implements com.github.f442y.dispersion.orchestration.spi.entities.SampleOrchestrationEntity.EntityAPI {

    @Column(name = "INFO")
    private String info;

    public SampleOrchestrationEntity() {super();}

    public SampleOrchestrationEntity(String id, String info) {
        super(id);
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
