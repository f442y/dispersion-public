package net.fa.dispersion.stateactorsystem.statemachine;

import net.fa.dispersion.stateactorsystem.statemachine.service.AnotherScopedStateMachineService;
import net.fa.dispersion.stateactorsystem.statemachine.service.ScopedStateMachineService;
import net.fa.dispersion.stateactorsystem.statemachine.simple_test_statemachines.SimpleParentStateMachine;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutionException;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
public class StateMachineNestedScopedBufferFullTest {

    @Autowired
    private ScopedStateMachineService<SimpleParentStateMachine.SimpleParentMachineContext, Void>
            scopedStateMachineServiceParentContext;
    @Autowired
    private AnotherScopedStateMachineService<SimpleParentStateMachine.SimpleParentMachineContext, Void>
            anotherScopedStateMachineServiceParentContext;

//    @BeforeAll
//    public static void setUp() throws InterruptedException {
//        System.gc();
//        sleep(6);
//        System.gc();
//    }
//
//    private static void sleep(int seconds) throws InterruptedException {
//        TimeUnit.SECONDS.sleep(seconds);
//    }
//
//    @Test
//    public void testZTenNestedSMs() throws InterruptedException {
//        for (int i = 0; i < 10; i++) {
//            scopedStateMachineService.createSMActor(SimpleNestedStateMachine.stateMachine);
//            anotherScopedStateMachineService.createSMActor(SimpleNestedStateMachine.stateMachine);
//        }
//        System.gc();
//        sleep(1);
//        System.gc();
//        sleep(5);
//    }

    @Test
    @Tag("LongRunningTests")
    public void testDManyNestedSMs() throws InterruptedException, ExecutionException {
        for (int i = 0; i < 500_000; i++) {
            scopedStateMachineServiceParentContext.createSMActor(
                    SimpleParentStateMachine.stateMachine).completableFuture.get();
            anotherScopedStateMachineServiceParentContext.createSMActor(SimpleParentStateMachine.stateMachine);
        }
        System.gc();
    }

    @Test
//    @RepeatedTest(10)
    public void testBThousandNestedSMs() throws InterruptedException, ExecutionException {
        for (int i = 0; i < 1_000; i++) {
            scopedStateMachineServiceParentContext.createSMActor(SimpleParentStateMachine.stateMachine);
            anotherScopedStateMachineServiceParentContext.createSMActor(SimpleParentStateMachine.stateMachine);
        }
        System.gc();
    }

    @Test
    public void testATenNestedSMs() throws InterruptedException, ExecutionException {
        for (int i = 0; i < 10; i++) {
            scopedStateMachineServiceParentContext.createSMActor(SimpleParentStateMachine.stateMachine);
            anotherScopedStateMachineServiceParentContext.createSMActor(SimpleParentStateMachine.stateMachine);
        }
        System.gc();
    }
}
