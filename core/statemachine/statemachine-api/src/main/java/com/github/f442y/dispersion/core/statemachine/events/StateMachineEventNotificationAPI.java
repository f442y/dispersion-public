package com.github.f442y.dispersion.core.statemachine.events;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Semaphore;

public interface StateMachineEventNotificationAPI {
    UUID stateMachineUUID();

    short numberOfEvents();

    short syncTo();

    Optional<Semaphore> syncPermit();
}
