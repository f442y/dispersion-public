package com.github.f442y.dispersion.composer.playbook;

import com.github.f442y.dispersion.composer.model.Play;
import com.github.f442y.dispersion.core.orchestration.OrchestrationEntity;
import com.softwaremill.jox.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * signal waiting actor
 * <p>
 * dispatches play for orchestration entity/entities to orchestrator server node on dependencies fulfilled
 */
@Slf4j
public class PlayActorRunnable implements PlayActor, Runnable {
    private final Play<?, ?> play;
    private final AtomicBoolean active = new AtomicBoolean(true);
    private final Channel<OrchestrationEntity> channel = Channel.newUnlimitedChannel();
    private final Set<PlayActor> playActorsToSignal = new HashSet<>();

    public PlayActorRunnable(Play<?, ?> play) {
        this.play = play;
    }

    @Override
    public void run() {
        Thread.currentThread().setName("playActor-vt-" + this.play.getClass().getSimpleName());
        // use play entity provider to build entity filter (run for these entities)
        // get dependencies from play
        Set<Play<?, ?>> playDependencies = play.afterThesePlays;
        // check dependency status (may already be complete)
        // search for active dependency play actors, subscribe to relevant signals
        log.info("waiting ...");
        playActorLoop:
        while (active.get()) {
            try {
                OrchestrationEntity orchestrationEntity = channel.receive();
                log.info("play actor message received: {}", orchestrationEntity);
                // check if entity is in provider
                // (if messaging play provider and this play provider are equal, then entity should always match)
                // dependency conditions met?

                // on success signal plays dependent on this one
//                TimeUnit.SECONDS.sleep(1);
                this.sendSignals(orchestrationEntity);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void sendSignals(OrchestrationEntity orchestrationEntity) {
        playActorsToSignal.forEach(playActor -> {
            log.info("sending signal to ({})", playActor.play().getClass().getSimpleName());
            try {
                playActor.sendSignalToChannel(orchestrationEntity);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public Play<?, ?> play() {
        return this.play;
    }

    @Override
    public void sendSignalToChannel(OrchestrationEntity signal) {
        try {
            this.channel.send(signal);
        } catch (InterruptedException e) {
            // channel closed
            throw new RuntimeException(e);
        }
    }

    @Override
    public void subscribe(PlayActor playActor) {
        this.playActorsToSignal.add(playActor);
    }
}
