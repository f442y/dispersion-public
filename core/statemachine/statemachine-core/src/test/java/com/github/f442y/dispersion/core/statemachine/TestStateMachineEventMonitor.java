package com.github.f442y.dispersion.core.statemachine;

import com.github.f442y.dispersion.core.statemachine.events.StateMachineEvent;
import com.github.f442y.dispersion.core.statemachine.events.StateMachineEventMonitorAPI;

public final class TestStateMachineEventMonitor implements StateMachineEventMonitorAPI<StateMachineEvent> {

    @Override
    public void synchronizeEventLog(StateMachine<?, ?> stateMachine, StateMachineEvent[] stateMachineEvents,
                                    short syncFrom, short syncTo
    ) {

    }

    @Override
    public void asyncEventLogUpdate(StateMachine<?, ?> stateMachine, StateMachineEvent[] stateMachineEvents,
                                    short syncFrom, short syncTo
    ) {

    }
}
