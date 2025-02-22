package com.github.f442y.dispersion.orchestrator.model.entity;

import com.github.f442y.dispersion.orchestrator.model.PlayTask;
import com.github.f442y.dispersion.orchestrator.zones.EntityZones;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Defines Entity Types for each PlayTask.
 * Used to compare with dependencies if entity types match
 */
@Entity
@Table(name = "PLAY_TASK_ENTITY_ZONE")
public class PlayTaskEntityZone {
    @Id
    @Column(name = "ID", nullable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLAY_TASK_ID", nullable = false)
    private PlayTask playTask;

    @Column(name = "ENTITY_ZONE")
    @Enumerated(EnumType.STRING)
    private EntityZones entityZone;

    public PlayTaskEntityZone() {}

    public PlayTaskEntityZone(String id, PlayTask playTask, EntityZones entityZone) {
        this.id = id;
        this.playTask = playTask;
        this.entityZone = entityZone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
