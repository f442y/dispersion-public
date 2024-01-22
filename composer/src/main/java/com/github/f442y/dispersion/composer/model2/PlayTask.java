package com.github.f442y.dispersion.composer.model2;

import com.github.f442y.dispersion.composer.model.Play;
import com.github.f442y.dispersion.composer.orchestration.OrchestratorServerNode;
import com.github.f442y.dispersion.core.orchestration.OrchestrationEntity;
import com.github.f442y.dispersion.core.orchestration.OrchestrationEntityProvider;
import com.github.f442y.dispersion.core.orchestration.OrchestrationService;
import com.github.f442y.dispersion.core.orchestration.PlayAPI;
import jakarta.inject.Inject;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "PLAY_TASK")
public class PlayTask<ORCHESTRATION_ENTITY extends OrchestrationEntity,
        ORCHESTRATION_SERVICE extends OrchestrationService>
        implements PlayAPI<ORCHESTRATION_ENTITY, ORCHESTRATION_SERVICE> {
    @Id
    @Column(name = "PLAY_TASK_ID")
    private String id;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "playTaskToRun")
    private Set<PlayTaskDependency> playTasksIDependOn;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "playTaskSource")
    private Set<PlayTaskDependency> playTasksDependingOnMe;


    public transient Set<com.github.f442y.dispersion.composer.model.Play<?, ? extends OrchestrationEntity, ?
            extends OrchestrationService>>
            afterThesePlays;
    public transient OrchestrationEntityProvider<ORCHESTRATION_ENTITY> entityProvider;
    private String name;
    public String atThisTime;
    protected transient OrchestratorServerNode<ORCHESTRATION_SERVICE> orchestratorServerNode;

    public PlayTask(OrchestrationEntityProvider<ORCHESTRATION_ENTITY> entityProvider,
                    Set<com.github.f442y.dispersion.composer.model.Play<?, ? extends OrchestrationEntity, ?
                            extends OrchestrationService>> afterThesePlays,
                    String playName
    ) {
        this.id = playName;
        this.name = playName;
        this.afterThesePlays = afterThesePlays;
        this.entityProvider = entityProvider;
    }

    public PlayTask() {}

    @Inject
    public final void setOrchestratorServerNode(OrchestratorServerNode<ORCHESTRATION_SERVICE> orchestratorServerNode) {
        this.orchestratorServerNode = orchestratorServerNode;
    }

    public final Set<Play<?, ? extends OrchestrationEntity, ? extends OrchestrationService>> getAfterThesePlays() {
        return afterThesePlays;
    }

    public final OrchestrationEntityProvider<ORCHESTRATION_ENTITY> getEntityProvider() {
        return entityProvider;
    }

    public final String getName() {
        return name;
    }

    public final String getAtThisTime() {
        return atThisTime;
    }

    public final void setAtThisTime(String atThisTime) {
        this.atThisTime = atThisTime;
    }

    public final OrchestratorServerNode<ORCHESTRATION_SERVICE> getOrchestratorServerNode() {
        return orchestratorServerNode;
    }

    public final String getId() {
        return id;
    }

    public final void setId(String id) {
        this.id = id;
    }

    public final Set<PlayTaskDependency> getPlayTasksIDependOn() {
        return playTasksIDependOn;
    }

    public final void setPlayTasksIDependOn(Set<PlayTaskDependency> playTasksIDependOn
    ) {
        this.playTasksIDependOn = playTasksIDependOn;
    }

    public final Set<PlayTaskDependency> getPlayTasksDependingOnMe() {
        return playTasksDependingOnMe;
    }

    public final void setPlayTasksDependingOnMe(Set<PlayTaskDependency> playTasksDependingOnMe
    ) {
        this.playTasksDependingOnMe = playTasksDependingOnMe;
    }
}
