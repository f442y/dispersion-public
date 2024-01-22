package net.fa.dispersion.stateactorsystem.statemachine.context;

/**
 * An interface representing the Context passed through states in a state-machine.
 * <p>
 * The {@link StateMachineContext} is passed through every
 * {@link net.fa.dispersion.stateactorsystem.statemachine.action.Action} and every
 * {@link net.fa.dispersion.stateactorsystem.statemachine.transition.Transition} to enable a context aware statemachine.
 *
 * @author Faizaan Ahmed
 * @see StateMachineContextFactory
 */
public interface StateMachineContext<CTX extends StateMachineContext<CTX>> {}
