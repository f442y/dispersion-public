package com.github.f442y.dispersion.core.statemachine.statemachineconfiguration;

import com.github.f442y.dispersion.core.application.ApplicationConfig;
import com.github.f442y.dispersion.core.application.BootExceptions;
import com.github.f442y.dispersion.core.statemachine.state.StateKey;
import com.github.f442y.dispersion.core.statemachine.StateMachine;
import com.github.f442y.dispersion.core.statemachine.StateMachineConfigurationAPI;
import com.github.f442y.dispersion.core.statemachine.context.StateMachineContext;
import com.github.f442y.dispersion.core.statemachine.context.StateMachineContextFactory;
import com.github.f442y.dispersion.core.statemachine.events.StateMachineEventLogAPI;
import com.github.f442y.dispersion.core.statemachine.events.StateMachineEventMonitorAPI;
import com.github.f442y.dispersion.core.statemachine.events.FinishEvent;
import com.github.f442y.dispersion.core.statemachine.events.InputEvent;
import com.github.f442y.dispersion.core.statemachine.events.OutputEvent;
import com.github.f442y.dispersion.core.statemachine.events.StartEvent;
import com.github.f442y.dispersion.core.statemachine.events.StateMachineEvent;
import com.github.f442y.dispersion.core.statemachine.exception.StateMachineException;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

/**
 * @param <CONTEXT> The context type of the statemachine. (Must implement {@link StateMachineContext})
 * @author Faizaan Ahmed
 */
@Slf4j
public non-sealed abstract class StateMachineConfiguration<CONTEXT extends StateMachineContext,
        STATE_KEY extends Enum<STATE_KEY> & StateKey, INPUT, OUTPUT>
        implements StateMachineConfigurationWithCallableTriggers<CONTEXT, STATE_KEY, INPUT, OUTPUT>,
        StateMachineConfigurationAPI {

    private final StateMap<CONTEXT, STATE_KEY> stateMap;
    private StateMachineEventMonitorAPI<StateMachineEvent> stateMachineEventMonitorAPI;

    public StateMachineConfiguration(StateMap<CONTEXT, STATE_KEY> stateMap) {
        this.stateMap = stateMap;
        // report validation checks
    }

    //
    public StateMachineConfiguration(BootExceptions bootExceptions, StateMap<CONTEXT, STATE_KEY> stateMap) {
        stateMap.exceptions().ifPresent(bootExceptions::addAllBootExceptions);
        this.stateMap = stateMap;
    }

    public StateMachineConfiguration(ApplicationConfig applicationConfig,
                                     StateMachineEventMonitorAPI<StateMachineEvent> stateMachineEventMonitorAPI,
                                     StateMap<CONTEXT, STATE_KEY> stateMap
    ) {
        stateMap.exceptions().ifPresent(applicationConfig::addAllBootExceptions);
        this.stateMap = stateMap;
        this.stateMachineEventMonitorAPI = stateMachineEventMonitorAPI;
    }

    @Override
    public final Optional<StateMachineEventMonitorAPI<StateMachineEvent>> getStateMachineEventMonitor() {
        return Optional.ofNullable(this.stateMachineEventMonitorAPI);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final StateMap<CONTEXT, STATE_KEY> getStateMap() {
        return this.stateMap;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final StateMachineContextFactory<CONTEXT> stateMachineContextFactoryTrigger(
            StateMachine<CONTEXT, STATE_KEY> stateMachine, StateMachineEventLogAPI<StateMachineEvent> stateMachineEventLogAPI
    ) {
        StartEvent startEvent = new StartEvent(LocalDateTime.now(ZoneOffset.UTC));
        // save event to event log
        stateMachineEventLogAPI.addStateMachineEventToLog(startEvent);
        return this.stateMachineContextFactory();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Optional<InputFunction<CONTEXT, INPUT>> inputFunctionTrigger(
            StateMachine<CONTEXT, STATE_KEY> stateMachine, StateMachineEventLogAPI<StateMachineEvent> stateMachineEventLogAPI
    ) throws StateMachineException {
        InputEvent inputEvent = new InputEvent(LocalDateTime.now(ZoneOffset.UTC));
        // save event to event log
        stateMachineEventLogAPI.addStateMachineEventToLog(inputEvent);
        return Optional.ofNullable(this.inputFunction());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Optional<OutputFunction<CONTEXT, OUTPUT>> outputFunctionTrigger(
            StateMachine<CONTEXT, STATE_KEY> stateMachine, StateMachineEventLogAPI<StateMachineEvent> stateMachineEventLogAPI
    ) throws StateMachineException {
        OutputEvent outputEvent = new OutputEvent(LocalDateTime.now(ZoneOffset.UTC));
        // save event to event log
        stateMachineEventLogAPI.addStateMachineEventToLog(outputEvent);
        return Optional.ofNullable(this.outputFunction());
    }

    @Override
    public final void stateMachineExceptionTrigger(StateMachine<CONTEXT, STATE_KEY> stateMachine,
                                                   StateMachineEventLogAPI<StateMachineEvent> stateMachineEventLogAPI,
                                                   StateMachineException stateMachineException
    ) {}

    @Override
    public final void stateMachineFinishTrigger(StateMachine<CONTEXT, STATE_KEY> stateMachine,
                                                StateMachineEventLogAPI<StateMachineEvent> stateMachineEventLogAPI
    ) {
        FinishEvent finishEvent = new FinishEvent(LocalDateTime.now(ZoneOffset.UTC));
        // save event to event log
        stateMachineEventLogAPI.addStateMachineEventToLog(finishEvent);
    }

    public abstract StateMachineContextFactory<CONTEXT> stateMachineContextFactory();

    public abstract InputFunction<CONTEXT, INPUT> inputFunction() throws StateMachineException;

    public abstract OutputFunction<CONTEXT, OUTPUT> outputFunction() throws StateMachineException;
}
