package com.github.f442y.dispersion.composer.playbook;

import com.github.f442y.dispersion.composer.model.Expectation;
import com.github.f442y.dispersion.composer.model.ExpectationRepository;
import com.github.f442y.dispersion.composer.model.ExpectationUpdate;
import com.github.f442y.dispersion.composer.model.ExpectationUpdateRepository;
import com.github.f442y.dispersion.composer.model.Play;
import com.github.f442y.dispersion.composer.model.PlayRepository;
import com.github.f442y.dispersion.composer.model.SimpleOrchestrationServicePlays;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.event.EventListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Named
@Singleton
@Slf4j
public class Conductor {

    private final Playbook playbook;
    private final PlayRepository playRepository;
    private final ExpectationRepository expectationRepository;
    private final ExpectationUpdateRepository expectationUpdateRepository;
    private final SimpleOrchestrationServicePlays.PlayOne playOne;
    private final List<Play<?, ?, ?>> plays;

    @Inject
    public Conductor(Playbook playbook, PlayRepository playRepository, ExpectationRepository expectationRepository,
                     ExpectationUpdateRepository expectationUpdateRepository, List<Play<?, ?, ?>> plays,
                     SimpleOrchestrationServicePlays.PlayOne playOne
    ) {
        this.playbook = playbook;
        this.expectationRepository = expectationRepository;
        this.expectationUpdateRepository = expectationUpdateRepository;
        this.playOne = playOne;
        this.playRepository = playRepository;
        this.plays = plays;
    }

//    @EventListener(ApplicationReadyEvent.class)
    public void startHook() throws InterruptedException {
        playRepository.saveAll(plays);
        log.info("waiting 2 seconds");
        TimeUnit.SECONDS.sleep(2);
        playOne.atThisTime = "time";
        playRepository.save(playOne);
        log.info("updated");

        Expectation expectationE1 = new Expectation("testEntityOne", "testZone", playOne, "statusTest");
        Expectation expectationE2 = new Expectation("testEntityTwo", "testZone", playOne, "statusTest");
        expectationRepository.saveAll(List.of(expectationE1, expectationE2));

        expectationUpdateRepository.save(new ExpectationUpdate(LocalDateTime.now(), expectationE1, "testStatusOne"));
        expectationUpdateRepository.save(new ExpectationUpdate(LocalDateTime.now(), expectationE1, "testStatusTwo"));
        expectationUpdateRepository.save(new ExpectationUpdate(LocalDateTime.now(), expectationE2, "testStatusOne"));
        expectationUpdateRepository.save(new ExpectationUpdate(LocalDateTime.now(), expectationE2, "testStatusTwo"));
    }

//    @EventListener(ApplicationReadyEvent.class)
//    public void startConductor() {
//        // sub conductor for each region/timezone
//        // E.g. is 10am for London
//        // get plays for this time in this region, including triggered plays
//        // run plays (pass in region/timezone)
//        // virtual thread per play
//        // waits on channel buffer to receive signal for entity start
//
//        var ch = new Channel<Integer>();
//
//        Play<?> play = playbook.plays().stream().findFirst().orElse(null);
//        if (play == null) {return;}
//        log.info(play.toString());
//        log.info(play.forTheseEntities.getEntities().toString());
//        log.info(play.orchestratorServerNode.toString());
//        if (play.orchestratorServerNode.getLocalServiceClient().isEmpty()) {
//            log.error("could not find service locally");
//        } else {
//            log.info("service is locally available");
//            // convert Play to PlayRun
//            log.info("dispatching play on client node");
//            var output = play.orchestratorServerNode.distributeToClients(new PlayRunInputToken());
//            log.info("client output: {}", output);
//        }
//    }
}
