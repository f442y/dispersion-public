package com.github.f442y.dispersion.samples.TestStateMachineSpring.states.stateThree;

import com.github.f442y.dispersion.core.statemachine.dependency.ActionDependencies;
import com.github.f442y.dispersion.core.statemachine.exception.ActionException;
import com.github.f442y.dispersion.core.statemachine.state.action.Action;
import com.github.f442y.dispersion.core.statemachine.state.action.traits.PersistentMutation;
import com.github.f442y.dispersion.samples.TestStateMachineSpring.TestSpringStateMachine;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ActionPM
        extends Action<TestSpringStateMachine.StateMachineSpringContext, ActionDependencies.NoDependencies>
        implements PersistentMutation {

    public ActionPM() {
        super(new ActionDependencies.NoDependencies());
    }

    @Override
    public TestSpringStateMachine.@NonNull StateMachineSpringContext action(
            TestSpringStateMachine.StateMachineSpringContext stateMachineContext,
            ActionDependencies.NoDependencies actionDependencies
    ) throws ActionException {
        for (int i = 0; i < 10; i++) {
            stateMachineContext.num++;
            stateMachineContext.string = String.valueOf(stateMachineContext.num);
        }
//        throw new ActionException()
        return stateMachineContext;
    }
}
