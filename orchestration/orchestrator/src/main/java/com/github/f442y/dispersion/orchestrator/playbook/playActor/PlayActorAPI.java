package com.github.f442y.dispersion.orchestrator.playbook.playActor;

import com.github.f442y.dispersion.core.orchestration.OrchestrationEntityAPI;
import com.github.f442y.dispersion.orchestrator.model.PlayTask;

public interface PlayActorAPI {
    PlayTask play();

    void sendSignalToChannel(OrchestrationEntityAPI signal) throws InterruptedException;

    void subscribe(PlayActorAPI playActor);
}
