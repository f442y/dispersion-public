package com.github.f442y.dispersion.core.statemachine.TestStateMachine.actions.simpleNumAddition.dependencies;

import com.github.f442y.dispersion.core.statemachine.dependency.ActionDependencies;
import com.github.f442y.dispersion.core.statemachine.dependency.registry.DataDependencyRegistry;
import com.github.f442y.dispersion.core.statemachine.dependency.registry.SystemStateDependencyRegistry;

import java.util.Optional;

public final class ActionSimpleNumAdditionDependencies
        extends ActionDependencies<DataDependencyRegistry.NoDataDependency,
        ActionSimpleNumAdditionServiceDR, SystemStateDependencyRegistry.NoSystemStateDependency> {
    private final ActionSimpleNumAdditionServiceDR actionSimpleNumAdditionServiceRegistryObject;

    public ActionSimpleNumAdditionDependencies() {
        this.actionSimpleNumAdditionServiceRegistryObject =
                new ActionSimpleNumAdditionServiceDR(
                        new DummyTestServices.DummyServiceOne(),
                        new DummyTestServices.DummyServiceTwo()
                );
    }

    @Override
    public Optional<ActionSimpleNumAdditionServiceDR> serviceRegistry() {
        return Optional.of(actionSimpleNumAdditionServiceRegistryObject);
    }
}
