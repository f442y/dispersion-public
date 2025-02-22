package com.github.f442y.dispersion.orchestrator.model.entity;

import com.github.f442y.dispersion.orchestration.spi.entities.EntitySPI;
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

@Entity
@Table(name = "PLAY_TASK_ENTITY_DEPENDENCY")
public class PlayTaskEntityDependency {
    @Id
    @Column(name = "ID")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLAY_TASK_TO_RUN_ID")
    private PlayTask playTaskToRun;

    @Column(name = "RUN_ENTITY_TYPE")
    private EntitySPI runEntityType;

    @Column(name = "RUN_ENTITY_ZONE")
    @Enumerated(EnumType.STRING)
    private EntityZones runEntityZone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLAY_TASK_SOURCE_ID")
    private PlayTask playTaskSource;

    @Column(name = "SOURCE_ENTITY_TYPE")
    @Enumerated(EnumType.STRING)
    private EntitySPI sourceEntityType;

    @Column(name = "SOURCE_ENTITY_ZONE")
    @Enumerated(EnumType.STRING)
    private EntityZones sourceEntityZone;

    // runEntityType vs sourceEntityType validation via 'instanceof'
    // if not a match must define
    // Entity Signal converter

    public PlayTaskEntityDependency() {}

    public PlayTaskEntityDependency(String id, PlayTask playTaskToRun, EntitySPI runEntityType,
                                    EntityZones runEntityZone, PlayTask playTaskSource, EntitySPI sourceEntityType,
                                    EntityZones sourceEntityZone
    ) {
        this.id = id;
        this.playTaskToRun = playTaskToRun;
        this.runEntityType = runEntityType;
        this.runEntityZone = runEntityZone;
        this.playTaskSource = playTaskSource;
        this.sourceEntityType = sourceEntityType;
        this.sourceEntityZone = sourceEntityZone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PlayTask getPlayTaskToRun() {
        return playTaskToRun;
    }

    public void setPlayTaskToRun(PlayTask playTaskToRun) {
        this.playTaskToRun = playTaskToRun;
    }

    public PlayTask getPlayTaskSource() {
        return playTaskSource;
    }

    public void setPlayTaskSource(PlayTask playTaskSource) {
        this.playTaskSource = playTaskSource;
    }

    public EntitySPI getRunEntityType() {
        return runEntityType;
    }

    public void setRunEntityType(EntitySPI runEntityType) {
        this.runEntityType = runEntityType;
    }

    public EntitySPI getSourceEntityType() {
        return sourceEntityType;
    }

    public void setSourceEntityType(EntitySPI sourceEntityType) {
        this.sourceEntityType = sourceEntityType;
    }

    public EntityZones getRunEntityZone() {
        return runEntityZone;
    }

    public void setRunEntityZone(EntityZones runEntityZone) {
        this.runEntityZone = runEntityZone;
    }

    public EntityZones getSourceEntityZone() {
        return sourceEntityZone;
    }

    public void setSourceEntityZone(EntityZones sourceEntityZone) {
        this.sourceEntityZone = sourceEntityZone;
    }
}
