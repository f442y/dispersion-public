package com.github.f442y.dispersion.samples.TestStateMachineSpring.states.stateThree;

import com.github.f442y.dispersion.core.statemachine.state.State;
import com.github.f442y.dispersion.samples.TestStateMachineSpring.TestSpringStateMachine;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

@Named
@Singleton
public final class StateThree
        extends State<TestSpringStateMachine.StateMachineSpringContext, TestSpringStateMachine.States> {

    @Inject
    public StateThree(ActionPM actionPM, TransitionStateThree transitionStateThree) {
        super(actionPM, transitionStateThree);
    }
}
