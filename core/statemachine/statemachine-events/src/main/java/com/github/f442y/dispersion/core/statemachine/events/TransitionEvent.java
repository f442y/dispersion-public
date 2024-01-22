package com.github.f442y.dispersion.core.statemachine.events;

import com.github.f442y.dispersion.core.statemachine.state.TransitionAPI;

import java.time.LocalDateTime;

public record TransitionEvent(LocalDateTime localDateTime, TransitionAPI transition) implements StateEvent {
    @Override
    public LocalDateTime time() {
        return localDateTime;
    }
}
