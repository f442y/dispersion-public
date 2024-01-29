package com.github.f442y.dispersion.core.statemachine.state.action;

import com.github.f442y.dispersion.core.statemachine.state.ActionAPI;
import com.github.f442y.dispersion.core.statemachine.context.StateMachineContext;
import com.github.f442y.dispersion.core.statemachine.dependency.ActionDependencies;
import com.github.f442y.dispersion.core.statemachine.exception.ActionException;
import jakarta.annotation.Nonnull;
import lombok.NonNull;

@Nonnull
public non-sealed abstract class Action
        <CONTEXT extends StateMachineContext, ACTION_DEPENDENCIES extends ActionDependencies<?, ?, ?>>
        implements ActionWithCallableTrigger<CONTEXT>, ActionAPI {
    @Nonnull
    private final ACTION_DEPENDENCIES actionDependencies;

    public Action(@NonNull ACTION_DEPENDENCIES actionDependencies) {
        this.actionDependencies = actionDependencies;
    }

    @Override
    public final CONTEXT actionCallableTrigger(CONTEXT stateMachineContext) throws ActionException {
        return this.action(stateMachineContext, this.actionDependencies);
    }

    @Nonnull
    public abstract CONTEXT action(CONTEXT stateMachineContext, ACTION_DEPENDENCIES actionDependencies
    ) throws ActionException;

    public ACTION_DEPENDENCIES viewDependencies() {
        return actionDependencies;
    }
}
