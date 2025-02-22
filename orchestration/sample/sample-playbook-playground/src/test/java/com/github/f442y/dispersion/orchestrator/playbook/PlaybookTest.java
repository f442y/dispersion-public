package com.github.f442y.dispersion.orchestrator.playbook;

import jakarta.inject.Inject;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.CountDownLatch;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@Tag("Playground")
public class PlaybookTest {
    private static final Logger log = LoggerFactory.getLogger(PlaybookTest.class);
    private final SamplePlaybook samplePlaybook;
    private final Playbook playbook;

    @Inject
    public PlaybookTest(SamplePlaybook samplePlaybook, Playbook playbook) {
        this.samplePlaybook = samplePlaybook;
        this.playbook = playbook;
    }

    @Test
    public void playgroundTest() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        // Load Sample Playbook data
        samplePlaybook.init();
        samplePlaybook.useData();

        // Build Playbook from DB
        playbook.reloadAllPlayTasksWithDependencies();

        latch.await();
    }
}
