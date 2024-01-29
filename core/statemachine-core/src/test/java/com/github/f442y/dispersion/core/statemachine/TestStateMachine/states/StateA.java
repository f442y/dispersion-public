package com.github.f442y.dispersion.core.statemachine.TestStateMachine.states;

import com.github.f442y.dispersion.core.statemachine.TestStateMachine.TestStateMachine;
import com.github.f442y.dispersion.core.statemachine.TestStateMachine.actions.simpleNumAddition.dependencies.ActionSimpleNumAdditionDependencies;
import com.github.f442y.dispersion.core.statemachine.exception.ActionException;
import com.github.f442y.dispersion.core.statemachine.exception.TransitionException;
import com.github.f442y.dispersion.core.statemachine.state.State;
import com.github.f442y.dispersion.core.statemachine.state.action.Action;
import com.github.f442y.dispersion.core.statemachine.state.transition.Transition;
import com.github.f442y.dispersion.core.statemachine.state.transition.TransitionStateKey;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StateA extends State<TestStateMachine.TestStateMachineContext, TestStateMachine.StateKeys> {
    public StateA() {
        super(ACTION_ONE, TRANSITION_STATE_A);
    }

    // Action

    private static final ActionOne ACTION_ONE = new ActionOne(new ActionSimpleNumAdditionDependencies());
    private static final Logger log = LoggerFactory.getLogger(StateA.class);

    private static class ActionOne
            extends Action<TestStateMachine.TestStateMachineContext, ActionSimpleNumAdditionDependencies> {

        public ActionOne(ActionSimpleNumAdditionDependencies actionSimpleNumAdditionDependencies) {
            super(actionSimpleNumAdditionDependencies);
        }

        @Override
        public TestStateMachine.@NonNull TestStateMachineContext action(
                TestStateMachine.TestStateMachineContext stateMachineContext,
                ActionSimpleNumAdditionDependencies actionDependencies
        ) throws ActionException {
            for (int i = 0; i < 10; i++) {
                stateMachineContext.num++;
                stateMachineContext.string = String.valueOf(stateMachineContext.num);
            }
            return stateMachineContext;
        }
    }

    private static final Transition<TestStateMachine.TestStateMachineContext, TestStateMachine.StateKeys, TSAKey>
            TRANSITION_STATE_A = new Transition<>(TSAKey.class) {
        @Override
        public TSAKey resolvePermittedTransition(TestStateMachine.TestStateMachineContext stateMachineContext
        ) throws TransitionException {
            return TSAKey.AtoB;
        }
    };

    public enum TSAKey implements TransitionStateKey<TestStateMachine.StateKeys> {
        AtoB {
            @Override
            public TestStateMachine.StateKeys stateKey() {
                return TestStateMachine.StateKeys.B;
            }
        }
    }
}
