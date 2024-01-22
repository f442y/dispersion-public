package net.fa.dispersion.stateactorsystem.actors;

import net.fa.dispersion.stateactorsystem.statemachine.AbstractStateMachine;
import net.fa.dispersion.stateactorsystem.statemachine.context.StateMachineContext;

import java.util.concurrent.Callable;

/**
 * Instantiates a {@link StateMachineActor} and runs the event loop within a {@link Callable}.
 * <p>
 * Has a builder {@link StateMachineCallableActorBuilder}.
 * <p>
 * See {@link StateMachineActor} to see the implemented statemachine event loop.
 *
 * @param <CTX> The context type of the statemachine
 * @param <RTN>>   The return type of the callable
 * @author Faizaan Ahmed
 * @see StateMachineRunnableActor
 * @see StateMachineActor
 */
public final class StateMachineCallableActor<CTX extends StateMachineContext<CTX>, RTN> extends StateMachineActor<CTX
        , RTN> implements
        Callable<RTN> {

    private StateMachineCallableActor(StateMachineCallableActorBuilder<CTX, RTN> stateMachineCallableActorBuilder) {
        super(stateMachineCallableActorBuilder.stateMachine);
    }

    @Override
    public RTN call() {
        return super.runStateMachineEventLoop();
    }

    public static class StateMachineCallableActorBuilder<CTX extends StateMachineContext<CTX>, RTN> {
        private AbstractStateMachine<CTX, RTN> stateMachine;

        public StateMachineCallableActor<CTX, RTN> stateMachine(AbstractStateMachine<CTX, RTN> stateMachine) {
            this.stateMachine = stateMachine;
            return new StateMachineCallableActor<>(this);
        }
    }
}
