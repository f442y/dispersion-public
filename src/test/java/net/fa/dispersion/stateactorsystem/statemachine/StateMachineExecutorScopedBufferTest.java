package net.fa.dispersion.stateactorsystem.statemachine;

import net.fa.dispersion.stateactorsystem.statemachine.service.ExecutorStateMachineService;
import net.fa.dispersion.stateactorsystem.statemachine.simple_test_statemachines.SimpleParentStateMachine;
import net.fa.dispersion.stateactorsystem.statemachine.simple_test_statemachines.SimpleStateMachine;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutionException;

@SpringBootTest
public class StateMachineExecutorScopedBufferTest {
    private static final Logger logger = LoggerFactory.getLogger(StateMachineExecutorScopedBufferTest.class);
    @Autowired
    private ExecutorStateMachineService<SimpleStateMachine.SimpleStateMachineContext, Integer>
            executorStateMachineService;
    @Autowired
    private ExecutorStateMachineService<SimpleParentStateMachine.SimpleParentMachineContext, Void>
            executorStateMachineServiceForNested;

//    @BeforeAll
//    public static void setUp() throws InterruptedException {
//        sleep(0);
//    }
//
//    private static void sleep(int seconds) throws InterruptedException {
//        TimeUnit.SECONDS.sleep(seconds);
//    }
//
//    @Test
//    public void testZTenActors() throws InterruptedException {
//        for (int i = 0; i < 10; i++) {
//            var actor = scopedStateMachineService.createSMActor(SimpleStateMachine.stateMachine);
//        }
//        sleep(5);
//    }

    @Test
    public void testSM() throws InterruptedException, ExecutionException {
        System.out.println(
                executorStateMachineService.createSMActor(SimpleStateMachine.stateMachine).completableFuture.get());

    }

    @Test
    public void testNestedSM() throws InterruptedException, ExecutionException {
        executorStateMachineServiceForNested.createSMActor(SimpleParentStateMachine.stateMachine);
    }
}
