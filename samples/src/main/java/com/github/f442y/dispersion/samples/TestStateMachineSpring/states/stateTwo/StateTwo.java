package com.github.f442y.dispersion.samples.TestStateMachineSpring.states.stateTwo;

import com.github.f442y.dispersion.core.statemachine.state.State;
import com.github.f442y.dispersion.samples.TestStateMachineSpring.TestSpringStateMachine;
import com.github.f442y.dispersion.samples.TestStateMachineSpring.actions.simpleNumAddition.ActionSimpleNumAddition;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

@Named
@Singleton
public final class StateTwo
        extends State<TestSpringStateMachine.StateMachineSpringContext, TestSpringStateMachine.States> {

    @Inject
    public StateTwo(ActionSimpleNumAddition actionSimpleNumAddition, TransitionStateTwo transitionStateTwo) {
        super(actionSimpleNumAddition, transitionStateTwo);
    }
}
