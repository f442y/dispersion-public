package com.github.f442y.dispersion.core.statemachine.events;

public interface StateMachineEventLogAPI<SM_EVENT extends StateMachineEventAPI> {
    void addStateMachineEventToLog(SM_EVENT stateMachineEvent);
}
