package com.github.f442y.dispersion.core.statemachine;

import com.github.f442y.dispersion.core.statemachine.TestStateMachine.TestStateMachine;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestStateMachineTests {
    private static final Logger log = LoggerFactory.getLogger(TestStateMachineTests.class);
    private static TestStateMachineExecutor testStateMachineExecutor;

    @BeforeAll
    public static void before() throws InterruptedException {
//            TimeUnit.SECONDS.sleep(6);
        testStateMachineExecutor = new TestStateMachineExecutor(new TestApplicationStatic());
    }

    /**
     * Many threads making many sequential calls
     */
    @Test
    @Tag("LongRunningTests")
    public void testNoSpringMultiVT() throws InterruptedException {
        int numOfSimultaneousCallingThreads = 2_000;
        int numOfSequentialCallsPerThread = 1_000;
        try (ExecutorService executorService = Executors.newThreadPerTaskExecutor(Thread
                .ofVirtual()
                .name("test-vt-noSpring", 1)
                .factory())) {
            CountDownLatch totalLatch =
                    new CountDownLatch(numOfSimultaneousCallingThreads * numOfSequentialCallsPerThread);
            CountDownLatch latch = new CountDownLatch(numOfSimultaneousCallingThreads);
            for (int t = 0; t < numOfSimultaneousCallingThreads; t++) {
                executorService.submit(() -> {
                    try {
                        for (int i = 0; i < numOfSequentialCallsPerThread; i++) {
                            var res = testStateMachineExecutor
                                    .dispatchWithInput(new TestStateMachine.WrappedInput(totalLatch, 10))
                                    .future()
                                    .get();
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
            totalLatch.await();
        }
//        System.gc();
//        TimeUnit.SECONDS.sleep(2);
    }

    /**
     * One thread making many sequential calls
     */
    @Test
    @Tag("LongRunningTests")
    public void testNoSpringMultiVTLatched() throws InterruptedException {
        int numOfSequentialCalls = 2_000_000;
        CountDownLatch latch = new CountDownLatch(numOfSequentialCalls);
        Thread.startVirtualThread(() -> {
            try {
                for (int i = 0; i < numOfSequentialCalls; i++) {
                    testStateMachineExecutor.dispatchWithInput(new TestStateMachine.WrappedInput(latch, 10));
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).setName("test-vt-noSpring");
        latch.await();
//        System.gc();
//        TimeUnit.SECONDS.sleep(2);
    }

    /**
     * One thread making one call
     */
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
