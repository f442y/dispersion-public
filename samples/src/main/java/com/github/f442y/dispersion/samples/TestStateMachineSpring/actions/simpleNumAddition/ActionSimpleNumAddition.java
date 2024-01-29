package com.github.f442y.dispersion.samples.TestStateMachineSpring.actions.simpleNumAddition;

import com.github.f442y.dispersion.core.statemachine.state.action.Action;
import com.github.f442y.dispersion.samples.TestStateMachineSpring.TestComponent;
import com.github.f442y.dispersion.samples.TestStateMachineSpring.TestSpringStateMachine;
import com.github.f442y.dispersion.samples.TestStateMachineSpring.actions.simpleNumAddition.dependencies.ActionSimpleNumAdditionDependencies;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ActionSimpleNumAddition
        extends Action<TestSpringStateMachine.StateMachineSpringContext, ActionSimpleNumAdditionDependencies> {

    public final TestComponent testComponent;

    @Autowired
    public ActionSimpleNumAddition(ActionSimpleNumAdditionDependencies actionSimpleNumAdditionDependencies,
                                   TestComponent testComponent
    ) {
        super(actionSimpleNumAdditionDependencies);
        this.testComponent = testComponent;
    }

    @Override
    public TestSpringStateMachine.@NonNull StateMachineSpringContext action(
            TestSpringStateMachine.StateMachineSpringContext stateMachineContext,
            ActionSimpleNumAdditionDependencies actionDependencies
    ) {
        for (int i = 0; i < 10; i++) {
            stateMachineContext.num++;
            stateMachineContext.string = String.valueOf(stateMachineContext.num);

        }
//        try {
//            TimeUnit.SECONDS.sleep(6);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        return stateMachineContext;
    }
}
