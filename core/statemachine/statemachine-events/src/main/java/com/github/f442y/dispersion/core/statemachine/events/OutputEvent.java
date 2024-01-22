package com.github.f442y.dispersion.core.statemachine.events;

import java.time.LocalDateTime;

public record OutputEvent(LocalDateTime localDateTime) implements StateMachineEvent {
    @Override
    public LocalDateTime time() {
        return localDateTime;
    }
}
