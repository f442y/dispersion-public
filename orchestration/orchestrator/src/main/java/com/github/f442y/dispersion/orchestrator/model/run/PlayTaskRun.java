package com.github.f442y.dispersion.orchestrator.model.run;

import com.github.f442y.dispersion.orchestrator.model.PlayTask;
import com.github.f442y.dispersion.orchestrator.zones.EntityZones;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "PLAY_TASK_RUN")
public class PlayTaskRun {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLAY_TASK_ID", nullable = false)
    private PlayTask playTask;

    @Column(name = "RUN_ZONE", nullable = false)
    @Enumerated(EnumType.STRING)
    private EntityZones entityZone;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "playTaskRun")
    private Set<PlayTaskRunExpectedEntity> entityExpectations;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "playTaskRun")
    private Set<PlayTaskRunEntityEvent> entityEvents;

    public PlayTaskRun() {}

    public PlayTaskRun(PlayTask playTask, EntityZones entityZone) {
        this.playTask = playTask;
        this.entityZone = entityZone;
    }

    public PlayTask getPlayTask() {
        return playTask;
    }

    public void setPlayTask(PlayTask playTask) {
        this.playTask = playTask;
    }

    public EntityZones getEntityZone() {
        return entityZone;
    }

    public void setEntityZone(EntityZones entityZone) {
        this.entityZone = entityZone;
    }

    public Set<PlayTaskRunExpectedEntity> getEntityExpectations() {
        return entityExpectations;
    }

    public void setEntityExpectations(Set<PlayTaskRunExpectedEntity> entityExpectations
    ) {
        this.entityExpectations = entityExpectations;
    }

    public Set<PlayTaskRunEntityEvent> getEntityEvents() {
        return entityEvents;
    }

    public void setEntityEvents(Set<PlayTaskRunEntityEvent> entityEvents
    ) {
        this.entityEvents = entityEvents;
    }

    public UUID getId() {
        return id;
    }
}
