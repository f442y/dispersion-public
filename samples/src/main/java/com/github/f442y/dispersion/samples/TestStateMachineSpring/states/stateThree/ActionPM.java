package com.github.f442y.dispersion.samples.TestStateMachineSpring.states.stateThree;

import com.github.f442y.dispersion.core.statemachine.dependency.ActionDependencies;
import com.github.f442y.dispersion.core.statemachine.state.action.Action;
import com.github.f442y.dispersion.core.statemachine.state.action.traits.PersistentMutation;
import com.github.f442y.dispersion.samples.TestStateMachineSpring.TestSpringStateMachine;
import jakarta.annotation.Nonnull;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

@Named
@Singleton
public class ActionPM
        extends Action<TestSpringStateMachine.StateMachineSpringContext, ActionDependencies.NoDependencies>
        implements PersistentMutation {

    public ActionPM() {
        super(new ActionDependencies.NoDependencies());
    }

    @Nonnull
    @Override
    public TestSpringStateMachine.StateMachineSpringContext action(
            TestSpringStateMachine.StateMachineSpringContext stateMachineContext,
            ActionDependencies.NoDependencies actionDependencies
    ) {
        for (int i = 0; i < 10; i++) {
            stateMachineContext.num++;
            stateMachineContext.string = String.valueOf(stateMachineContext.num);
        }
//        throw new ActionException()
        return stateMachineContext;
    }
}
