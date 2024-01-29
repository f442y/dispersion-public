package net.fa.dispersion.stateactorsystem.statemachine;

import net.fa.dispersion.stateactorsystem.statemachine.service.AnotherExecutorStateMachineService;
import net.fa.dispersion.stateactorsystem.statemachine.service.ExecutorStateMachineService;
import net.fa.dispersion.stateactorsystem.statemachine.simple_test_statemachines.SimpleParentStateMachine;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
public class StateMachineBufferedExecutorNestedMachineFullTest {

    @Autowired
    private ExecutorStateMachineService<SimpleParentStateMachine.SimpleParentMachineContext, Void>
            executorStateMachineService;
    @Autowired
    private AnotherExecutorStateMachineService<SimpleParentStateMachine.SimpleParentMachineContext, Void>
            anotherExecutorStateMachineService;

//    private static void sleep(int seconds) throws InterruptedException {
//        TimeUnit.SECONDS.sleep(seconds);
//    }
//
//    @BeforeAll
//    public static void setUp() throws InterruptedException {
//        System.gc();
//        sleep(6);
//        System.gc();
//    }

//    @Test
//    public void testZTenNestedSMs() throws InterruptedException {
//        for (int i = 0; i < 10; i++) {
//            executorStateMachineService.createSMActor(SimpleParentStateMachine.stateMachine);
//            anotherExecutorStateMachineService.createSMActor(SimpleParentStateMachine.stateMachine);
//        }
//        System.gc();
//        sleep(1);
//        System.gc();
//        sleep(5);
//    }

    @Test
    @Tag("LongRunningTests")
    public void testDManyNestedSMs() throws InterruptedException {
        for (int i = 0; i < 500_000; i++) {
            executorStateMachineService.createSMActor(SimpleParentStateMachine.stateMachine);
            anotherExecutorStateMachineService.createSMActor(SimpleParentStateMachine.stateMachine);
        }
    }

    @Test
    public void testBThousandNestedSMs() throws InterruptedException {
        for (int i = 0; i < 1_000; i++) {
            executorStateMachineService.createSMActor(SimpleParentStateMachine.stateMachine);
            anotherExecutorStateMachineService.createSMActor(SimpleParentStateMachine.stateMachine);
        }
    }

    @Test
    public void testATenNestedSMs() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            executorStateMachineService.createSMActor(SimpleParentStateMachine.stateMachine);
            anotherExecutorStateMachineService.createSMActor(SimpleParentStateMachine.stateMachine);
        }
    }
}
