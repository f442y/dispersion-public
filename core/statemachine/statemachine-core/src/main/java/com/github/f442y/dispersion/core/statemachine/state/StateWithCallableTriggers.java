package com.github.f442y.dispersion.core.statemachine.state;

import com.github.f442y.dispersion.core.statemachine.StateMachine;
import com.github.f442y.dispersion.core.statemachine.StateMachineCallable;
import com.github.f442y.dispersion.core.statemachine.context.StateMachineContext;
import com.github.f442y.dispersion.core.statemachine.events.StateMachineEvent;
import com.github.f442y.dispersion.core.statemachine.events.StateMachineEventLogAPI;
import com.github.f442y.dispersion.core.statemachine.state.action.ActionWithCallableTrigger;
import com.github.f442y.dispersion.core.statemachine.state.transition.TransitionWithCallableTrigger;

/**
 * This is the base interface for every state in the statemachine.
 * <p>
 * This interface is sealed, so it cannot be implemented directly. It exists to allow the
 * {@link StateMachineCallable} to handle both Observed and
 * Unobserved states with a common interface.
 * <p>
 * States must explicitly extend one of the permitted non-sealed abstract classes.
 * ({@link State}
 *
 * @param <CONTEXT> The context type of the statemachine
 * @author Faizaan Ahmed
 * @see State
 */
public sealed interface StateWithCallableTriggers<CONTEXT extends StateMachineContext,
        STATE_KEY extends Enum<STATE_KEY> & StateKey>
        extends StateAPI permits State, StateWithCallableTriggers.EndStateCallableTriggers {

    /**
     * This method is only called in {@link StateMachineCallable}
     * to get the action.
     * <p>
     * It is a Trigger method used as a hook hide the parameters in this method which are only used for state
     * observation.
     *
     * @param stateMachine The statemachine in which this method is called.
     * @return The {@link ActionWithCallableTrigger} this state resolves to.
     */
    ActionWithCallableTrigger<CONTEXT> stateMachineGetActionCallableTrigger(
            StateMachine<CONTEXT, STATE_KEY> stateMachine,
            StateMachineEventLogAPI<StateMachineEvent> stateMachineEventLogAPI
    );

    /**
     * This method is only called in {@link StateMachineCallable}
     * to get the transition.
     * <p>
     * It is a Wrapper/Trigger method used as a hook to hide the parameters in this method which are only used
     * for state observation.
     *
     * @param stateMachine The statemachine in which this method is called.
     * @return The {@link ActionWithCallableTrigger} this state resolves to.
     */
    TransitionWithCallableTrigger<CONTEXT, STATE_KEY> stateMachineGetTransitionCallableTrigger(
            StateMachine<CONTEXT, STATE_KEY> stateMachine,
            StateMachineEventLogAPI<StateMachineEvent> stateMachineEventLogAPI
    );


    sealed interface EndStateCallableTriggers<CONTEXT extends StateMachineContext,
            STATE_KEY extends Enum<STATE_KEY> & StateKey>
            extends StateWithCallableTriggers<CONTEXT, STATE_KEY> permits State.EndState {}
}
