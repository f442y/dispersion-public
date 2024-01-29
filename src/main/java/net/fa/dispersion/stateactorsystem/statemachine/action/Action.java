package net.fa.dispersion.stateactorsystem.statemachine.action;

import net.fa.dispersion.stateactorsystem.statemachine.context.StateMachineContext;

@FunctionalInterface
public interface Action<CTX extends StateMachineContext<CTX>> {
    CTX runAction(CTX stateMachineContext);
}
