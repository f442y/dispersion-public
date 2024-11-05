package com.github.f442y.dispersion.samples.TestStateMachineSpring.states.stateThree;

import com.github.f442y.dispersion.core.statemachine.exception.TransitionException;
import com.github.f442y.dispersion.core.statemachine.state.transition.Transition;
import com.github.f442y.dispersion.core.statemachine.state.transition.TransitionStateKey;
import com.github.f442y.dispersion.samples.TestStateMachineSpring.TestSpringStateMachine;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

@Named
@Singleton
public class TransitionStateThree
        extends Transition<TestSpringStateMachine.StateMachineSpringContext, TestSpringStateMachine.States,
        TransitionStateThree.TS3Key> {
    public TransitionStateThree() {
        super(TS3Key.class);
    }

    @Override
    public TS3Key resolvePermittedTransition(TestSpringStateMachine.StateMachineSpringContext stateMachineContext
    ) throws TransitionException {
        return TS3Key.END;
    }

    public enum TS3Key implements TransitionStateKey<TestSpringStateMachine.States> {
        END {
            @Override
            public TestSpringStateMachine.States stateKey() {
                return TestSpringStateMachine.States.END;
            }
        },
//        TWO {
//            @Override
//            public TestSpringStateMachine.States stateKey() {
//                return TestSpringStateMachine.States.TWO;
//            }
//        }
    }
}
