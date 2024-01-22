package com.github.f442y.dispersion.core.statemachine.events;

import com.github.f442y.dispersion.core.statemachine.StateMachine;

public interface StateMachineEventMonitorAPI<SM_EVENT extends StateMachineEventAPI> {
    /**
     * updates/events to be persisted/synchronized before the statemachine can continue. (UI and Persisted updates)
     */
    void synchronizeEventLog(StateMachine<?, ?> stateMachine, SM_EVENT[] stateMachineEvents, short syncFrom,
                             short syncTo
    );

    /**
     * updates/events that do not need to be immediately persisted, therefore should not block the statemachine and
     * are sent in timed batches. (UI updates only)
     */
    void asyncEventLogUpdate(StateMachine<?, ?> stateMachine, SM_EVENT[] stateMachineEvents, short syncFrom,
                             short syncTo
    );
}
