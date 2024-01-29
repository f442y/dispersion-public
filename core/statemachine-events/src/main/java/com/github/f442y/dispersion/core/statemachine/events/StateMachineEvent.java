package com.github.f442y.dispersion.core.statemachine.events;

import java.time.LocalDateTime;

public sealed interface StateMachineEvent extends StateMachineEventAPI
        permits FinishEvent, InputEvent, OutputEvent, StartEvent, StateEvent {
    LocalDateTime time();
}
