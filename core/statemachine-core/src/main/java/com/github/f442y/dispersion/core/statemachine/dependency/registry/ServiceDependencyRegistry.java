package com.github.f442y.dispersion.core.statemachine.dependency.registry;

import com.github.f442y.dispersion.core.service.Service;

import java.util.Set;

public abstract non-sealed class ServiceDependencyRegistry implements DependencyRegistry {

    public abstract Set<Service> allServiceDependencies();

    public static final class NoServiceDependency extends ServiceDependencyRegistry implements NoDependency {
        @Override
        public Set<Service> allServiceDependencies() {
            return Set.of();
        }
    }
}
