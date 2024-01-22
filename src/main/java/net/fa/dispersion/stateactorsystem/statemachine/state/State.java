package net.fa.dispersion.stateactorsystem.statemachine.state;

import net.fa.dispersion.stateactorsystem.statemachine.context.StateMachineContext;

public sealed interface State<CTX extends StateMachineContext<CTX>> permits AbstractStateMachineState,
        State.EndState {
    final class EndState<CTX extends StateMachineContext<CTX>> implements State<CTX> {}
}
