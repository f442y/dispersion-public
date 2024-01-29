package com.github.f442y.dispersion.composer.playbook;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@SpringBootTest
@Slf4j
public class ConductorTest {
    @Autowired
    private Collection<Play<?,?>> plays;

    private ExecutorService executorService =
            Executors.newThreadPerTaskExecutor(Thread.ofVirtual().name("playActor-", 0).factory());

    @Test
    public void playsTest() {
        plays.forEach(System.out::println);
        // get plays without dependencies
        var nonSignalled = plays.stream().filter(play -> play.afterThesePlays.isEmpty()).collect(Collectors.toSet());
        log.info("non signalled plays: {}", nonSignalled);
        // create actor for each play
        // add signalling channel for each dependency
    }
}