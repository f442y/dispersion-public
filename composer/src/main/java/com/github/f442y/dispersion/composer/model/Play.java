package com.github.f442y.dispersion.composer.model;

import com.github.f442y.dispersion.composer.orchestration.OrchestratorServerNode;
import com.github.f442y.dispersion.core.orchestration.OrchestrationEntity;
import com.github.f442y.dispersion.core.orchestration.OrchestrationEntityProvider;
import com.github.f442y.dispersion.core.orchestration.OrchestrationService;
import com.github.f442y.dispersion.core.orchestration.PlayAPI;
import jakarta.inject.Inject;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "PLAY")
@Inheritance
@DiscriminatorColumn(name = "unique_play_name")
@Data
public abstract class Play<ZONE extends Zone, ORCHESTRATION_ENTITY extends OrchestrationEntity,
        ORCHESTRATION_SERVICE extends OrchestrationService>
        implements PlayAPI<ORCHESTRATION_ENTITY, ORCHESTRATION_SERVICE>, Serializable {
    // schedule: zone...
    // dependency type: after all complete, entity signal, different entity
//    @OneToMany(mappedBy = "id")
    @Transient
    public final Set<Play<?, ? extends OrchestrationEntity, ? extends OrchestrationService>> afterThesePlays;
    @Transient
    public final OrchestrationEntityProvider<ORCHESTRATION_ENTITY> forTheseEntities;
    private final String name;
    //    when: scheduled, one-shot, trigger only, between only,
    public String atThisTime;
    // default: play within same zone
    //    Restart/Retry policy
    //    ... other data
    @Transient
    protected OrchestratorServerNode<ORCHESTRATION_SERVICE> orchestratorServerNode;
    //dynamic entity provider, with inputs such as zone
    @Id
    @GeneratedValue
    private Long id;

    public Play(OrchestrationEntityProvider<ORCHESTRATION_ENTITY> forTheseEntities,
                Set<Play<?, ? extends OrchestrationEntity, ? extends OrchestrationService>> afterThesePlays
    ) {
        this.name = this.getClass().getCanonicalName();
        this.afterThesePlays = afterThesePlays;
        this.forTheseEntities = forTheseEntities;

    }

    @Inject
    public final void setOrchestratorServerNode(OrchestratorServerNode<ORCHESTRATION_SERVICE> orchestratorServerNode) {
        this.orchestratorServerNode = orchestratorServerNode;
    }
}

// Configuration Scenarios
// Timing/Scheduling
// (Play should be for only one zone (e.g. UTC))
// - at start of day (9am) for UTC
// - at 12pm,


// when (scheduler)
// which (provider)
// what (service)


//        public Play(PlayBuilder<ORCHESTRATION_SERVICE> playBuilder) {
//        this.afterThesePlays = playBuilder.afterThesePlays.stream().toList();
//        this.forTheseEntities = playBuilder.entityProvider;
//    }

//    @Configuration
//    public static class PlayBuilder<ORCHESTRATION_SERVICE extends OrchestrationService> {
//        private OrchestrationEntityProvider<?> entityProvider;
//        private final ArrayList<Play<? extends OrchestrationService>> afterThesePlays = new ArrayList<>();
//
//        public PlayBuilder() {}
//
//        public PlayBuilder<ORCHESTRATION_SERVICE> entityProvider(OrchestrationEntityProvider<?> entityProvider) {
//            this.entityProvider = entityProvider;
//            return this;
//        }
//
//        public PlayBuilder<ORCHESTRATION_SERVICE> afterPlay(Play<? extends OrchestrationService> play) {
//            afterThesePlays.add(play);
//            return this;
//        }
//
//        @Bean
//        public Play<ORCHESTRATION_SERVICE> build() {
//            return new Play<>(this);
//        }
//    }