package com.github.f442y.dispersion.core.statemachine.statemachineconfiguration;

import com.github.f442y.dispersion.core.statemachine.context.StateMachineContext;

import java.util.function.Function;

@FunctionalInterface
public interface OutputFunction<CONTEXT extends StateMachineContext, OUTPUT> extends Function<CONTEXT, OUTPUT> {}
