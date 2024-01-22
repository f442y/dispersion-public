package net.fa.dispersion.stateactorsystem.statemachine.action;

import net.fa.dispersion.stateactorsystem.message.ActorMessage;
import net.fa.dispersion.stateactorsystem.statemachine.context.StateMachineContext;

@FunctionalInterface
public non-sealed interface WaitingAction<CTX extends StateMachineContext<CTX>> extends Action<CTX> {
    CTX receiveMessage(ActorMessage msg, CTX stateMachineContext);
}
