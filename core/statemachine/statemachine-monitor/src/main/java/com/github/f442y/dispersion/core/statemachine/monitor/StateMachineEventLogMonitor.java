package com.github.f442y.dispersion.core.statemachine.monitor;

import com.github.f442y.dispersion.core.statemachine.StateMachine;
import com.github.f442y.dispersion.core.statemachine.events.StateMachineEvent;
import com.github.f442y.dispersion.core.statemachine.events.StateMachineEventMonitorAPI;
import com.github.f442y.dispersion.core.statemachine.events.StateMachineEventNotificationAPI;
import jakarta.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Semaphore;

@Named
public final class StateMachineEventLogMonitor implements StateMachineEventMonitorAPI<StateMachineEvent> {
    private static final Logger log = LoggerFactory.getLogger(StateMachineEventLogMonitor.class);

    @Override
    public void synchronizeEventLog(StateMachine<?, ?> stateMachine, StateMachineEvent[] stateMachineEvents,
                                    short syncFrom, short syncTo
    ) {
//        log.info("block and perform sync for statemachine: {}", stateMachine.uuid());
        if (syncFrom >= syncTo) {
            return; // do nothing
        }
    }

    // ui update only
    @Override
    public void asyncEventLogUpdate(StateMachine<?, ?> stateMachine, StateMachineEvent[] stateMachineEvents,
                                    short syncFrom, short syncTo
    ) {
        log.info("send async ui update for statemachine: {}", stateMachine.uuid());
        if (syncFrom >= syncTo) {
            return; // do nothing
        }
    }

    private record StateMachineEventNotification(UUID stateMachineUUID, short numberOfEventsToTake, short syncTo,
                                                 Semaphore syncToken) implements StateMachineEventNotificationAPI {
        @Override
        public UUID stateMachineUUID() {
            return stateMachineUUID;
        }

        @Override
        public short numberOfEvents() {
            return numberOfEventsToTake;
        }

        @Override
        public short syncTo() {
            return syncTo;
        }

        @Override
        public Optional<Semaphore> syncPermit() {
            return Optional.ofNullable(syncToken);
        }
    }
}
