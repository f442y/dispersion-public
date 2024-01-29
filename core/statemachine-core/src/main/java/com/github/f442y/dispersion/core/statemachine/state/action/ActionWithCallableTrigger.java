package com.github.f442y.dispersion.core.statemachine.state.action;

import com.github.f442y.dispersion.core.statemachine.state.ActionAPI;
import com.github.f442y.dispersion.core.statemachine.StateMachineCallable;
import com.github.f442y.dispersion.core.statemachine.context.StateMachineContext;
import com.github.f442y.dispersion.core.statemachine.exception.ActionException;
import jakarta.annotation.Nonnull;

/**
 * This is the base interface for actions.
 * <p>
 * All actions must implement this interface.
 * <p>
 * This interface defines only the basic generics used by the
 * {@link StateMachineCallable}.
 * <p>
 * An action must be applicable to a specific {@link StateMachineContext},
 * <p>
 * The action logic is run in the interface method {@link #actionCallableTrigger(StateMachineContext)}.
 * <p>
 * An example implementation of this interface is {@link Action},
 * which adds an extra {@link com.github.f442y.dispersion.core.statemachine.dependency.ActionDependencies} generic which
 * allows subclasses to predefine all the required dependencies using an
 * {@link com.github.f442y.dispersion.core.statemachine.dependency.ActionDependencies} object which is passed to the
 * action
 * along with the statemachine context ({@link StateMachineContext}).
 *
 * @param <CONTEXT> The context type of the statemachine
 * @see Action
 */
@Nonnull
public sealed interface ActionWithCallableTrigger<CONTEXT extends StateMachineContext> extends ActionAPI permits Action {
    /**
     * The base method for triggering an action, is only triggered by
     * {@link StateMachineCallable}.
     *
     * @param stateMachineContext The context of the statemachine (mutable)
     * @return The context of the statemachine after being read or mutated by the action
     */
    CONTEXT actionCallableTrigger(CONTEXT stateMachineContext) throws ActionException;
}
