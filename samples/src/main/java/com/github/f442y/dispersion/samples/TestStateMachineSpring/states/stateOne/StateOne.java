package com.github.f442y.dispersion.samples.TestStateMachineSpring.states.stateOne;

import com.github.f442y.dispersion.core.statemachine.exception.TransitionException;
import com.github.f442y.dispersion.core.statemachine.state.State;
import com.github.f442y.dispersion.core.statemachine.state.transition.Transition;
import com.github.f442y.dispersion.core.statemachine.state.transition.TransitionStateKey;
import com.github.f442y.dispersion.samples.TestStateMachineSpring.TestSpringStateMachine;
import com.github.f442y.dispersion.samples.TestStateMachineSpring.actions.simpleNumAddition.ActionSimpleNumAddition;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

@Named
@Singleton
public final class StateOne
        extends State<TestSpringStateMachine.StateMachineSpringContext, TestSpringStateMachine.States> {

    @Inject
    public StateOne(ActionSimpleNumAddition actionSimpleNumAddition) {
        super(actionSimpleNumAddition, new TransitionStateOne());
    }

    public static final class TransitionStateOne
            extends Transition<TestSpringStateMachine.StateMachineSpringContext, TestSpringStateMachine.States,
            TransitionStateOne.TS1Key> {
        public TransitionStateOne() {
            super(TS1Key.class);
        }

        @Override
        public TS1Key resolvePermittedTransition(TestSpringStateMachine.StateMachineSpringContext stateMachineContext
        ) throws TransitionException {
            return TS1Key.ONEtoTWO;
        }

        public enum TS1Key implements TransitionStateKey<TestSpringStateMachine.States> {
            ONEtoTWO(TestSpringStateMachine.States.TWO), ONEtoTHREE(TestSpringStateMachine.States.THREE);

            private final TestSpringStateMachine.States state;

            TS1Key(TestSpringStateMachine.States state) {
                this.state = state;
            }

            @Override
            public TestSpringStateMachine.States stateKey() {
                return state;
            }
        }
    }
}