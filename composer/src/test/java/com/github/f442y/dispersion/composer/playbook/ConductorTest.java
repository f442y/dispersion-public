package com.github.f442y.dispersion.composer.playbook;

import com.github.f442y.dispersion.composer.model.Play;
import com.github.f442y.dispersion.composer.model.PlayRepository;
import com.github.f442y.dispersion.composer.playbook.playActor.PlayActorAPI;
import com.github.f442y.dispersion.composer.playbook.playActor.PlayActorRunnable;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@SpringBootTest
@Slf4j
public class ConductorTest {
    private final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
    private final Map<String, PlayActorAPI> activePlays = new HashMap<>();
    @Autowired
    private PlayRepository playRepository;
    @Autowired
    private Collection<Play<?, ?, ?>> plays;

    @Test
    public void playsTest() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        plays.forEach(System.out::println);
        // get plays without dependencies
        var nonSignalled = plays.stream().filter(play -> play.afterThesePlays.isEmpty()).collect(Collectors.toSet());
        log.info("non signalled plays: {}", nonSignalled);
        // create actor for each play
        plays.forEach(play -> {
            String keyName = play.getClass().getSimpleName();
            PlayActorRunnable playActorRunnable = new PlayActorRunnable(play);
            executorService.submit(playActorRunnable);
            activePlays.put(keyName, playActorRunnable);
        });
        log.info(activePlays.toString());
        // add signalling channel for each dependency
        activePlays.forEach((s, playActor) -> {
            playActor.play().afterThesePlays.forEach(previousPlay -> {
                // find in active plays
                var actor = activePlays.get(previousPlay.getClass().getSimpleName());
                actor.subscribe(playActor);
            });
        });
        TimeUnit.SECONDS.sleep(1);
        activePlays.get("PlayOne").sendSignalToChannel(new SimpleOrchestrationEntity());
        TimeUnit.SECONDS.sleep(1);
//        latch.await();
    }

    @Test
    public void repoTest() {
        playRepository.findAll().iterator().forEachRemaining(System.out::println);
    }
}