package com.github.f442y.dispersion.samples.TestStateMachineSpring;

import com.github.f442y.dispersion.samples.TestStateMachineSpring.service.BufferedBlockingSpawningServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Slf4j
public class SampleTestSpringStateMachineTests {

    @Autowired
    BufferedBlockingSpawningServiceImpl bufferedBlockingSpawningService;

//    @BeforeAll
//    public static void before() throws InterruptedException {
//        TimeUnit.SECONDS.sleep(4);
//    }

    @Test
    @Tag("LongRunningTests")
    public void testMultiVT() throws InterruptedException {
        int numOfSimultaneousCallingThreads = 2_000;
        int numOfSequentialCallsPerThread = 1_000;
        try (ExecutorService executorService = Executors.newThreadPerTaskExecutor(Thread
                .ofVirtual()
                .name("test-vt-", 1)
                .factory())) {
            CountDownLatch latch = new CountDownLatch(numOfSimultaneousCallingThreads);
            for (int t = 0; t < numOfSimultaneousCallingThreads; t++) {
                executorService.submit(() -> {
                    try {
                        for (int i = 0; i < numOfSequentialCallsPerThread; i++) {
                            var res = bufferedBlockingSpawningService.dispatch().future().get();
//                            log.info("SM Future Resolved, i={}, {}", i, res);
                            assertEquals(40, res);
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
    public void testSingleVT() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Thread.startVirtualThread(() -> {
            try {
                var res = bufferedBlockingSpawningService.dispatch().future().get();
                log.info("SM Future Resolved, {}", res);
                assertEquals(40, res);
                latch.countDown();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }).setName("test-vt");
        latch.await();
    }
}
