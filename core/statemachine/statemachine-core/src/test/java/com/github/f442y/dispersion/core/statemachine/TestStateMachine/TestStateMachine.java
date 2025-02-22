package com.github.f442y.dispersion.core.statemachine.TestStateMachine;

import com.github.f442y.dispersion.core.statemachine.TestApplicationStatic;
import com.github.f442y.dispersion.core.statemachine.TestStateMachine.actions.multi_context_action.SampleContextInterface;
import com.github.f442y.dispersion.core.statemachine.TestStateMachine.states.StateA;
import com.github.f442y.dispersion.core.statemachine.TestStateMachine.states.StateB;
import com.github.f442y.dispersion.core.statemachine.TestStateMachine.states.StateC;
import com.github.f442y.dispersion.core.statemachine.TestStateMachineEventMonitor;
import com.github.f442y.dispersion.core.statemachine.context.StateMachineContext;
import com.github.f442y.dispersion.core.statemachine.context.StateMachineContextFactory;
import com.github.f442y.dispersion.core.statemachine.events.StateMachineEvent;
import com.github.f442y.dispersion.core.statemachine.events.StateMachineEventMonitorAPI;
import com.github.f442y.dispersion.core.statemachine.exception.StateMachineException;
import com.github.f442y.dispersion.core.statemachine.state.StateKey;
import com.github.f442y.dispersion.core.statemachine.statemachineconfiguration.InputFunction;
import com.github.f442y.dispersion.core.statemachine.statemachineconfiguration.OutputFunction;
import com.github.f442y.dispersion.core.statemachine.statemachineconfiguration.StateMachineConfiguration;
import com.github.f442y.dispersion.core.statemachine.statemachineconfiguration.StateMapBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

public class TestStateMachine
        extends StateMachineConfiguration<TestStateMachine.TestStateMachineContext, TestStateMachine.StateKeys,
        TestStateMachine.WrappedInput, Integer> {
    private static final Logger log = LoggerFactory.getLogger(TestStateMachine.class);

    public enum StateKeys implements StateKey {
        A, B, C, END
    }

    private static final TestStateMachineContext CONTEXT_FACTORY = new TestStateMachineContext();

    private static final StateMachineEventMonitorAPI<StateMachineEvent> EM = new TestStateMachineEventMonitor();

    public record WrappedInput(CountDownLatch latch, Integer input) {}

    public TestStateMachine() {
        super(
                new TestApplicationStatic(),
                EM,
                StateMapBuilder
                        .<TestStateMachineContext, StateKeys>buildStateMap(false)
                        .states(StateKeys.class)
                        .addStateMapping(StateKeys.A, new StateA())
                        .addStateMapping(StateKeys.B, new StateB())
                        .addStateMapping(StateKeys.C, new StateC())
//                .addStateMapping(States.C, new StateC())
                        .done()
                        .initialState(StateKeys.A)
                        .endStateKey(StateKeys.END)
                        .build()
        );
    }

    @Override
    public StateMachineContextFactory<TestStateMachineContext> stateMachineContextFactory() {
        return CONTEXT_FACTORY;
    }

    @Override
    public InputFunction<TestStateMachineContext, WrappedInput> inputFunction() throws StateMachineException {
        return (testStateMachineContext, wrappedInput) -> {
            testStateMachineContext.num = 10;
            if (wrappedInput.latch != null) {
                testStateMachineContext.latch = wrappedInput.latch;
            }
            return testStateMachineContext;
        };
    }

    @Override
    public OutputFunction<TestStateMachineContext, Integer> outputFunction() throws StateMachineException {
        return testStateMachineContext -> {
            if (testStateMachineContext.latch != null) {
                testStateMachineContext.latch.countDown();
            }
            return testStateMachineContext.num;
        };
    }

    public static class TestStateMachineContext implements StateMachineContext,
            StateMachineContextFactory<TestStateMachineContext>,
            SampleContextInterface {
        public String string = "Simple Contextual String (Test)";
        public int num = 0;
        public CountDownLatch latch;

        @Override
        public TestStateMachineContext newInstance() {
            return new TestStateMachineContext();
        }

        @Override
        public void sampleContextSpecificMethod() {
//            log.info("SampleContextInterface accessed");
        }
    }
}
