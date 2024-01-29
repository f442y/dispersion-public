package com.github.f442y.dispersion.samples.TestStateMachineSpring.states.stateThree;

import com.github.f442y.dispersion.core.statemachine.state.State;
import com.github.f442y.dispersion.samples.TestStateMachineSpring.TestSpringStateMachine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class StateThree
        extends State<TestSpringStateMachine.StateMachineSpringContext, TestSpringStateMachine.States> {

    @Autowired
    public StateThree(ActionPM actionPM, TransitionStateThree transitionStateThree) {
        super(actionPM, transitionStateThree);
    }
}
