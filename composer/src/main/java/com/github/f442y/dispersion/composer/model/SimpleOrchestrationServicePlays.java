package com.github.f442y.dispersion.composer.model;

import com.github.f442y.dispersion.composer.playbook.SimpleOrchestrationEntity;
import com.github.f442y.dispersion.core.orchestration.OrchestrationEntityProvider;
import com.github.f442y.dispersion.service.interfaces.SimpleOrchestrationService;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    @Component
    @Entity
    @DiscriminatorValue(value = "play_one")
    public static class PlayOne extends Play<SimpleOrchestrationEntity, SimpleOrchestrationService> {
        public PlayOne() {
            super(null, null, null);
        }

        @Autowired
        public PlayOne(OrchestrationEntityProvider<SimpleOrchestrationEntity> provider) {
            super("play_one", provider, Collections.emptySet());
        }
    }

    @Component
    @Entity
    @DiscriminatorValue(value = "play_two")
    public static class PlayTwo extends Play<SimpleOrchestrationEntity, SimpleOrchestrationService> {
        public PlayTwo() {
            super(null, null, null);
        }

        @Autowired
        public PlayTwo(OrchestrationEntityProvider<SimpleOrchestrationEntity> provider, PlayOne playOne) {
            super("play_two", provider, Set.of(playOne));
        }
    }

    @Component
    @Entity
    @DiscriminatorValue(value = "play_three")
    public static class PlayThree extends Play<SimpleOrchestrationEntity, SimpleOrchestrationService> {
        public PlayThree() {
            super(null, null, null);
        }

        @Autowired
        public PlayThree(OrchestrationEntityProvider<SimpleOrchestrationEntity> provider, PlayTwo playTwo) {
            super("play_three", provider, Set.of(playTwo));
        }
    }

    @Component
    @Entity
    @DiscriminatorValue(value = "play_four")
    public static class PlayFour extends Play<SimpleOrchestrationEntity, SimpleOrchestrationService> {
        public PlayFour() {
            super(null, null, null);
        }

        @Autowired
        public PlayFour(OrchestrationEntityProvider<SimpleOrchestrationEntity> provider, PlayTwo playTwo) {
            super("play_four", provider, Set.of(playTwo));
        }
    }

    @Component
    @Entity
    @DiscriminatorValue(value = "play_five")
    public static class PlayFive extends Play<SimpleOrchestrationEntity, SimpleOrchestrationService> {
        public PlayFive() {
            super(null, null, null);
        }

        @Autowired
        public PlayFive(OrchestrationEntityProvider<SimpleOrchestrationEntity> provider, PlayThree playThree, PlayFour playFour) {
            super("play_five", provider, Set.of(playThree, playFour));
        }
    }
}
