package com.github.f442y.dispersion.orchestrator.model.entity;

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

/**
 * Defines Entity Types for each PlayTask.
 * Used to compare with dependencies if entity types match
 */
@Entity
@Table(name = "PLAY_TASK_ENTITY_TYPE")
@Deprecated
public class PlayTaskEntityType {
    @Id
    @Column(name = "ID", nullable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLAY_TASK_ID", nullable = false)
    private PlayTask playTask;

    @Column(name = "ENTITY_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private EntitySPI entityType;

    public PlayTaskEntityType() {
    }

    public PlayTaskEntityType(String id, PlayTask playTask, EntitySPI entityType) {
        this.id = id;
        this.playTask = playTask;
        this.entityType = entityType;
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

    public EntitySPI getEntityType() {
        return entityType;
    }

    public void setEntityType(EntitySPI entityType) {
        this.entityType = entityType;
    }
}
