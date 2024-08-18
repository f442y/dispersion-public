package com.github.f442y.dispersion.composer.playbook;

import com.github.f442y.dispersion.composer.playbook.play.Play;
import com.github.f442y.dispersion.core.orchestration.OrchestrationEntity;
import com.softwaremill.jox.Channel;

public interface PlayActor {
    Play<?,?> play();

    void sendSignalToChannel(OrchestrationEntity signal) throws InterruptedException;

    void subscribe(PlayActor playActor);
}
