package com.github.f442y.dispersion.core.statemachine.state.transition;

import com.github.f442y.dispersion.core.statemachine.context.StateMachineContext;
import com.github.f442y.dispersion.core.statemachine.state.StateKey;

/**
 * This is the base interface for mapping a {@link StateKey} to the constants in the {@link Enum} used in a
 * {@link Transition}.
 * <p>
 * The {@link Transition} abstract class requires every possible state transition to be predefined in an {@link Enum}.
 * <br>
 * (The {@link Transition#resolvePermittedTransition(StateMachineContext)} method resolves to one of these Enum
 * constants).
 * <p>
 * Each {@link Enum} should resolve to a specific State ({@link StateKey}) within the same statemachine.
 * <br>
 * (Resolved/Mapped via the {@link #stateKey()} method).
 *
 * @param <STATE_KEY> The Enum type that holds the state keys ({@link StateKey}), used to ensure that only states in
 *                    within the same statemachine can be mapped.
 * @see Transition
 */
public interface TransitionStateKey<STATE_KEY extends Enum<STATE_KEY> & StateKey> {
    /**
     * Mapped to a {@link StateKey}.
     *
     * @return {@link StateKey} within the same statemachine.
     */
    STATE_KEY stateKey();
}
