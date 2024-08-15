package com.github.f442y.dispersion.composer.playbook;

import com.github.f442y.dispersion.composer.playbook.play.Play;
import com.softwaremill.jox.Channel;

public interface PlayActor {
    Play<?,?> play();
    Channel<Integer> channel();

    void sendSignalToChannel(Integer signal) throws InterruptedException;

    void subscribe(PlayActor playActor);
}
