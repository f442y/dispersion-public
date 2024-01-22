package net.fa.dispersion.stateactorsystem.statemachine.transition;

import net.fa.dispersion.stateactorsystem.statemachine.context.StateMachineContext;
import net.fa.dispersion.stateactorsystem.statemachine.state.State;
import net.fa.dispersion.stateactorsystem.statemachine.state.StateMachineStateConfiguration;

/**
 * Transition interface for a statemachine actor.
 * <p>
 * All transitions must implement this interface as the
 * {@link net.fa.dispersion.stateactorsystem.actors.StateMachineActor<CTX>} event loop will use the
 * {@code `applyToStateConfig()`} function to transition the state of the statemachine.
 * <p>
 * static methods for common usages are available within the interface.
 *
 * @param <CTX> The context type of the statemachine
 * @author Faizaan Ahmed
 */
@FunctionalInterface
public interface Transition<CTX extends StateMachineContext<CTX>> {

    /**
     * @param nextStateMachineStateConfiguration The state the statemachine should become.
     * @param <CTX>                              The context type of the statemachine
     * @return A Transition function which ignores/drops the original state configuration it is applied to and
     * returns the state configuration passed into this function.
     * <p>
     * Used to move the statemachine to a different state.
     */
    static <CTX extends StateMachineContext<CTX>> Transition<CTX> Become(StateMachineStateConfiguration<CTX> nextStateMachineStateConfiguration) {
        return _ -> nextStateMachineStateConfiguration;
    }

    /**
     * @param <CTX> The context type of the statemachine
     * @return A Transition function which returns the same state configuration that it is applied to.
     * <p>
     * Used to bring the statemachine back to the beginning of the same state.
     */
    static <CTX extends StateMachineContext<CTX>> Transition<CTX> Stay() {
        return originalStateConfiguration -> originalStateConfiguration;
    }

    /**
     * @param <CTX> The context type of the statemachine
     * @return A utility Transition which returns a predefined 'End'/'Kill' state which is used to end the statemachine.
     * <p>
     * The predefined state configuration
     * {@link net.fa.dispersion.stateactorsystem.statemachine.state.StateMachineStateConfiguration.EndStateMachineStateConfiguration}
     * contains a state {@link State.EndState <CTX>}
     * recognised by the event loop within the {@link net.fa.dispersion.stateactorsystem.actors.StateMachineActor<CTX>}
     * to end/kill the statemachine.
     * <p>
     * Used to end/kill the statemachine.
     */
    static <CTX extends StateMachineContext<CTX>> Transition<CTX> End() {
        return _ -> new StateMachineStateConfiguration.EndStateMachineStateConfiguration<>();
    }

    /**
     * Used by the {@link net.fa.dispersion.stateactorsystem.actors.StateMachineActor} event loop to apply this
     * {@link Transition} to the current {@link StateMachineStateConfiguration} to transition to the next state
     * configuration.
     *
     * @param originalStateMachineStateConfiguration The current state configuration of the statemachine.
     * @return The state configuration the statemachine should become.
     */
    StateMachineStateConfiguration<CTX> applyToStateConfig(StateMachineStateConfiguration<CTX> originalStateMachineStateConfiguration);
}
