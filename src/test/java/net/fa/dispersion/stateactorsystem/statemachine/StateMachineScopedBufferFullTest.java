package net.fa.dispersion.stateactorsystem.statemachine;

import net.fa.dispersion.stateactorsystem.statemachine.service.AnotherScopedStateMachineService;
import net.fa.dispersion.stateactorsystem.statemachine.service.ScopedStateMachineService;
import net.fa.dispersion.stateactorsystem.statemachine.simple_test_statemachines.SimpleStateMachine;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutionException;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
public class StateMachineScopedBufferFullTest {

    @Autowired
    private ScopedStateMachineService<SimpleStateMachine.SimpleStateMachineContext, Integer>
            scopedStateMachineServiceChildContext;
    @Autowired
    private AnotherScopedStateMachineService<SimpleStateMachine.SimpleStateMachineContext, Integer>
            anotherScopedStateMachineServiceChildContext;

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
//    public void testZTenActors() throws InterruptedException {
//        for (int i = 0; i < 10; i++) {
//            scopedStateMachineServiceChildContext.createSMActor(SimpleStateMachine.stateMachine);
//            anotherScopedStateMachineServiceChildContext.createSMActor(SimpleStateMachine.stateMachine);
//        }
//        System.gc();
//        sleep(1);
//        System.gc();
//        sleep(5);
//    }

    @Test
    @Tag("LongRunningTests")
    public void testDManyMillionActors() throws InterruptedException, ExecutionException {
        for (int i = 0; i < 3_000_000; i++) {
            scopedStateMachineServiceChildContext.createSMActor(SimpleStateMachine.stateMachine);
            anotherScopedStateMachineServiceChildContext.createSMActor(SimpleStateMachine.stateMachine);
        }
        System.gc();
    }

    @Test
    public void testCMillionActors() throws InterruptedException, ExecutionException {
        for (int i = 0; i < 1_000_000; i++) {
            scopedStateMachineServiceChildContext.createSMActor(SimpleStateMachine.stateMachine);
            anotherScopedStateMachineServiceChildContext.createSMActor(SimpleStateMachine.stateMachine);
        }
        System.gc();
    }

    @Test
//    @RepeatedTest(10)
    public void testBThousandActors() throws InterruptedException, ExecutionException {
        for (int i = 0; i < 1_000; i++) {
            scopedStateMachineServiceChildContext.createSMActor(SimpleStateMachine.stateMachine);
            anotherScopedStateMachineServiceChildContext.createSMActor(SimpleStateMachine.stateMachine);
        }
        System.gc();
    }

    @Test
    public void testATenActors() throws InterruptedException, ExecutionException {
        for (int i = 0; i < 10; i++) {
            scopedStateMachineServiceChildContext.createSMActor(SimpleStateMachine.stateMachine);
            anotherScopedStateMachineServiceChildContext.createSMActor(SimpleStateMachine.stateMachine);
        }
        System.gc();
    }
}
