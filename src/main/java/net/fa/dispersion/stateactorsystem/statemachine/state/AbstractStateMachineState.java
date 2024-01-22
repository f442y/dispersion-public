package net.fa.dispersion.stateactorsystem.statemachine.state;

import lombok.Getter;
import net.fa.dispersion.stateactorsystem.statemachine.action.Action;
import net.fa.dispersion.stateactorsystem.statemachine.context.StateMachineContext;

@Getter
public abstract non-sealed class AbstractStateMachineState<CTX extends StateMachineContext<CTX>> implements State<CTX> {

    private final Action<CTX> action;

    public AbstractStateMachineState(AbstractStateMachineStateBuilder<?, ?, CTX> stateMachineStateBuilder) {
        this.action = stateMachineStateBuilder.action;
    }

    public abstract static class AbstractStateMachineStateBuilder<B extends AbstractStateMachineStateBuilder<B, T,
            CTX>, T, CTX extends StateMachineContext<CTX>> {
        private Action<CTX> action;

        protected abstract B builderType();

        public B setAction(Action<CTX> action) {
            this.action = action;
            return builderType();
        }

        public abstract T build();
    }
}
