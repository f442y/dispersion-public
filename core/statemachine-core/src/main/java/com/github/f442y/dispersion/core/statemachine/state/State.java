package com.github.f442y.dispersion.core.statemachine.state;

import com.github.f442y.dispersion.core.statemachine.StateMachine;
import com.github.f442y.dispersion.core.statemachine.StateMachineCallable;
import com.github.f442y.dispersion.core.statemachine.context.StateMachineContext;
import com.github.f442y.dispersion.core.statemachine.dependency.ActionDependencies;
import com.github.f442y.dispersion.core.statemachine.events.ActionEvent;
import com.github.f442y.dispersion.core.statemachine.events.StateMachineEvent;
import com.github.f442y.dispersion.core.statemachine.events.StateMachineEventLogAPI;
import com.github.f442y.dispersion.core.statemachine.events.TransitionEvent;
import com.github.f442y.dispersion.core.statemachine.state.action.Action;
import com.github.f442y.dispersion.core.statemachine.state.action.ActionWithCallableTrigger;
import com.github.f442y.dispersion.core.statemachine.state.action.traits.PersistentMutation;
import com.github.f442y.dispersion.core.statemachine.state.transition.Transition;
import com.github.f442y.dispersion.core.statemachine.state.transition.TransitionStateKey;
import com.github.f442y.dispersion.core.statemachine.state.transition.TransitionWithCallableTrigger;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * <B>All states should extend this class.</B>
 * <p>
 * <p>
 * The methods overridden default trigger methods contain parameters populated when triggered by
 * {@link StateMachineCallable} that are useful for observation.
 * <p>
 * The overridden default methods should only be used as hooks to be called by
 * {@link StateMachineCallable}, their functionality is already
 * defined.
 *
 * @param <CONTEXT> The context type of the statemachine
 * @author Faizaan Ahmed
 * @see StateWithCallableTriggers
 */
@Slf4j
public abstract non-sealed class State<CONTEXT extends StateMachineContext,
        STATE_KEY extends Enum<STATE_KEY> & StateKey>
        implements StateWithCallableTriggers<CONTEXT, STATE_KEY>, StateAPI {

    public final Action<CONTEXT, ? extends ActionDependencies<?, ?, ?>> action;
    public final Transition<CONTEXT, STATE_KEY, ? extends TransitionStateKey<STATE_KEY>> transition;

    public State(Action<CONTEXT, ? extends ActionDependencies<?, ?, ?>> action,
                 Transition<CONTEXT, STATE_KEY, ? extends TransitionStateKey<STATE_KEY>> transition
    ) {
        this.action = action;
        this.transition = transition;
    }

    /**
     * <B>OBSERVED</B>
     * <p>
     * {@inheritDoc}
     */
    @Override
    public final ActionWithCallableTrigger<CONTEXT> stateMachineGetActionCallableTrigger(
            StateMachine<CONTEXT, STATE_KEY> stateMachine,
            StateMachineEventLogAPI<StateMachineEvent> stateMachineEventLogAPI
    ) {
        boolean syncEvents = this.action instanceof PersistentMutation;
        // create action event
        ActionEvent actionEvent = new ActionEvent(LocalDateTime.now(ZoneOffset.UTC), syncEvents, this.action);
        // save event to event log
        stateMachineEventLogAPI.addStateMachineEventToLog(actionEvent);
        return this.action;
    }

    /**
     * <B>OBSERVED</B>
     * <p>
     * {@inheritDoc}
     */
    @Override
    public final TransitionWithCallableTrigger<CONTEXT, STATE_KEY> stateMachineGetTransitionCallableTrigger(
            StateMachine<CONTEXT, STATE_KEY> stateMachine,
            StateMachineEventLogAPI<StateMachineEvent> stateMachineEventLogAPI
    ) {
        TransitionEvent transitionEvent = new TransitionEvent(LocalDateTime.now(ZoneOffset.UTC), this.transition);
        // save event to event log
        stateMachineEventLogAPI.addStateMachineEventToLog(transitionEvent);
        return this.transition;
    }

    /**
     * Predefined End State class
     */
    public static final class EndState<CONTEXT extends StateMachineContext,
            STATE_KEY extends Enum<STATE_KEY> & StateKey>
            extends State<CONTEXT, STATE_KEY> implements EndStateCallableTriggers<CONTEXT, STATE_KEY> {
        public EndState() {
            super(null, null);
        }
    }
}
