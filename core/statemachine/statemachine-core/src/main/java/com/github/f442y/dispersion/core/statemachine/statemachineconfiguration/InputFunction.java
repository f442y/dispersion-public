package com.github.f442y.dispersion.core.statemachine.statemachineconfiguration;

import com.github.f442y.dispersion.core.statemachine.context.StateMachineContext;

import java.util.function.BiFunction;

@FunctionalInterface
public interface InputFunction<CONTEXT extends StateMachineContext, INPUT>
        extends BiFunction<CONTEXT, INPUT, CONTEXT> {}
