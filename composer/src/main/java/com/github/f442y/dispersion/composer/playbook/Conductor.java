package com.github.f442y.dispersion.composer.playbook;

import com.github.f442y.dispersion.composer.model.PlayRepository;
import com.github.f442y.dispersion.composer.model.SimpleOrchestrationServicePlays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Component
@Slf4j
public class Conductor {

    private final Playbook playbook;
    private final PlayRepository playRepository;
    private final SimpleOrchestrationServicePlays.PlayOne playOne;

    @Autowired
    public Conductor(Playbook playbook, SimpleOrchestrationServicePlays.PlayOne playOne, PlayRepository playRepository) {
        this.playbook = playbook;
        this.playOne = playOne;
        this.playRepository = playRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void startHook() throws InterruptedException {
        playRepository.save(playOne);
        log.info("waiting 10s");
        TimeUnit.SECONDS.sleep(11);
        playOne.atThisTime = "time";
        playRepository.save(playOne);
        log.info("updated");
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
