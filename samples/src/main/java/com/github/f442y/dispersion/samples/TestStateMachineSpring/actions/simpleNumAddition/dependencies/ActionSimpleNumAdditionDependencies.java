package com.github.f442y.dispersion.samples.TestStateMachineSpring.actions.simpleNumAddition.dependencies;

import com.github.f442y.dispersion.core.statemachine.dependency.ActionDependencies;
import com.github.f442y.dispersion.core.statemachine.dependency.registry.DataDependencyRegistry;
import com.github.f442y.dispersion.core.statemachine.dependency.registry.SystemStateDependencyRegistry;
import com.github.f442y.dispersion.samples.TestStateMachineSpring.DummyTestServices.DummyServiceOne;
import com.github.f442y.dispersion.samples.TestStateMachineSpring.DummyTestServices.DummyServiceTwo;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import java.util.Optional;

@Named
@Singleton
public final class ActionSimpleNumAdditionDependencies
        extends ActionDependencies<DataDependencyRegistry.NoDataDependency,
        ActionSimpleNumAdditionServiceDependencyRegistry, SystemStateDependencyRegistry.NoSystemStateDependency> {
    private final ActionSimpleNumAdditionServiceDependencyRegistry actionSimpleNumAdditionServiceRegistryObject;

    public ActionSimpleNumAdditionDependencies() {
        this.actionSimpleNumAdditionServiceRegistryObject =
                new ActionSimpleNumAdditionServiceDependencyRegistry(new DummyServiceOne(), new DummyServiceTwo());
    }

    @Override
    public Optional<ActionSimpleNumAdditionServiceDependencyRegistry> serviceRegistry() {
        return Optional.of(actionSimpleNumAdditionServiceRegistryObject);
    }
}
