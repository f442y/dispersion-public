package com.github.f442y.dispersion.core.statemachine.TestStateMachine.states;

import com.github.f442y.dispersion.core.statemachine.TestStateMachine.TestStateMachine;
import com.github.f442y.dispersion.core.statemachine.TestStateMachine.actions.multi_context_action.ActionMultiContext;
import com.github.f442y.dispersion.core.statemachine.TestStateMachine.actions.simpleNumAddition.dependencies.ActionSimpleNumAdditionDependencies;
import com.github.f442y.dispersion.core.statemachine.exception.TransitionException;
import com.github.f442y.dispersion.core.statemachine.state.State;
import com.github.f442y.dispersion.core.statemachine.state.transition.Transition;
import com.github.f442y.dispersion.core.statemachine.state.transition.TransitionStateKey;

public class StateC extends State<TestStateMachine.TestStateMachineContext, TestStateMachine.StateKeys> {

    public StateC() {
        super(ACTION_MULTI_CONTEXT, TRANSITION_STATE_C);
    }

    // Action

    private static final ActionMultiContext<TestStateMachine.TestStateMachineContext> ACTION_MULTI_CONTEXT =
            new ActionMultiContext<>(new ActionSimpleNumAdditionDependencies());
    private static final Transition<TestStateMachine.TestStateMachineContext, TestStateMachine.StateKeys, TSAKey>
            TRANSITION_STATE_C = new Transition<>(TSAKey.class) {
        @Override
        public TSAKey resolvePermittedTransition(TestStateMachine.TestStateMachineContext stateMachineContext
        ) throws TransitionException {
            return TSAKey.CtoEND;
        }
    };

    // Transition

    public enum TSAKey implements TransitionStateKey<TestStateMachine.StateKeys> {
        CtoEND {
            @Override
            public TestStateMachine.StateKeys stateKey() {
                return TestStateMachine.StateKeys.END;
            }
        }
    }
}
