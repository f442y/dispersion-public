package com.github.f442y.dispersion.composer.model;

import com.github.f442y.dispersion.composer.playbook.SimpleOrchestrationEntity;
import com.github.f442y.dispersion.composer.playbook.SimpleZone;
import com.github.f442y.dispersion.core.orchestration.OrchestrationEntityProvider;
import com.github.f442y.dispersion.service.interfaces.SimpleOrchestrationService;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.Collections;
import java.util.Set;

/**
 * .                                              +-------------+
 * .                                       +----->+  PlayThree  +------+
 * . +-------------+     +-------------+   |      +-------------+      |     +-------------+
 * . |   PlayOne   +---->|   PlayTwo   +---+                           +---->|  PlayFive   |
 * . +-------------+     +-------------+   |      +-------------+      |     +-------------+
 * .                                       +----->+  PlayFour   +------+
 * .                                              +-------------+
 */
public class SimpleOrchestrationServicePlays {

    private SimpleOrchestrationServicePlays() {/* no-op */}

    @Named
    @Singleton
    @Entity
    @DiscriminatorValue(value = "play_one")
    public static class PlayOne extends Play<SimpleZone, SimpleOrchestrationEntity, SimpleOrchestrationService> {
        public PlayOne() {
            super(null, null);
        }

        @Inject
        public PlayOne(OrchestrationEntityProvider<SimpleOrchestrationEntity> provider) {
            super(provider, Collections.emptySet());
        }
    }

    @Named
    @Singleton
    @Entity
    @DiscriminatorValue(value = "play_two")
    public static class PlayTwo extends Play<SimpleZone, SimpleOrchestrationEntity, SimpleOrchestrationService> {
        public PlayTwo() {
            super(null, null);
        }

        @Inject
        public PlayTwo(OrchestrationEntityProvider<SimpleOrchestrationEntity> provider, PlayOne playOne) {
            super(provider, Set.of(playOne));
        }
    }

    @Named
    @Singleton
    @Entity
    @DiscriminatorValue(value = "play_three")
    public static class PlayThree extends Play<SimpleZone, SimpleOrchestrationEntity, SimpleOrchestrationService> {
        public PlayThree() {
            super(null, null);
        }

        @Inject
        public PlayThree(OrchestrationEntityProvider<SimpleOrchestrationEntity> provider, PlayTwo playTwo) {
            super(provider, Set.of(playTwo));
        }
    }

    @Named
    @Singleton
    @Entity
    @DiscriminatorValue(value = "play_four")
    public static class PlayFour extends Play<SimpleZone, SimpleOrchestrationEntity, SimpleOrchestrationService> {
        public PlayFour() {
            super(null, null);
        }

        @Inject
        public PlayFour(OrchestrationEntityProvider<SimpleOrchestrationEntity> provider, PlayTwo playTwo) {
            super(provider, Set.of(playTwo));
        }
    }

    @Named
    @Singleton
    @Entity
    @DiscriminatorValue(value = "play_five")
    public static class PlayFive extends Play<SimpleZone, SimpleOrchestrationEntity, SimpleOrchestrationService> {
        public PlayFive() {
            super(null, null);
        }

        @Inject
        public PlayFive(OrchestrationEntityProvider<SimpleOrchestrationEntity> provider, PlayThree playThree,
                        PlayFour playFour
        ) {
            super(provider, Set.of(playThree, playFour));
        }
    }
}
