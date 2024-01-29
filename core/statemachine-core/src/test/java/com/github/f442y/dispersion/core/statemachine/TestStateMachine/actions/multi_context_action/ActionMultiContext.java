package com.github.f442y.dispersion.core.statemachine.TestStateMachine.actions.multi_context_action;

import com.github.f442y.dispersion.core.statemachine.TestStateMachine.actions.simpleNumAddition.dependencies.ActionSimpleNumAdditionDependencies;
import com.github.f442y.dispersion.core.statemachine.exception.ActionException;
import com.github.f442y.dispersion.core.statemachine.state.action.Action;
import lombok.NonNull;

/**
 * Multi context type action.
 * <br>
 * For any action implementing {@link SampleContextInterface}.
 *
 * @param <CONTEXT>
 */
public class ActionMultiContext<CONTEXT extends SampleContextInterface>
        extends Action<CONTEXT, ActionSimpleNumAdditionDependencies> {

    public ActionMultiContext(@NonNull ActionSimpleNumAdditionDependencies actionSimpleNumAdditionDependencies
    ) {
        super(actionSimpleNumAdditionDependencies);
    }

    @NonNull
    @Override
    public CONTEXT action(CONTEXT stateMachineContext, ActionSimpleNumAdditionDependencies actionDependencies
    ) throws ActionException {
        stateMachineContext.sampleContextSpecificMethod();
        return stateMachineContext;
    }
}
