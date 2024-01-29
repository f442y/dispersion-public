package com.github.f442y.dispersion.core.statemachine.dependency.registry;

public sealed interface DependencyRegistry permits DataDependencyRegistry,
        DependencyRegistry.NoDependency,
        ServiceDependencyRegistry,
        SystemStateDependencyRegistry {
    void validateRegistryObject();

    sealed interface NoDependency extends DependencyRegistry permits DataDependencyRegistry.NoDataDependency,
            ServiceDependencyRegistry.NoServiceDependency,
            SystemStateDependencyRegistry.NoSystemStateDependency {
        @Override
        default void validateRegistryObject() {}
    }
}
