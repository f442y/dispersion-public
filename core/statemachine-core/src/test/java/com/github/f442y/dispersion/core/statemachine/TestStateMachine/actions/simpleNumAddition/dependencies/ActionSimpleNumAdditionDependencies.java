package com.github.f442y.dispersion.core.statemachine.TestStateMachine.actions.simpleNumAddition.dependencies;

import com.github.f442y.dispersion.core.statemachine.dependency.ActionDependencies;
import com.github.f442y.dispersion.core.statemachine.dependency.registry.DataDependencyRegistry;
import com.github.f442y.dispersion.core.statemachine.dependency.registry.SystemStateDependencyRegistry;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public final class ActionSimpleNumAdditionDependencies
        extends ActionDependencies<DataDependencyRegistry.NoDataDependency,
        ActionSimpleNumAdditionServiceDependencyRegistry, SystemStateDependencyRegistry.NoSystemStateDependency> {
    private final ActionSimpleNumAdditionServiceDependencyRegistry actionSimpleNumAdditionServiceRegistryObject;

    public ActionSimpleNumAdditionDependencies() {
        this.actionSimpleNumAdditionServiceRegistryObject =
                new ActionSimpleNumAdditionServiceDependencyRegistry(
                        new DummyTestServices.DummyServiceOne(),
                        new DummyTestServices.DummyServiceTwo()
                );
    }

    @Override
    public Optional<ActionSimpleNumAdditionServiceDependencyRegistry> serviceRegistry() {
        return Optional.of(actionSimpleNumAdditionServiceRegistryObject);
    }
}
