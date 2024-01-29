package com.github.f442y.dispersion.composer.playbook;

import com.softwaremill.jox.Channel;

public interface PlayActor {
    Channel<Integer> channel();

    void sendSignalToChannel(Integer signal) throws InterruptedException;

    void subscribe(PlayActor playActor);
}
