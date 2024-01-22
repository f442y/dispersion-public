package net.fa.dispersion.stateactorsystem.statemachine.context;

/**
 * Every {@link net.fa.dispersion.stateactorsystem.actors.StateMachineActor} uses a factory of this implementation to
 * create a context object for itself.
 *
 * @author Faizaan Ahmed
 * @see StateMachineContext
 */
public interface StateMachineContextFactory<CTX extends StateMachineContext<CTX>> {

    CTX newInstance();
}