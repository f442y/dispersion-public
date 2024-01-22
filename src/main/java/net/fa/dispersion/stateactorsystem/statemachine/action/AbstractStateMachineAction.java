package net.fa.dispersion.stateactorsystem.statemachine.action;

import net.fa.dispersion.stateactorsystem.statemachine.context.StateMachineContext;

import java.util.function.Function;

public abstract class AbstractStateMachineAction<CTX extends StateMachineContext<CTX>> {
    private Function<CTX, CTX> action;

    public CTX runAction(CTX stateMachineContext) {
        return action.apply(stateMachineContext);
    }

    public static class StateMachineActionBuilder {

    }
}
