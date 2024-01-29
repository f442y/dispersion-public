package com.github.f442y.dispersion.core.statemachine.dependency;

import com.github.f442y.dispersion.core.statemachine.dependency.registry.DataDependencyRegistry;
import com.github.f442y.dispersion.core.statemachine.dependency.registry.ServiceDependencyRegistry;
import com.github.f442y.dispersion.core.statemachine.dependency.registry.SystemStateDependencyRegistry;

import java.util.Optional;

/**
 * For accessing action dependencies via registry objects
 *
 * @param <DATA_REGISTRY>
 * @param <SERVICE_REGISTRY>
 * @param <SYSTEM_STATE_REGISTRY>
 */
public abstract class ActionDependencies<DATA_REGISTRY extends DataDependencyRegistry,
        SERVICE_REGISTRY extends ServiceDependencyRegistry,
        SYSTEM_STATE_REGISTRY extends SystemStateDependencyRegistry> {

    public Optional<DATA_REGISTRY> dataRegistry() {
        return Optional.empty();
    }

    public Optional<SERVICE_REGISTRY> serviceRegistry() {
        return Optional.empty();
    }

    public Optional<SYSTEM_STATE_REGISTRY> systemStateRegistry() {
        return Optional.empty();
    }

    void verifyDependencies() {
        this.serviceRegistry().ifPresent(serviceRegistry -> {
            // check service registry services
            serviceRegistry.validateRegistryObject();
        });
        this.dataRegistry().ifPresent(dataRegistry -> {
            // data dependencies object
        });
        this.systemStateRegistry().ifPresent(systemStateRegistry -> {
            // validate system state dependencies object
        });
    }

    /**
     * Used for Actions with no dependencies.
     */
    public static class NoDependencies
            extends ActionDependencies<DataDependencyRegistry.NoDataDependency,
            ServiceDependencyRegistry.NoServiceDependency, SystemStateDependencyRegistry.NoSystemStateDependency> {}
}
