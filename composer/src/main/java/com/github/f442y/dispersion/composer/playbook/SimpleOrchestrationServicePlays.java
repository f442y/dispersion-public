package com.github.f442y.dispersion.composer.playbook;

import com.github.f442y.dispersion.core.orchestration.OrchestrationEntityProvider;
import com.github.f442y.dispersion.service.interfaces.SimpleOrchestrationService;
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
    public static class PlayOne extends Play<SimpleOrchestrationEntity, SimpleOrchestrationService> {
        @Autowired
        public PlayOne(OrchestrationEntityProvider<SimpleOrchestrationEntity> provider) {
            super(provider, Collections.emptySet());
        }
    }

    @Component
    public static class PlayTwo extends Play<SimpleOrchestrationEntity, SimpleOrchestrationService> {
        @Autowired
        public PlayTwo(OrchestrationEntityProvider<SimpleOrchestrationEntity> provider, PlayOne playOne) {
            super(provider, Set.of(playOne));
        }
    }

    @Component
    public static class PlayThree extends Play<SimpleOrchestrationEntity, SimpleOrchestrationService> {
        @Autowired
        public PlayThree(OrchestrationEntityProvider<SimpleOrchestrationEntity> provider, PlayTwo playTwo) {
            super(provider, Set.of(playTwo));
        }
    }

    @Component
    public static class PlayFour extends Play<SimpleOrchestrationEntity, SimpleOrchestrationService> {
        @Autowired
        public PlayFour(OrchestrationEntityProvider<SimpleOrchestrationEntity> provider, PlayTwo playTwo) {
            super(provider, Set.of(playTwo));
        }
    }

    @Component
    public static class PlayFive extends Play<SimpleOrchestrationEntity, SimpleOrchestrationService> {
        @Autowired
        public PlayFive(OrchestrationEntityProvider<SimpleOrchestrationEntity> provider, PlayThree playThree,
                        PlayFour playFour
        ) {
            super(provider, Set.of(playThree, playFour));
        }
    }
}
