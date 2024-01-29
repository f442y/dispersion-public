package com.github.f442y.dispersion.core.statemachine.TestStateMachine.states;

import com.github.f442y.dispersion.core.statemachine.TestStateMachine.TestStateMachine;
import com.github.f442y.dispersion.core.statemachine.TestStateMachine.actions.simpleNumAddition.ActionSimpleNumAddition;
import com.github.f442y.dispersion.core.statemachine.TestStateMachine.actions.simpleNumAddition.dependencies.ActionSimpleNumAdditionDependencies;
import com.github.f442y.dispersion.core.statemachine.exception.TransitionException;
import com.github.f442y.dispersion.core.statemachine.state.State;
import com.github.f442y.dispersion.core.statemachine.state.transition.Transition;
import com.github.f442y.dispersion.core.statemachine.state.transition.TransitionStateKey;

public class StateB extends State<TestStateMachine.TestStateMachineContext, TestStateMachine.StateKeys> {

    public StateB() {
        super(ACTION_TWO, TRANSITION_STATE_B);
    }

    // Action

    private static final ActionSimpleNumAddition ACTION_TWO =
            new ActionSimpleNumAddition(new ActionSimpleNumAdditionDependencies());
    private static final Transition<TestStateMachine.TestStateMachineContext, TestStateMachine.StateKeys, TSBKey>
            TRANSITION_STATE_B = new Transition<>(TSBKey.class) {
        @Override
        public TSBKey resolvePermittedTransition(TestStateMachine.TestStateMachineContext stateMachineContext
        ) throws TransitionException {
            return TSBKey.BtoC;
        }
    };

    // Transition

    public enum TSBKey implements TransitionStateKey<TestStateMachine.StateKeys> {
        BtoC {
            @Override
            public TestStateMachine.StateKeys stateKey() {
                return TestStateMachine.StateKeys.C;
            }
        }
    }
}
