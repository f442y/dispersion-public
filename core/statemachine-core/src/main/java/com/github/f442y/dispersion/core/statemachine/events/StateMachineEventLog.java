package com.github.f442y.dispersion.core.statemachine.events;

import com.github.f442y.dispersion.core.statemachine.StateMachine;
import com.github.f442y.dispersion.core.statemachine.statemachineconfiguration.StateMachineConfigurationWithCallableTriggers;
import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Non-retaining log
 * <br/>
 * Events are appended to the {@link #stateMachineEventsArray} and are not retained after being cleared out by the event
 * handler.
 *
 * @author Faizaan Ahmed
 * @see StateMachineEventMonitorAPI
 */

@Slf4j
public class StateMachineEventLog implements StateMachineEventLogAPI<StateMachineEvent> {
    private final static Timer EVENT_LOG_TIMER = new Timer("event-notification-timer-dt", true);
    private boolean timerRunning;
    private short eventCount = 0;
    private short eventsSynced = 0;
    private short eventsSentToUI = 0;
    private final ReentrantLock eventCountLock = new ReentrantLock();
    private final StateMachineEvent[] stateMachineEventsArray;
    private final StateMachineEventMonitorAPI<StateMachineEvent> stateMachineEventMonitorAPI;
    private final StateMachine<?, ?> stateMachine;
    private AsyncUIUpdateTask asyncUIUpdateTask;

    public StateMachineEventLog(
            StateMachineConfigurationWithCallableTriggers<?, ?, ?, ?> stateMachineConfigurationWithCallableTriggers,
            StateMachine<?, ?> stateMachine
    ) {
        this.stateMachineEventsArray =
                new StateMachineEvent[calculateMaxEvents(stateMachineConfigurationWithCallableTriggers)];

        this.stateMachine = stateMachine;
        this.stateMachineEventMonitorAPI =
                stateMachineConfigurationWithCallableTriggers.getStateMachineEventMonitor().orElse(null);
    }

    /**
     * Log is sized to maximum number of events:
     * <br>
     * One event for each of StartEvent + EndEvent + InputEvent + OutputEvent = 4
     * <br>
     * Each state has events, however, the End State has no events, so we can remove a state:
     * <br>
     * number of event states = total number of states - 1(End state)
     * <br>
     * Each StateEvent has two events, ActionEvent + TransitionEvent = 2 * number of event states
     * <br>
     * max event size:
     * <br>
     * = 4 + (2 * (number of event states))
     * <br>
     * = 4 + (2 * (total number of states - 1))
     */
    private int calculateMaxEvents(
            StateMachineConfigurationWithCallableTriggers<?, ?, ?, ?> stateMachineConfigurationWithCallableTriggers
    ) {
        return 4 + (2 * (stateMachineConfigurationWithCallableTriggers.getStateMap().stateEnumMap().size() - 1));
    }

    @Override
    public void addStateMachineEventToLog(@Nonnull StateMachineEvent stateMachineEvent) {
        // if no event handler is available, events do not need to be logged
        if (stateMachineEventMonitorAPI == null) {
            return;
        }
        // append event to log
        this.stateMachineEventsArray[eventCount] = stateMachineEvent;
        // lock to prevent async timer using different event counts
        eventCountLock.lock();
        try {
            eventCount++;
            if (this.isSyncEvent(stateMachineEvent)) {
                // stop timer task as sync event notification synchronize events
                // (if running) may not be running if first event or previous event is sync event
                if (timerRunning) {
                    asyncUIUpdateTask.cancel();
                    timerRunning = false;
                }
                // events synced on this thread
                // sync all events added since previous event
                // attempt sync (ui update included)
                stateMachineEventMonitorAPI.synchronizeEventLog(stateMachine,
                        stateMachineEventsArray,
                        (short) (eventsSynced + 1),
                        eventCount
                );
                // update events synced count
                eventsSynced = eventCount;
                // update events sent to ui count
                eventsSentToUI = eventCount;
                // counts updated
                eventCountLock.unlock();
            } else {
                eventCountLock.unlock();
                // as event is non-sync timer thread can continue with notification
                // non-sync event
                // events do not need to be synced immediately as rerunning on recovery would not affect system
                // start a new timer for ui-notification task if none running
                if (!timerRunning) {
                    asyncUIUpdateTask = new AsyncUIUpdateTask(this);
                    EVENT_LOG_TIMER.scheduleAtFixedRate(asyncUIUpdateTask, new Random().nextInt(500, 1000), 2000);
                    timerRunning = true;
                }
            }
        } finally {
            if (eventCountLock.isHeldByCurrentThread()) {
                eventCountLock.unlock();
            }
        }
    }

    private boolean isSyncEvent(StateMachineEvent stateMachineEvent) {
        switch (stateMachineEvent) {
            case StartEvent ignored -> {return true;}
            case FinishEvent ignored -> {return true;}
            default -> {return false;}
        }
    }

    // Task run by Timer Thread
    private static class AsyncUIUpdateTask extends TimerTask {
        private final StateMachineEventLog stateMachineEventLog;

        public AsyncUIUpdateTask(StateMachineEventLog stateMachineEventLog) {
            this.stateMachineEventLog = stateMachineEventLog;
        }

        @Override
        public void run() {
            // lock event count so same event count value is sent in update and assigned to 'eventsSentToUI'
            // this will block statemachine/log thread
            // do not block timer thread as event could be sync and block for long time.
            if (!stateMachineEventLog.eventCountLock.tryLock()) {
                // if event is sync, timer task will be cancelled, but this running task will run to completion, as
                // sync event will do an update, async update does not need to be sent, so this task can return early
                // if event is not sync, this task will run again by schedule to pick up events missed by early
                // return (acceptably missed async update)
                return;
            }
            // lock acquired
            try {
                // check for new events
                if (stateMachineEventLog.eventsSentToUI == stateMachineEventLog.eventCount) {
                    log.info("no new events, no ui updates triggered");
                    // no new events, no update necessary
                    return;
                }
                log.info("new events, sending ui update");
                // async event update only
                stateMachineEventLog.stateMachineEventMonitorAPI.asyncEventLogUpdate(stateMachineEventLog.stateMachine,
                        stateMachineEventLog.stateMachineEventsArray,
                        (short) (stateMachineEventLog.eventsSentToUI + 1),
                        stateMachineEventLog.eventCount
                );
                // keep count of number of events sent to the ui to only send updates if there are new events
                // as event count is locked, the statemachine has not changed the value while this task was running
                stateMachineEventLog.eventsSentToUI = stateMachineEventLog.eventCount;
            } finally {
                stateMachineEventLog.eventCountLock.unlock();
            }
        }
    }
}