package net.fa.dispersion.stateactorsystem.actor;

import net.fa.dispersion.stateactorsystem.statemachine.AbstractStateMachine;
import net.fa.dispersion.stateactorsystem.statemachine.context.StateMachineContext;

import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;
import java.util.function.Supplier;

/**
 * Instantiates a {@link StateMachineActor} and runs the event loop within a {@link Callable}.
 * <p>
 * Has a builder {@link StateMachineSupplierActor}.
 * <p>
 * See {@link StateMachineActor} to see the implemented statemachine event loop.
 *
 * @param <CTX>  The context type of the statemachine
 * @param <RTN>> The return type of the callable
 * @author Faizaan Ahmed
 * @see StateMachineRunnableActor
 * @see StateMachineActor
 */
public final class StateMachineSupplierActor<CTX extends StateMachineContext<CTX>, RTN> extends StateMachineActor<CTX
        , RTN> implements
        Supplier<RTN> {

    private StateMachineSupplierActor(StateMachineSupplierActorBuilder<CTX, RTN> stateMachineCallableActorBuilder) {
        super(stateMachineCallableActorBuilder.stateMachine, stateMachineCallableActorBuilder.bufferSemaphore);
    }

    @Override
    public RTN get() {
        return super.runStateMachineEventLoop();
    }

    public static class StateMachineSupplierActorBuilder<CTX extends StateMachineContext<CTX>, RTN> {
        private AbstractStateMachine<CTX, RTN> stateMachine;
        private Semaphore bufferSemaphore;

        public StateMachineSupplierActorBuilder<CTX, RTN> buffered(Semaphore bufferSemaphore) {
            this.bufferSemaphore = bufferSemaphore;
            return this;
        }

        public StateMachineSupplierActor<CTX, RTN> stateMachine(AbstractStateMachine<CTX, RTN> stateMachine) {
            this.stateMachine = stateMachine;
            return new StateMachineSupplierActor<>(this);
        }
    }
}
