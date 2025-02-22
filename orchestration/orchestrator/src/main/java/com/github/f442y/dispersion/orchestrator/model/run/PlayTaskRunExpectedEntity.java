package com.github.f442y.dispersion.orchestrator.model.run;

import com.github.f442y.dispersion.core.orchestration.OrchestrationEntityAPI;
import com.github.f442y.dispersion.orchestration.spi.entities.EntitySPI;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "PLAY_TASK_RUN_ENTITY_EXPECTATION")
public class PlayTaskRunExpectedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ENTITY_RUN", nullable = false)
    private PlayTaskRun playTaskRun;

    @Column(name = "ENTITY_ID", nullable = false)
    private String orchestrationEntityId;

    @Column(name = "ENTITY_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private EntitySPI entityType;

    @Column(name = "EXPECTATION_STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private ExpectationStatus expectationStatus;

    public enum ExpectationStatus {
        DEPENDENCIES_PENDING, READY
    }

    public PlayTaskRunExpectedEntity() {}

    public PlayTaskRunExpectedEntity(PlayTaskRun playTaskRun, OrchestrationEntityAPI orchestrationEntity,
                                     ExpectationStatus expectationStatus
    ) {
        this.playTaskRun = playTaskRun;
        this.orchestrationEntityId = orchestrationEntity.getOrchestrationId();
        this.entityType = EntitySPI.getFromCode(orchestrationEntity.getOrchestrationEntityTypeCode());
        this.expectationStatus = expectationStatus;
    }

    public enum EventStatus {
        INCOMPLETE, COMPLETE
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public PlayTaskRun getPlayTaskRun() {
        return playTaskRun;
    }

    public void setPlayTaskRun(PlayTaskRun playTaskRun) {
        this.playTaskRun = playTaskRun;
    }

    public String getOrchestrationEntityId() {
        return orchestrationEntityId;
    }

    public void setOrchestrationEntityId(String orchestrationEntityId) {
        this.orchestrationEntityId = orchestrationEntityId;
    }

    public EntitySPI getEntityType() {
        return entityType;
    }

    public void setEntityType(EntitySPI entityType) {
        this.entityType = entityType;
    }

    public ExpectationStatus getExpectationStatus() {
        return expectationStatus;
    }

    public void setExpectationStatus(ExpectationStatus expectationStatus
    ) {
        this.expectationStatus = expectationStatus;
    }
}
