package com.github.f442y.dispersion.orchestrator.playbook.playActor;

import com.github.f442y.dispersion.core.orchestration.OrchestrationEntityAPI;
import com.github.f442y.dispersion.orchestrator.model.PlayTask;
import com.softwaremill.jox.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * signal waiting actor
 * <p>
 * dispatches play for orchestration entity/entities to orchestrator server node on dependencies fulfilled
 */
public class PlayActorRunnable implements PlayActorAPI, Runnable {
    private static final Logger log = LoggerFactory.getLogger(PlayActorRunnable.class);
    private final PlayTask play;
    private final AtomicBoolean active = new AtomicBoolean(true);
    private final Channel<OrchestrationEntityAPI> channel = Channel.newUnlimitedChannel();
    private final Set<PlayActorAPI> playActorsToSignal = new HashSet<>();

    public PlayActorRunnable(PlayTask play) {
        this.play = play;
    }

    @Override
    public void run() {
        Thread.currentThread().setName("playActor-vt-" + this.play.getClass().getSimpleName());
        // use play entity provider to build entity filter (run for these entities)
        // get dependencies from play
        // check dependency status (may already be complete)
        // search for active dependency play actors, subscribe to relevant signals
        log.info("waiting ...");
        playActorLoop:
        while (active.get()) {
            try {
                OrchestrationEntityAPI orchestrationEntityAPI = channel.receive();
                log.info("play actor message received: {}", orchestrationEntityAPI);
                // check if entity is in provider
                // (if messaging play provider and this play provider are equal, then entity should always match)
                // dependency conditions met?

                // on success signal plays dependent on this one
//                TimeUnit.SECONDS.sleep(1);
                this.sendSignals(orchestrationEntityAPI);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void sendSignals(OrchestrationEntityAPI orchestrationEntityAPI) {
        playActorsToSignal.forEach(playActor -> {
            log.info("sending signal to ({})", playActor.play());
            try {
                playActor.sendSignalToChannel(orchestrationEntityAPI);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public PlayTask play() {
        return this.play;
    }

    @Override
    public void sendSignalToChannel(OrchestrationEntityAPI signal) {
        try {
            this.channel.send(signal);
        } catch (InterruptedException e) {
            // channel closed
            throw new RuntimeException(e);
        }
    }

    @Override
    public void subscribe(PlayActorAPI playActor) {
        this.playActorsToSignal.add(playActor);
    }
}
