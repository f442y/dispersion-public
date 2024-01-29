package com.github.f442y.dispersion.core.statemachine.dependency.registry;

public abstract non-sealed class DataDependencyRegistry implements DependencyRegistry {
    public static final class NoDataDependency extends DataDependencyRegistry implements NoDependency {}
}
