package com.github.f442y.dispersion.core.statemachine.state.transition;

import com.github.f442y.dispersion.core.statemachine.state.StateKey;
import com.github.f442y.dispersion.core.statemachine.StateMachineCallable;
import com.github.f442y.dispersion.core.statemachine.context.StateMachineContext;
import com.github.f442y.dispersion.core.statemachine.state.TransitionAPI;
import com.github.f442y.dispersion.core.statemachine.exception.TransitionException;

/**
 * This is the base interface for transitions.
 * <p>
 * All transitions must implement this interface.
 * <p>
 * This interface defines only the basic generics used by the
 * {@link StateMachineCallable}.
 * <p>
 * A transition must be applicable to a specific statemachine, therefore, this class requires statemachine specific
 * generics:
 * <ul>
 *     <li>The context type of the statemachine</li>
 *     <li>The Enum type that holds the state keys ({@link StateKey})</li>
 * </ul>
 * <p>
 * The transition logic is run in the interface method {@link #transitionCallableTrigger(StateMachineContext)}.
 * <p>
 * An example implementation of this interface is {@link Transition},
 * which adds an extra {@link com.github.f442y.dispersion.core.statemachine.dependency.ActionDependencies} generic which
 * allows
 *
 * @param <CONTEXT>   The context type of the statemachine
 * @param <STATE_KEY> The Enum type that holds the state keys ({@link StateKey})
 * @see Transition
 */
public sealed interface TransitionWithCallableTrigger<CONTEXT extends StateMachineContext,
        STATE_KEY extends Enum<STATE_KEY> & StateKey>
        extends TransitionAPI permits Transition {

    /**
     * <b>Can return ANY {@link StateKey} for in the statemachine</b>
     * <p>
     * The base method for triggering a transition, is only triggered by
     * {@link StateMachineCallable}.
     *
     * @param stateMachineContext The context type of the statemachine
     * @return {@link StateKey} for the next state in the statemachine
     */
    STATE_KEY transitionCallableTrigger(CONTEXT stateMachineContext) throws TransitionException;
}
