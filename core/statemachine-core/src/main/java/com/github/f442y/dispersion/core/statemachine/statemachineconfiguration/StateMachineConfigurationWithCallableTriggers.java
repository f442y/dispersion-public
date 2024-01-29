package com.github.f442y.dispersion.core.statemachine.statemachineconfiguration;

import com.github.f442y.dispersion.core.statemachine.state.StateKey;
import com.github.f442y.dispersion.core.statemachine.StateMachine;
import com.github.f442y.dispersion.core.statemachine.StateMachineCallable;
import com.github.f442y.dispersion.core.statemachine.StateMachineConfigurationAPI;
import com.github.f442y.dispersion.core.statemachine.context.StateMachineContext;
import com.github.f442y.dispersion.core.statemachine.context.StateMachineContextFactory;
import com.github.f442y.dispersion.core.statemachine.events.StateMachineEventLogAPI;
import com.github.f442y.dispersion.core.statemachine.events.StateMachineEventMonitorAPI;
import com.github.f442y.dispersion.core.statemachine.events.StateMachineEvent;
import com.github.f442y.dispersion.core.statemachine.exception.StateMachineException;

import java.util.Optional;
import java.util.function.Function;

/**
 * Base State Machine Configuration interface.
 * <p>
 * All State Machine Configurations must implement this interface.
 * <p>
 * This interface defines only the basic generics used by the
 * {@link StateMachineCallable}.
 * <p>
 *
 * @param <CONTEXT> The context type of the statemachine. (Must implement {@link StateMachineContext})
 * @author Faizaan Ahmed
 */
public sealed interface StateMachineConfigurationWithCallableTriggers<CONTEXT extends StateMachineContext,
        STATE_KEY extends Enum<STATE_KEY> & StateKey, INPUT, OUTPUT>
        extends StateMachineConfigurationAPI permits StateMachineConfiguration {
    /**
     * This method is only called in {@link StateMachineCallable}
     * to get the state map ({@link StateMap}).
     */
    StateMap<CONTEXT, STATE_KEY> getStateMap();

    /**
     * This method is only called in {@link StateMachineCallable}
     * to get the {@link StateMachineContextFactory} and signal the start of the statemachine.
     */
    StateMachineContextFactory<CONTEXT> stateMachineContextFactoryTrigger(StateMachine<CONTEXT, STATE_KEY> stateMachine,
                                                                          StateMachineEventLogAPI<StateMachineEvent> stateMachineEventLogAPI
    );

    /**
     * This method is only called in {@link StateMachineCallable}
     * to get the input function ({@link InputFunction}).
     */
    Optional<InputFunction<CONTEXT, INPUT>> inputFunctionTrigger(StateMachine<CONTEXT, STATE_KEY> stateMachine,
                                                                 StateMachineEventLogAPI<StateMachineEvent> stateMachineEventLogAPI
    ) throws StateMachineException;

    /**
     * This method is only called in {@link StateMachineCallable}
     * to get the output function ({@link Function}).
     */
    Optional<OutputFunction<CONTEXT, OUTPUT>> outputFunctionTrigger(StateMachine<CONTEXT, STATE_KEY> stateMachine,
                                                                    StateMachineEventLogAPI<StateMachineEvent> stateMachineEventLogAPI
    ) throws StateMachineException;

    /**
     * This method is only called in {@link StateMachineCallable} to mark an exception.
     */
    void stateMachineExceptionTrigger(StateMachine<CONTEXT, STATE_KEY> stateMachine,
                                      StateMachineEventLogAPI<StateMachineEvent> stateMachineEventLogAPI,
                                      StateMachineException stateMachineException
    );

    /**
     * This method is only called in {@link StateMachineCallable} to signal the death of the statemachine.
     */
    void stateMachineFinishTrigger(StateMachine<CONTEXT, STATE_KEY> stateMachine,
                                   StateMachineEventLogAPI<StateMachineEvent> stateMachineEventLogAPI
    );

    Optional<StateMachineEventMonitorAPI<StateMachineEvent>> getStateMachineEventMonitor();
}
