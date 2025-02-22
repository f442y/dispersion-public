package com.github.f442y.dispersion.orchestrator.websocket;

import com.github.f442y.dispersion.orchestrator.playbook.Playbook;
import com.github.f442y.dispersion.orchestrator.playbook.SamplePlaybook;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.CountDownLatch;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@Tag("Playground")
public class WSHandlerPlaygroundTest {

    private final SamplePlaybook samplePlaybook;
    private final Playbook playbook;
    CountDownLatch latch = new CountDownLatch(1);

    @Inject
    public WSHandlerPlaygroundTest(SamplePlaybook samplePlaybook, Playbook playbook) {
        this.samplePlaybook = samplePlaybook;
        this.playbook = playbook;
    }

    @Test
    public void wsHandlerPlaygroundTest() throws InterruptedException {
        samplePlaybook.init();
        playbook.reloadAllPlayTasksWithDependencies();

        latch.await();
    }
}