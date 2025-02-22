package com.github.f442y.dispersion.orchestrator.model.run;

import com.github.f442y.dispersion.core.orchestration.OrchestrationEntityAPI;
import com.github.f442y.dispersion.orchestration.spi.entities.EntitySPI;
import com.github.f442y.dispersion.orchestrator.model.PlayTask;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "PLAY_TASK_RUN_ENTITY_EVENT")
public class PlayTaskRunEntityEvent {
    @Id
    @Column(name = "ID", nullable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLAY_TASK_ID", nullable = false)
    private PlayTask playTask;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ENTITY_RUN", nullable = false)
    private PlayTaskRun playTaskRun;

    @Column(name = "ENTITY_ID", nullable = false)
    private String orchestrationEntityId;

    @Column(name = "ENTITY_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private EntitySPI entityType;

    @Column(name = "EVENT_STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private EventStatus eventStatus;

    public enum EventStatus {
        INCOMPLETE, COMPLETE
    }

    public PlayTaskRunEntityEvent() {}

    public PlayTaskRunEntityEvent(String id, PlayTask playTask, PlayTaskRun playTaskRun,
                                  OrchestrationEntityAPI orchestrationEntity, EventStatus eventStatus
    ) {
        this.id = id;
        this.playTask = playTask;
        this.playTaskRun = playTaskRun;
        this.orchestrationEntityId = orchestrationEntity.getOrchestrationId();
        this.entityType = EntitySPI.getFromCode(orchestrationEntity.getOrchestrationEntityTypeCode());
        this.eventStatus = eventStatus;
    }

    public PlayTask getPlayTask() {
        return playTask;
    }

    public void setPlayTask(PlayTask playTask) {
        this.playTask = playTask;
    }

    public PlayTaskRun getPlayTaskRun() {
        return playTaskRun;
    }

    public void setPlayTaskRun(PlayTaskRun playTaskRun) {
        this.playTaskRun = playTaskRun;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public EventStatus getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(EventStatus eventStatus) {
        this.eventStatus = eventStatus;
    }
}
