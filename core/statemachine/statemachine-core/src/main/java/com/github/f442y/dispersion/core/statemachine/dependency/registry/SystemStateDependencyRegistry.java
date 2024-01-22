package com.github.f442y.dispersion.core.statemachine.dependency.registry;

public abstract non-sealed class SystemStateDependencyRegistry implements DependencyRegistry {
    public static final class NoSystemStateDependency extends SystemStateDependencyRegistry implements NoDependency {}
}
