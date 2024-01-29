package com.github.f442y.dispersion.core.statemachine.state.transition;


import com.github.f442y.dispersion.core.statemachine.state.StateKey;
import com.github.f442y.dispersion.core.statemachine.StateMachineCallable;
import com.github.f442y.dispersion.core.statemachine.context.StateMachineContext;
import com.github.f442y.dispersion.core.statemachine.state.TransitionAPI;
import com.github.f442y.dispersion.core.statemachine.exception.TransitionException;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This is the parent abstract class to define a Transition.
 * <p>
 * All transitions with all possible future states predefined must extend this class.
 * <p>
 * A transition must be applicable to a specific statemachine, therefore, this class requires statemachine specific
 * generics:
 * <ul>
 *     <li>The context type of the statemachine</li>
 *     <li>The Enum type that holds the state keys ({@link StateKey})</li>
 * </ul>
 * <p>
 * The transition logic is run in the abstract method {@link #resolvePermittedTransition(StateMachineContext)}.
 * <p>
 * The logic resolves to a {@link TransitionStateKey} enum which is unique to this transition only. From this
 * {@link TransitionStateKey}, a {@link StateKey} is extracted which can only be a key for the same statemachine.
 * <p>
 * Every transition should have a unique {@link Enum} extending {@link TransitionStateKey}, this is to define every
 * possible transition for a state.
 * <p>
 * This abstract class does not prevent transitioning to the same state.
 *
 * @param <CONTEXT>              The context type of the statemachine
 * @param <STATE_KEY>            The Enum type that holds the state keys ({@link StateKey})
 * @param <TRANSITION_STATE_KEY> The Enum type that holds the {@link Transition} keys ({@link TransitionStateKey})
 * @author Faizaan Ahmed
 * @see TransitionStateKey
 * @see StateKey
 */
public abstract non-sealed class Transition<CONTEXT extends StateMachineContext,
        STATE_KEY extends Enum<STATE_KEY> & StateKey,
        TRANSITION_STATE_KEY extends Enum<TRANSITION_STATE_KEY> & TransitionStateKey<STATE_KEY>>
        implements TransitionWithCallableTrigger<CONTEXT, STATE_KEY>, TransitionAPI {

    private final EnumSet<TRANSITION_STATE_KEY> tskEnumSet;

    /**
     * @param tskClass The class of the {@link Enum} implementing {@link TransitionStateKey}
     */
    public Transition(Class<TRANSITION_STATE_KEY> tskClass) {
        this.tskEnumSet = EnumSet.allOf(tskClass);
    }

    /**
     * Method called to determine the next state of the statemachine.
     * <p>
     * If next state can be any of several, {@link StateMachineContext} can be used to determine next state.
     *
     * @param stateMachineContext The context type of the statemachine
     * @return {@link TransitionStateKey} which resolves to the next state
     */
    public abstract TRANSITION_STATE_KEY resolvePermittedTransition(CONTEXT stateMachineContext
    ) throws TransitionException;

    /**
     * Gets the keys ({@link StateKey}) that this {@link Transition} can resolve to.
     * <p>
     * This is used to build the adjacency list for state validation.
     * <p>
     * {@link StateKey}s are extracted from the EnumSet of {@link TransitionStateKey}s defined in
     * {@link #tskEnumSet}.
     *
     * @see com.github.f442y.dispersion.core.statemachine.statemachineconfiguration.StateMap
     */
    public final Set<STATE_KEY> getPossibleFutureStateKeys() {
        return this.tskEnumSet.stream().map(TransitionStateKey::stateKey).collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     * <p>
     * Overridden to run transition logic in {@link #resolvePermittedTransition(StateMachineContext)} which can only
     * return a {@link TransitionStateKey} and extract the {@link StateKey} for the
     * {@link StateMachineCallable}
     */
    @Override
    public final STATE_KEY transitionCallableTrigger(CONTEXT stateMachineContext) throws TransitionException {
        return resolvePermittedTransition(stateMachineContext).stateKey();
    }
}
