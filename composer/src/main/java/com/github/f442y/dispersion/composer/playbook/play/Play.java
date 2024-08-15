package com.github.f442y.dispersion.composer.playbook.play;

import com.github.f442y.dispersion.composer.orchestration.OrchestratorServerNode;
import com.github.f442y.dispersion.core.orchestration.OrchestrationEntity;
import com.github.f442y.dispersion.core.orchestration.OrchestrationEntityProvider;
import com.github.f442y.dispersion.core.orchestration.OrchestrationService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

@Getter
public abstract class Play<ORCHESTRATION_ENTITY extends OrchestrationEntity,
        ORCHESTRATION_SERVICE extends OrchestrationService> {
    //    when: scheduled, one-shot, trigger only, between only,
    public String atThisTime;
    //    after what
    // dependency type: after all complete, entity signal, different entity
    public final Set<Play<? extends OrchestrationEntity, ? extends OrchestrationService>> afterThesePlays;
    public final OrchestrationEntityProvider<ORCHESTRATION_ENTITY> forTheseEntities;
    //    Restart/Retry policy
    //    ... other data
    protected OrchestratorServerNode<ORCHESTRATION_SERVICE> orchestratorServerNode;

    public Play(OrchestrationEntityProvider<ORCHESTRATION_ENTITY> forTheseEntities,
                Set<Play<? extends OrchestrationEntity, ? extends OrchestrationService>> afterThesePlays
    ) {
        this.afterThesePlays = afterThesePlays;
        this.forTheseEntities = forTheseEntities;
    }

    @Autowired
    public final void setOrchestratorServerNode(OrchestratorServerNode<ORCHESTRATION_SERVICE> orchestratorServerNode) {
        this.orchestratorServerNode = orchestratorServerNode;
    }

    public void attemptToRunPlay(String regionOrTimeZone) {}
}


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