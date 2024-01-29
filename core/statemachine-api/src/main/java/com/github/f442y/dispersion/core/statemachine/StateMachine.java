package com.github.f442y.dispersion.core.statemachine;

import com.github.f442y.dispersion.core.statemachine.context.StateMachineContext;
import com.github.f442y.dispersion.core.statemachine.state.StateKey;

import java.util.UUID;

/**
 * Base interface for a StateMachine.
 * <p>
 * Exposes methods used to access current information about the statemachine.
 *
 * @param <CONTEXT> The context type of the statemachine
 * @author Faizaan Ahmed
 */
public interface StateMachine<CONTEXT extends StateMachineContext, STATE_KEY extends Enum<STATE_KEY> & StateKey> {
    /**
     * Every statemachine will have its own UUID, even nested statemachines have their own UUID.
     */
    UUID uuid();
}
