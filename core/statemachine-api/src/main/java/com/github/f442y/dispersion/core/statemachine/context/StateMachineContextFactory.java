package com.github.f442y.dispersion.core.statemachine.context;

/**
 * Every statemachine uses a factory of this implementation to create a context object for itself.
 *
 * @author Faizaan Ahmed
 * @see StateMachineContext
 */
@FunctionalInterface
public interface StateMachineContextFactory<CONTEXT extends StateMachineContext> {
    CONTEXT newInstance();
}
