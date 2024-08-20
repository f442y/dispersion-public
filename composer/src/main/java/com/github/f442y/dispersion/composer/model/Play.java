package com.github.f442y.dispersion.composer.model;

import com.github.f442y.dispersion.composer.orchestration.OrchestratorServerNode;
import com.github.f442y.dispersion.core.orchestration.OrchestrationEntity;
import com.github.f442y.dispersion.core.orchestration.OrchestrationEntityProvider;
import com.github.f442y.dispersion.core.orchestration.OrchestrationService;
import com.github.f442y.dispersion.core.orchestration.PlayAPI;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "PLAY")
@Inheritance
@DiscriminatorColumn(name = "unique_play_name")
@Data
public class Play<ORCHESTRATION_ENTITY extends OrchestrationEntity, ORCHESTRATION_SERVICE extends OrchestrationService> implements PlayAPI<ORCHESTRATION_ENTITY, ORCHESTRATION_SERVICE>, Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private final String name;
    //    when: scheduled, one-shot, trigger only, between only,
    public String atThisTime;
    //    after what
    // dependency type: after all complete, entity signal, different entity
//    @OneToMany(mappedBy = "id")
    @Transient
    public final Set<Play<? extends OrchestrationEntity, ? extends OrchestrationService>> afterThesePlays;
    @Transient
    public final OrchestrationEntityProvider<ORCHESTRATION_ENTITY> forTheseEntities;
    //    Restart/Retry policy
    //    ... other data
    @Transient
    protected OrchestratorServerNode<ORCHESTRATION_SERVICE> orchestratorServerNode;

    public Play(String name, OrchestrationEntityProvider<ORCHESTRATION_ENTITY> forTheseEntities, Set<Play<? extends OrchestrationEntity, ? extends OrchestrationService>> afterThesePlays) {
        this.name = name;
        this.afterThesePlays = afterThesePlays;
        this.forTheseEntities = forTheseEntities;

    }

    @Autowired
    public final void setOrchestratorServerNode(OrchestratorServerNode<ORCHESTRATION_SERVICE> orchestratorServerNode) {
        this.orchestratorServerNode = orchestratorServerNode;
    }

    @Transient
    private PlayRepository playRepository;

    @Autowired
    public final void setPlayRepository(PlayRepository playRepository) {
        this.playRepository = playRepository;
    }

    public final <PLAY extends Play<?, ?>> void addToRepository(PLAY play) {
        this.playRepository.save(play);
    }

    public void attemptToRunPlay(String regionOrTimeZone) {
    }
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