package com.github.f442y.dispersion.core.statemachine;

import com.github.f442y.dispersion.core.application.ApplicationConfig;
import com.github.f442y.dispersion.core.statemachine.TestStateMachine.TestStateMachine;
import com.github.f442y.dispersion.core.statemachine.executor.BufferedStateMachineExecutor;
import com.github.f442y.dispersion.core.statemachine.statemachineconfiguration.StateMachineConfigurationWithCallableTriggers;

public class TestStateMachineExecutor
        extends BufferedStateMachineExecutor<TestStateMachine.TestStateMachineContext, TestStateMachine.StateKeys,
        TestStateMachine.WrappedInput, Integer> {

    public static final StateMachineConfigurationWithCallableTriggers<TestStateMachine.TestStateMachineContext,
            TestStateMachine.StateKeys, TestStateMachine.WrappedInput, Integer>
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
        return super.addStateMachineTaskToExecutor(new TestStateMachine.WrappedInput(null, null));
    }

    public StateMachineFuture<Integer> dispatchWithInput(TestStateMachine.WrappedInput wrappedInput
    ) throws InterruptedException {
        return super.addStateMachineTaskToExecutor(wrappedInput);
    }
}