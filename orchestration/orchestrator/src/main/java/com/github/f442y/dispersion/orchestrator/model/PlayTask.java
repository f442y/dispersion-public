package com.github.f442y.dispersion.orchestrator.model;

import com.github.f442y.dispersion.core.orchestration.PlayTaskAPI;
import com.github.f442y.dispersion.orchestrator.model.entity.PlayTaskEntityDependency;
import com.github.f442y.dispersion.orchestrator.model.entity.PlayTaskEntityType;
import com.github.f442y.dispersion.orchestrator.model.entity.PlayTaskEntityZone;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "PLAY_TASK")
public class PlayTask implements PlayTaskAPI {
    @Id
    @Column(name = "PLAY_TASK_ID", nullable = false)
    private String id;

    @Column(name = "PLAY_TASK_NAME", nullable = false)
    private String name;

//    @Column(name = "PROVIDER", nullable = false)
//    @Enumerated(EnumType.STRING)
//    private Providers provider;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "playTask")
    private Set<PlayTaskEntityType> entityComposition;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "playTask")
    private Set<PlayTaskEntityZone> zoneComposition;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "playTaskToRun")
    private Set<PlayTaskEntityDependency> playTasksIDependOn;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "playTaskSource")
    private Set<PlayTaskEntityDependency> playTasksDependingOnMe;

    public void buildExpectations() {

    }

    public PlayTask(String playName) {
        this.id = playName;
        this.name = playName;
    }

    public PlayTask() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<PlayTaskEntityType> getEntityComposition() {
        return entityComposition == null ? null : entityComposition;
    }

    public void setEntityComposition(Set<PlayTaskEntityType> entityComposition) {
        this.entityComposition = entityComposition;
    }

    public Set<PlayTaskEntityZone> getZoneComposition() {
        return zoneComposition;
    }

    public void setZoneComposition(Set<PlayTaskEntityZone> zoneComposition
    ) {
        this.zoneComposition = zoneComposition;
    }

    public Set<PlayTaskEntityDependency> getPlayTasksIDependOn() {
        return playTasksIDependOn;
    }

    public Set<PlayTaskEntityDependency> getPlayTasksDependingOnMe() {
        return playTasksDependingOnMe;
    }
}
