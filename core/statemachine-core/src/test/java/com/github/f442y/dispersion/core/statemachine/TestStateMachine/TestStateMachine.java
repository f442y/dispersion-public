package com.github.f442y.dispersion.core.statemachine.TestStateMachine;


import com.github.f442y.dispersion.core.statemachine.context.StateMachineContext;
import com.github.f442y.dispersion.core.statemachine.context.StateMachineContextFactory;
import com.github.f442y.dispersion.core.statemachine.TestStateMachine.actions.multi_context_action.SampleContextInterface;
import com.github.f442y.dispersion.core.statemachine.TestStateMachine.states.StateA;
import com.github.f442y.dispersion.core.statemachine.TestStateMachine.states.StateB;
import com.github.f442y.dispersion.core.statemachine.TestStateMachine.states.StateC;
import com.github.f442y.dispersion.core.statemachine.exception.StateMachineException;
import com.github.f442y.dispersion.core.statemachine.state.StateKey;
import com.github.f442y.dispersion.core.statemachine.statemachineconfiguration.InputFunction;
import com.github.f442y.dispersion.core.statemachine.statemachineconfiguration.OutputFunction;
import com.github.f442y.dispersion.core.statemachine.statemachineconfiguration.StateMachineConfiguration;
import com.github.f442y.dispersion.core.statemachine.statemachineconfiguration.StateMapBuilder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestStateMachine
        extends StateMachineConfiguration<TestStateMachine.TestStateMachineContext, TestStateMachine.StateKeys,
        Integer, Integer> {
    private static final TestStateMachineContext CONTEXT_FACTORY = new TestStateMachineContext();

    public TestStateMachine() {
        super(StateMapBuilder
                .<TestStateMachineContext, StateKeys>buildStateMap(false)
                .states(StateKeys.class)
                .addStateMapping(StateKeys.A, new StateA())
                .addStateMapping(StateKeys.B, new StateB())
                .addStateMapping(StateKeys.C, new StateC())
//                .addStateMapping(States.C, new StateC())
                .done()
                .initialState(StateKeys.A)
                .endState(StateKeys.END)
                .build());
    }

    @Override
    public StateMachineContextFactory<TestStateMachineContext> stateMachineContextFactory() {
        return CONTEXT_FACTORY;
    }

    @Override
    public InputFunction<TestStateMachineContext, Integer> inputFunction() throws StateMachineException {
        return (testStateMachineContext, inputInteger) -> {
            testStateMachineContext.num = inputInteger;
            return testStateMachineContext;
        };
    }

    @Override
    public OutputFunction<TestStateMachineContext, Integer> outputFunction() throws StateMachineException {
        return testStateMachineContext -> testStateMachineContext.num;
    }

    public enum StateKeys implements StateKey {
        A, B, C, END
    }

    public static class TestStateMachineContext implements StateMachineContext,
            StateMachineContextFactory<TestStateMachineContext>,
            SampleContextInterface {
        public String string = "Simple Contextual String (Test)";
        public int num = 0;

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
