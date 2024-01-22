package net.fa.dispersion.stateactorsystem.statemachine.action;

import net.fa.dispersion.stateactorsystem.statemachine.context.StateMachineContext;

@FunctionalInterface
public non-sealed interface ProgressingAction<CTX extends StateMachineContext<CTX>> extends Action<CTX> {
    CTX progress(CTX stateMachineContext);
}
