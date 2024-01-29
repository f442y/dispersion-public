package com.github.f442y.dispersion.core.statemachine;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class TestStateMachineTests {


    private static TestStateMachineExecutor testStateMachineExecutor;

    @BeforeAll
    public static void before() throws InterruptedException {
//            TimeUnit.SECONDS.sleep(6);
        testStateMachineExecutor = new TestStateMachineExecutor(new TestApplicationStatic());
    }

    @Test
    @Tag("LongRunningTests")
    public void testNoSpringMultiVT() throws InterruptedException {
        int numOfSimultaneousCallingThreads = 2_000;
        int numOfSequentialCallsPerThread = 1_000;
        try (ExecutorService executorService = Executors.newThreadPerTaskExecutor(Thread
                .ofVirtual()
                .name("test-vt-noSpring", 1)
                .factory())) {
            CountDownLatch latch = new CountDownLatch(numOfSimultaneousCallingThreads);
            for (int t = 0; t < numOfSimultaneousCallingThreads; t++) {
                executorService.submit(() -> {
                    try {
                        for (int i = 0; i < numOfSequentialCallsPerThread; i++) {
                            var res = testStateMachineExecutor.dispatch().future().get();
//                            log.info("SM Future Resolved, i={}, {}", i, res);
                            assertEquals(30, res);
                        }
                        latch.countDown();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
            latch.await();
        }
//        System.gc();
//        TimeUnit.SECONDS.sleep(2);
    }

    @Test
    public void testNoSpringSingleVT() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Thread.startVirtualThread(() -> {
            try {
                var res = testStateMachineExecutor.dispatch().future().get();
                log.info("SM Future Resolved, {}", res);
                assertEquals(30, res);
                latch.countDown();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }).setName("test-vt-noSpring");
        latch.await();
    }
}
