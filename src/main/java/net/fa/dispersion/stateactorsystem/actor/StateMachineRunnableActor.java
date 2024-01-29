package net.fa.dispersion.stateactorsystem.actor;

import net.fa.dispersion.stateactorsystem.statemachine.AbstractStateMachine;
import net.fa.dispersion.stateactorsystem.statemachine.context.StateMachineContext;

/**
 * Instantiates a {@link StateMachineActor} and runs the event loop within a {@link Runnable}.
 * <p>
 * Has a builder {@link StateMachineRunnableActorBuilder}.
 * <p>
 * See {@link StateMachineActor} to see the implemented statemachine event loop.
 *
 * @param <CTX> The context type of the statemachine
 * @author Faizaan Ahmed
 * @see StateMachineCallableActor
 * @see StateMachineActor
 */
public final class StateMachineRunnableActor<CTX extends StateMachineContext<CTX>> extends StateMachineActor<CTX,
        Void> implements
        Runnable {

    private StateMachineRunnableActor(StateMachineRunnableActorBuilder<CTX> stateMachineCallableActorBuilder) {
        super(stateMachineCallableActorBuilder.stateMachine);
    }

    @Override
    public void run() {
        super.runStateMachineEventLoop();
    }

    public static class StateMachineRunnableActorBuilder<CTX extends StateMachineContext<CTX>> {
        private AbstractStateMachine<CTX, Void> stateMachine;

        public StateMachineRunnableActor<CTX> stateMachine(AbstractStateMachine<CTX, Void> stateMachine) {
            this.stateMachine = stateMachine;
            return new StateMachineRunnableActor<>(this);
        }
    }
}

