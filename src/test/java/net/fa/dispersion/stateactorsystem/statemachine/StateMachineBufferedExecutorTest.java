package net.fa.dispersion.stateactorsystem.statemachine;

import lombok.extern.slf4j.Slf4j;
import net.fa.dispersion.stateactorsystem.statemachine.service.ExecutorStateMachineService;
import net.fa.dispersion.stateactorsystem.statemachine.simple_test_statemachines.SimpleParentStateMachine;
import net.fa.dispersion.stateactorsystem.statemachine.simple_test_statemachines.SimpleStateMachine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class StateMachineBufferedExecutorTest {
    @Autowired
    private ExecutorStateMachineService<SimpleStateMachine.SimpleStateMachineContext, Integer>
            executorStateMachineService;
    @Autowired
    private ExecutorStateMachineService<SimpleParentStateMachine.SimpleParentMachineContext, Void>
            executorStateMachineServiceForNested;

    @Test
    public void testSM() throws InterruptedException {
        executorStateMachineService.createSMActor(SimpleStateMachine.stateMachine);
    }

    @Test
    public void testNestedSM() throws InterruptedException {
        executorStateMachineServiceForNested.createSMActor(SimpleParentStateMachine.stateMachine);
    }
}
