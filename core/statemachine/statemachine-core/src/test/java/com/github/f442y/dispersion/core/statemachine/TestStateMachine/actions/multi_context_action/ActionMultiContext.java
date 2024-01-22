package com.github.f442y.dispersion.core.statemachine.TestStateMachine.actions.multi_context_action;

import com.github.f442y.dispersion.core.statemachine.TestStateMachine.actions.simpleNumAddition.dependencies.ActionSimpleNumAdditionDependencies;
import com.github.f442y.dispersion.core.statemachine.state.action.Action;
import jakarta.annotation.Nonnull;

/**
 * Multi context type action.
 * <br>
 * For any action implementing {@link SampleContextInterface}.
 *
 * @param <CONTEXT>
 */
public class ActionMultiContext<CONTEXT extends SampleContextInterface>
        extends Action<CONTEXT, ActionSimpleNumAdditionDependencies> {

    public ActionMultiContext(ActionSimpleNumAdditionDependencies actionSimpleNumAdditionDependencies
    ) {
        super(actionSimpleNumAdditionDependencies);
    }

    @Nonnull
    @Override
    public CONTEXT action(CONTEXT stateMachineContext, ActionSimpleNumAdditionDependencies actionDependencies) {
        stateMachineContext.sampleContextSpecificMethod();
        return stateMachineContext;
    }
}
