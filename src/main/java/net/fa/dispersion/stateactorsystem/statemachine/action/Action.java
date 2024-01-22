package net.fa.dispersion.stateactorsystem.statemachine.action;

import net.fa.dispersion.stateactorsystem.statemachine.context.StateMachineContext;

public sealed interface Action<CTX extends StateMachineContext<CTX>> permits ProgressingAction,
        WaitingAction {

}
