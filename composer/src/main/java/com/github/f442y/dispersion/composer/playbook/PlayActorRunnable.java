package com.github.f442y.dispersion.composer.playbook;

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
    private final Channel<Integer> channel = Channel.newUnlimitedChannel();
    private final Set<PlayActor> playActorsToSignal = new HashSet<>();

    public PlayActorRunnable(Play<?, ?> play) {this.play = play;}

    @Override
    public void run() {
        // use play entity provider to build entity filter (run for these entities)
        // get dependencies from play
        Set<Play<?, ?>> playDependencies = play.afterThesePlays;
        // check dependency status (may already be complete)
        // search for active dependency play actors, subscribe to relevant signals
        while (active.get()) {
            try {
                var msg = channel.receive();
                // check if entity is in provider
                // (if messaging play provider and this play provider are equal, then entity should always match)
                log.info("play actor message: {}", msg);

                // on success signal plays dependent on this one
                this.sendSignals();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void sendSignals() {
        playActorsToSignal.forEach(playActor -> {
            try {
                playActor.sendSignalToChannel(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public Channel<Integer> channel() {
        return this.channel;
    }

    @Override
    public void sendSignalToChannel(Integer signal) {
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
