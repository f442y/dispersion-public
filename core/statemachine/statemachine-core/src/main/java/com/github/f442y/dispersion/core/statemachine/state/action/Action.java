package com.github.f442y.dispersion.core.statemachine.state.action;

import com.github.f442y.dispersion.core.statemachine.context.StateMachineContext;
import com.github.f442y.dispersion.core.statemachine.dependency.ActionDependencies;
import com.github.f442y.dispersion.core.statemachine.exception.ActionException;
import com.github.f442y.dispersion.core.statemachine.state.ActionAPI;
import jakarta.annotation.Nonnull;

@Nonnull
public non-sealed abstract class Action<CONTEXT extends StateMachineContext,
        ACTION_DEPENDENCIES extends ActionDependencies<?, ?, ?>>
        implements ActionWithCallableTrigger<CONTEXT>, ActionAPI {
    @Nonnull
    private final ACTION_DEPENDENCIES actionDependencies;

    public Action(@Nonnull ACTION_DEPENDENCIES actionDependencies) {
        this.actionDependencies = actionDependencies;
    }

    /**
     * Encapsulates all {@link RuntimeException}s wrapping the exception into an {@link ActionException}
     * <p>
     * {@inheritDoc}
     */
    @Override
    public final CONTEXT actionCallableTrigger(CONTEXT stateMachineContext) throws ActionException {
        try {
            // verify dependencies before running?
            return this.action(stateMachineContext, this.actionDependencies);
        } catch (RuntimeException e) {
            throw new ActionException(e);
        }
    }

    /**
     * {@link Action} method with {@link ActionDependencies}.
     */
    @Nonnull
    public abstract CONTEXT action(CONTEXT stateMachineContext,
                                   ACTION_DEPENDENCIES actionDependencies
    ) throws RuntimeException;

    public final ACTION_DEPENDENCIES getDependencies() {
        return actionDependencies;
    }
}
