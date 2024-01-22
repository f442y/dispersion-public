package net.fa.dispersion.stateactorsystem.statemachine.state;

import lombok.Getter;
import net.fa.dispersion.stateactorsystem.statemachine.context.StateMachineContext;


@Getter
public class StateMachineState<CTX extends StateMachineContext<CTX>> extends AbstractStateMachineState<CTX> {
    private StateMachineState(StateMachineStateBuilder<CTX> stateMachineStateBuilder) {
        super(stateMachineStateBuilder);
    }

    public static class StateMachineStateBuilder<CTX extends StateMachineContext<CTX>> extends
            AbstractStateMachineStateBuilder<StateMachineStateBuilder<CTX>, StateMachineState<CTX>, CTX> {

        @Override
        protected StateMachineStateBuilder<CTX> builderType() {
            return this;
        }

        public StateMachineState<CTX> build() {
            return new StateMachineState<>(this);
        }
    }
}
