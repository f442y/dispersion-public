package com.github.f442y.dispersion.core.statemachine;

import com.github.f442y.dispersion.core.application.ApplicationConfig;
import com.github.f442y.dispersion.core.statemachine.TestStateMachine.TestStateMachine;
import com.github.f442y.dispersion.core.statemachine.executor.BufferedStateMachineExecutor;
import com.github.f442y.dispersion.core.statemachine.statemachineconfiguration.StateMachineConfigurationWithCallableTriggers;

public class TestStateMachineExecutor
        extends BufferedStateMachineExecutor<TestStateMachine.TestStateMachineContext, TestStateMachine.StateKeys,
                Integer, Integer> {

    public static final StateMachineConfigurationWithCallableTriggers<TestStateMachine.TestStateMachineContext,
                TestStateMachine.StateKeys, Integer, Integer>
            TEST_STATE_MACHINE = new TestStateMachine();

    public TestStateMachineExecutor(ApplicationConfig applicationConfig) {
        super(
                "buffered-blocking",
                TEST_STATE_MACHINE,
                Runtime.getRuntime().availableProcessors() * 3,
                applicationConfig
        );
    }

    public StateMachineFuture<Integer> dispatch() throws InterruptedException {
        return super.addStateMachineTaskToExecutor(10);
    }

    public StateMachineFuture<Integer> dispatchWithInput(int input) throws InterruptedException {
        return super.addStateMachineTaskToExecutor(input);
    }
}