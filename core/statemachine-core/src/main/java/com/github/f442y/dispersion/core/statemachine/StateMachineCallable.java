package com.github.f442y.dispersion.core.statemachine;

import com.github.f442y.dispersion.core.application.PermitConfig;
import com.github.f442y.dispersion.core.statemachine.context.StateMachineContext;
import com.github.f442y.dispersion.core.statemachine.events.StateMachineEventLog;
import com.github.f442y.dispersion.core.statemachine.exception.ActionException;
import com.github.f442y.dispersion.core.statemachine.exception.StateMachineException;
import com.github.f442y.dispersion.core.statemachine.exception.TransitionException;
import com.github.f442y.dispersion.core.statemachine.state.StateKey;
import com.github.f442y.dispersion.core.statemachine.state.StateWithCallableTriggers;
import com.github.f442y.dispersion.core.statemachine.statemachineconfiguration.InputFunction;
import com.github.f442y.dispersion.core.statemachine.statemachineconfiguration.OutputFunction;
import com.github.f442y.dispersion.core.statemachine.statemachineconfiguration.StateMachineConfigurationWithCallableTriggers;

import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @param <CONTEXT>   The context type of the statemachine
 * @param <STATE_KEY>
 * @param <INPUT>
 * @param <OUTPUT>
 */
public final class StateMachineCallable<CONTEXT extends StateMachineContext,
        STATE_KEY extends Enum<STATE_KEY> & StateKey, INPUT, OUTPUT>
        implements StateMachine<CONTEXT, STATE_KEY>, Callable<OUTPUT> {
    private final UUID uuid;
    private final StateMachineConfigurationWithCallableTriggers<CONTEXT, STATE_KEY, INPUT, OUTPUT>
            stateMachineConfiguration;
    private final Semaphore bufferSemaphore;
    private final PermitConfig permitConfig;
    private final AtomicBoolean active = new AtomicBoolean(true);
    private final INPUT input;

    private StateMachineCallable(
            StateMachineCallableBuilder<CONTEXT, STATE_KEY, INPUT, OUTPUT> stateMachineCallableBuilder
    ) {
        this.uuid = stateMachineCallableBuilder.uuid;
        this.stateMachineConfiguration = stateMachineCallableBuilder.stateMachineConfiguration;
        this.input = stateMachineCallableBuilder.input;
        this.bufferSemaphore = stateMachineCallableBuilder.bufferSemaphore;
        this.permitConfig = stateMachineCallableBuilder.permitConfig;
    }

    @Override
    public OUTPUT call() throws ExecutionException {
        // *Thread/Statemachine Setup*
        // (run only once before the statemachine loop starts)
        // initialize a statemachine event log
        StateMachineEventLog stateMachineEventLog =
                new StateMachineEventLog(stateMachineConfiguration, this);
        // set initial state for statemachine, extracted from statemachine configuration
        StateWithCallableTriggers<CONTEXT, STATE_KEY> currentState =
                stateMachineConfiguration.getStateMap().getInitialState();
        // extract context factory from statemachine configuration use it to create a new instance
        CONTEXT stateMachineContext = stateMachineConfiguration
                .stateMachineContextFactoryTrigger(this, stateMachineEventLog)
                .newInstance();

        try {
            // *Input*
            // pre statemachine loop function run, used for processing input
            // (input is no longer directly accessible after this function run, should be added to context if needed)
            InputFunction<CONTEXT, INPUT> inputFunction =
                    stateMachineConfiguration.inputFunctionTrigger(this, stateMachineEventLog).orElse(null);
            if (inputFunction != null) {
                stateMachineContext = inputFunction.apply(stateMachineContext, input);
            }
            // StateMachine Loop
            stateMachineLoop:
            // break the loop and end the callable if the active flag is false
            while (active.get()) {
                // Action
                // run the action in the state configuration
                stateMachineContext = currentState
                        // get the action
                        .stateMachineGetActionCallableTrigger(this, stateMachineEventLog)
                        // run the action
                        .actionCallableTrigger(stateMachineContext);

                // *Transition*
                // apply the transition in the current state configuration
                // resolves to the next state configuration, which is assigned to the state configuration field
                STATE_KEY stateKey = currentState
                        // get the transition
                        .stateMachineGetTransitionCallableTrigger(this, stateMachineEventLog)
                        // run the transition
                        .transitionCallableTrigger(stateMachineContext);
                currentState = stateMachineConfiguration.getStateMap().getState(stateKey);

                // *End State*
                // if the next state configuration is an end state, break the loop, ending the statemachine
                if (currentState instanceof StateWithCallableTriggers.EndStateCallableTriggers<?, ?>) {
                    break stateMachineLoop;
                }
            }

            // *Output*
            // post statemachine loop function run, used for processing context to make an output
            OutputFunction<CONTEXT, OUTPUT> outputFunction =
                    stateMachineConfiguration.outputFunctionTrigger(this, stateMachineEventLog).orElse(null);
            if (outputFunction != null) {
                return outputFunction.apply(stateMachineContext);
            } else {
                return null;
            }
        } catch (StateMachineException e) {
            // StateMachine Exception Handling
//            log.error(e.getMessage());
            switch (e) {
                case ActionException actionException -> {}
                case TransitionException transitionException -> {}
            }
            throw new ExecutionException(e);
        } finally {
            // signal death of statemachine
            stateMachineConfiguration.stateMachineFinishTrigger(this, stateMachineEventLog);
            // Thread Permit Handling
            if (bufferSemaphore != null) {
                // release any permits this statemachine is holding
                permitConfig.releaseThreadPermits(bufferSemaphore);
            }
        }
    }

    @Override
    public UUID uuid() {
        return uuid;
    }

    // Builder
    public static class StateMachineCallableBuilder<CONTEXT extends StateMachineContext,
            STATE_KEY extends Enum<STATE_KEY> & StateKey, INPUT, OUTPUT> {
        private UUID uuid;
        private StateMachineConfigurationWithCallableTriggers<CONTEXT, STATE_KEY, INPUT, OUTPUT>
                stateMachineConfiguration;
        private INPUT input;
        private Semaphore bufferSemaphore;
        private PermitConfig permitConfig;

        public StateMachineCallableBuilder<CONTEXT, STATE_KEY, INPUT, OUTPUT> buffered(Semaphore bufferSemaphore) {
            this.bufferSemaphore = bufferSemaphore;
            return this;
        }

        public StateMachineCallableBuilder<CONTEXT, STATE_KEY, INPUT, OUTPUT> permitConfig(PermitConfig permitConfig) {
            this.permitConfig = permitConfig;
            return this;
        }

        public StateMachineCallableBuilder<CONTEXT, STATE_KEY, INPUT, OUTPUT> input(INPUT input) {
            this.input = input;
            return this;
        }

        public StateMachineCallable<CONTEXT, STATE_KEY, INPUT, OUTPUT> stateMachine(
                StateMachineConfigurationWithCallableTriggers<CONTEXT, STATE_KEY, INPUT, OUTPUT> stateMachineConfiguration
        ) {
            this.stateMachineConfiguration = stateMachineConfiguration;
            this.uuid = UUID.randomUUID();
            return new StateMachineCallable<>(this);
        }
    }
}
