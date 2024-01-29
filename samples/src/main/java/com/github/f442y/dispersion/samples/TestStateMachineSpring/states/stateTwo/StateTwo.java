package com.github.f442y.dispersion.samples.TestStateMachineSpring.states.stateTwo;

import com.github.f442y.dispersion.core.statemachine.state.State;
import com.github.f442y.dispersion.samples.TestStateMachineSpring.TestSpringStateMachine;
import com.github.f442y.dispersion.samples.TestStateMachineSpring.actions.simpleNumAddition.ActionSimpleNumAddition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class StateTwo
        extends State<TestSpringStateMachine.StateMachineSpringContext, TestSpringStateMachine.States> {

    @Autowired
    public StateTwo(ActionSimpleNumAddition actionSimpleNumAddition, TransitionStateTwo transitionStateTwo) {
        super(actionSimpleNumAddition, transitionStateTwo);
    }
}
