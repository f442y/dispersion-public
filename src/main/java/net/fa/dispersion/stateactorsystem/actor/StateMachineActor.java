package net.fa.dispersion.stateactorsystem.actor;

import lombok.extern.slf4j.Slf4j;
import net.fa.dispersion.stateactorsystem.message.ActorMessage;
import net.fa.dispersion.stateactorsystem.message.ControlActorMessage;
import net.fa.dispersion.stateactorsystem.statemachine.AbstractStateMachine;
import net.fa.dispersion.stateactorsystem.statemachine.StateMachine;
import net.fa.dispersion.stateactorsystem.statemachine.action.Action;
import net.fa.dispersion.stateactorsystem.statemachine.context.StateMachineContext;
import net.fa.dispersion.stateactorsystem.statemachine.context.StateMachineContextFactory;
import net.fa.dispersion.stateactorsystem.statemachine.state.AbstractStateMachineState;
import net.fa.dispersion.stateactorsystem.statemachine.state.State;
import net.fa.dispersion.stateactorsystem.statemachine.state.StateMachineStateConfiguration;
import net.fa.dispersion.stateactorsystem.statemachine.transition.Transition;
import org.springframework.lang.NonNull;

import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

/**
 * A statemachine actor class which contains the event loop for running a statemachine.
 * <p>
 * Creating an instance of this class initializes the context for a statemachine using the
 * {@link StateMachineContextFactory#newInstance()} method.
 * <p>
 * The event loop within the {@link #runStateMachineEventLoop()} function runs through the statemachine
 * {@link StateMachineStateConfiguration}s starting with the initial configuration defined in the
 * {@link #stateMachine}.
 * <p>
 * The {@link AbstractStateMachine} is extracted from the configuration, running the
 * {@link Action} within. When an action has finished, the
 * {@link Transition} is extracted and run to determine
 * the next configuration.
 * <p>
 * This loop continues until a {@link State.EndState} is
 * reached, or the event loop is broken.
 * <p>
 * The {@link #stateMachineContext} is passed through every
 * {@link Action} and every
 * {@link Transition} to enable a context aware statemachine.
 * <p>
 * A {@link StateMachineActor} should not be instantiated directly, hence why it is sealed, it should be instantiated as
 * a {@link Runnable} or {@link Callable} to allow the {@link #runStateMachineEventLoop()}
 * method to be executed on a {@link Thread}.
 * <p>
 * The permitted subclasses {@link StateMachineCallableActor} and {@link StateMachineRunnableActor} have builders
 * ({@link StateMachineCallableActor.StateMachineCallableActorBuilder} and
 * {@link StateMachineRunnableActor.StateMachineRunnableActorBuilder})
 * available to build an executable {@link StateMachineActor}.
 *
 * @param <CTX> The context type of the statemachine, this is passed through every
 *              {@link Action} and every
 *              {@link Transition} so the type should be
 *              fixed throughout the statemachine.
 * @author Faizaan Ahmed
 * @see StateMachineRunnableActor
 * @see StateMachineCallableActor
 * @see Actor
 */
@Slf4j
public sealed class StateMachineActor<CTX extends StateMachineContext<CTX>, RTN> implements Actor<ActorMessage>,
        AutoCloseable permits StateMachineCallableActor, StateMachineRunnableActor, StateMachineSupplierActor {

    private final AtomicBoolean active = new AtomicBoolean(true);
    private final AbstractStateMachine<CTX, RTN> stateMachine;
    public Function<CTX, RTN> returnFunction;
    private CTX stateMachineContext;
    private Semaphore semaphore;

    protected StateMachineActor(AbstractStateMachine<CTX, RTN> stateMachine, Semaphore bufferSemaphore) {
        this(stateMachine);
        this.semaphore = bufferSemaphore;
    }

    protected StateMachineActor(AbstractStateMachine<CTX, RTN> stateMachine) {
        this.stateMachine = stateMachine;
        this.stateMachineContext = stateMachine.stateMachineContextFactory.newInstance();
        if (stateMachine instanceof StateMachine<CTX, RTN> returningStateMachine) {
            this.returnFunction = returningStateMachine.returnFunction;
        } else {
            this.returnFunction = null;
        }
    }

    @Override
    public Actor<ActorMessage> tell(@NonNull final ActorMessage message) {
        if (message instanceof ControlActorMessage controlActorMessage) {
            control(controlActorMessage);
        }
        return this;
    }

    private void control(@NonNull final ControlActorMessage controlActorMessage) {
        switch (controlActorMessage) {
            case KILL -> {
                log.info("Thread: {} | Killing Actor", Thread.currentThread().threadId());
                active.set(false);
            }
            case PAUSE -> log.trace("Thread: {} | Pausing Actor", Thread.currentThread().threadId());
            case START -> {
                log.info("Thread: {} | Starting Actor", Thread.currentThread().threadId());
            }
            default -> log.info("Thread: {} | Unrecognised control command", Thread.currentThread().threadId());
        }
    }

    /**
     * Start running the statemachine event loop beginning with the
     * {@link AbstractStateMachine#initialStateMachineStateConfiguration}.
     */
    public RTN runStateMachineEventLoop() {
        StateMachineStateConfiguration<CTX> stateConfig = stateMachine.initialStateMachineStateConfiguration;
        log.trace("Initial state config assigned, loop not started");
        eventLoop:
        // break the loop and end the callable if the active flag is no longer true
        while (active.get()) {
            log.trace("loop run");
            switch (stateConfig.state()) {
                case State.EndState<CTX> ignored -> {
                    // end state reached, break the loop to end this runnable/callable
                    break eventLoop;
                }
                case AbstractStateMachineState<CTX> stateMachineState ->
                    // extract action from state and run action providing context
                    // returned context becomes new context
                        stateMachineContext = stateMachineState.getAction().runAction(stateMachineContext);
                // todo: consider adding guards and skip checks to state
            }
            // apply transition effect to actor state config, transitioning actor state
            // send context to transition function
            stateConfig = stateConfig
                    .transition()
                    .getNextStateConfiguration(stateMachineContext)
                    .applyToStateConfig(stateConfig);
        }
        // log.info("stopping actor");
        // statemachine/actor event loop end end

        if (returnFunction == null) {
            return null;
        } else {
            return returnFunction.apply(stateMachineContext);
        }
    }

    @Override
    public void close() {
        if (semaphore != null) {
            semaphore.release();
        }
    }
}
