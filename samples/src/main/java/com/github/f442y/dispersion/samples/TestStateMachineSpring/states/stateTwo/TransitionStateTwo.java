package com.github.f442y.dispersion.samples.TestStateMachineSpring.states.stateTwo;

import com.github.f442y.dispersion.core.statemachine.exception.TransitionException;
import com.github.f442y.dispersion.core.statemachine.state.transition.Transition;
import com.github.f442y.dispersion.core.statemachine.state.transition.TransitionStateKey;
import com.github.f442y.dispersion.samples.TestStateMachineSpring.TestSpringStateMachine;
import org.springframework.stereotype.Component;

@Component
public class TransitionStateTwo
        extends Transition<TestSpringStateMachine.StateMachineSpringContext, TestSpringStateMachine.States,
        TransitionStateTwo.TS2Key> {
    public TransitionStateTwo() {
        super(TS2Key.class);
    }

    @Override
    public TS2Key resolvePermittedTransition(TestSpringStateMachine.StateMachineSpringContext stateMachineContext
    ) throws TransitionException {
        return TS2Key.THREE;
    }

    public enum TS2Key implements TransitionStateKey<TestSpringStateMachine.States> {
        THREE(TestSpringStateMachine.States.THREE), END(TestSpringStateMachine.States.END);

        private final TestSpringStateMachine.States state;

        TS2Key(TestSpringStateMachine.States state) {
            this.state = state;
        }

        @Override
        public TestSpringStateMachine.States stateKey() {
            return state;
        }
    }
}
