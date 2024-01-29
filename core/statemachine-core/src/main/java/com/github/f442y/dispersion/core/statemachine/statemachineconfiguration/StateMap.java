package com.github.f442y.dispersion.core.statemachine.statemachineconfiguration;

import com.github.f442y.dispersion.core.statemachine.state.StateKey;
import com.github.f442y.dispersion.core.statemachine.context.StateMachineContext;
import com.github.f442y.dispersion.core.statemachine.state.StateWithCallableTriggers;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.EnumMap;
import java.util.Optional;

/**
 * @param <CONTEXT>   The context type of the statemachine.
 * @param <STATE_KEY> The enum in which state keys are defined for the corresponding statemachine
 */
@Slf4j
public final class StateMap<CONTEXT extends StateMachineContext, STATE_KEY extends Enum<STATE_KEY> & StateKey> {
    private final EnumMap<STATE_KEY, StateWithCallableTriggers<CONTEXT, STATE_KEY>> stateEnumMap;
    private final STATE_KEY initialState;
    private final Collection<StateMapBuilderException> exceptions;

    StateMap(StateMapBuilder.StateMapBuilderObject<CONTEXT, STATE_KEY> stateMapBuilderObject
    ) {
        this.stateEnumMap = new EnumMap<>(stateMapBuilderObject.builderValidationEnumStateMap);
        this.initialState = stateMapBuilderObject.initialState;
        this.exceptions = stateMapBuilderObject.exceptions.isEmpty() ? null : stateMapBuilderObject.exceptions;
    }

    public StateWithCallableTriggers<CONTEXT, STATE_KEY> getInitialState() {
        return this.stateEnumMap.get(initialState);
    }

    public StateWithCallableTriggers<CONTEXT, STATE_KEY> getState(STATE_KEY stateKey) {
        return this.stateEnumMap.get(stateKey);
    }

    public EnumMap<STATE_KEY, StateWithCallableTriggers<CONTEXT, STATE_KEY>> stateEnumMap() {
        return this.stateEnumMap;
    }

    public Optional<Collection<StateMapBuilderException>> exceptions() {
        return Optional.ofNullable(exceptions);
    }
}
