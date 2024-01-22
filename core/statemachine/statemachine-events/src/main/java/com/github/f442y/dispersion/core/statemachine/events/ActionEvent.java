package com.github.f442y.dispersion.core.statemachine.events;

import com.github.f442y.dispersion.core.statemachine.state.ActionAPI;

import java.time.LocalDateTime;

public record ActionEvent(LocalDateTime localDateTime, boolean syncEvents, ActionAPI action) implements StateEvent {
    @Override
    public LocalDateTime time() {
        return localDateTime;
    }
}
