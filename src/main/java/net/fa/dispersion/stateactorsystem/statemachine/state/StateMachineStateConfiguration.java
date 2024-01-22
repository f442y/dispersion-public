package net.fa.dispersion.stateactorsystem.statemachine.state;

import net.fa.dispersion.stateactorsystem.statemachine.context.StateMachineContext;
import net.fa.dispersion.stateactorsystem.statemachine.transition.StateMachineTransition;

public interface StateMachineStateConfiguration<CTX extends StateMachineContext<CTX>> {
    State<CTX> state();

    StateMachineTransition<CTX> transition();

    class EndStateMachineStateConfiguration<CTX extends StateMachineContext<CTX>> implements
            StateMachineStateConfiguration<CTX> {
        @Override
        public State<CTX> state() {
            return new State.EndState<>();
        }

        @Override
        public StateMachineTransition<CTX> transition() {
            return StateMachineTransition.end();
        }
    }
}
