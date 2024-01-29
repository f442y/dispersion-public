package com.github.f442y.dispersion.core.statemachine.TestStateMachine.actions.simpleNumAddition;

import com.github.f442y.dispersion.core.statemachine.TestStateMachine.TestStateMachine;
import com.github.f442y.dispersion.core.statemachine.TestStateMachine.actions.simpleNumAddition.dependencies.ActionSimpleNumAdditionDependencies;
import com.github.f442y.dispersion.core.statemachine.exception.ActionException;
import com.github.f442y.dispersion.core.statemachine.state.action.Action;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ActionSimpleNumAddition
        extends Action<TestStateMachine.TestStateMachineContext, ActionSimpleNumAdditionDependencies> {

    public ActionSimpleNumAddition(ActionSimpleNumAdditionDependencies actionSimpleNumAdditionDependencies
    ) {
        super(actionSimpleNumAdditionDependencies);
    }

    @NonNull
    @Override
    public TestStateMachine.TestStateMachineContext action(TestStateMachine.TestStateMachineContext stateMachineContext,
                                                           ActionSimpleNumAdditionDependencies actionDependencies
    ) throws ActionException {
        for (int i = 0; i < 10; i++) {
            stateMachineContext.num++;
            stateMachineContext.string = String.valueOf(stateMachineContext.num);
        }
//        log.info("action run");

        return stateMachineContext;
    }
}
