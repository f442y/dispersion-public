package com.github.f442y.dispersion.composer.playbook.playActor;

import com.github.f442y.dispersion.composer.model.Play;
import com.github.f442y.dispersion.core.orchestration.OrchestrationEntity;

public interface PlayActorAPI {
    Play<?, ?, ?> play();

    void sendSignalToChannel(OrchestrationEntity signal) throws InterruptedException;

    void subscribe(PlayActorAPI playActor);
}
