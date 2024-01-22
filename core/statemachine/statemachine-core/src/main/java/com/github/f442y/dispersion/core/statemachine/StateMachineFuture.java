package com.github.f442y.dispersion.core.statemachine;

import java.util.concurrent.Future;

public record StateMachineFuture<OUTPUT>(StateMachine<?, ?> stateMachine, Future<OUTPUT> future)
        implements StateMachineOutput<OUTPUT> {}
