package com.github.f442y.dispersion.core.statemachine.events;

import java.time.LocalDateTime;

public record StartEvent(LocalDateTime localDateTime) implements StateMachineEvent {
    @Override
    public LocalDateTime time() {
        return localDateTime;
    }
}