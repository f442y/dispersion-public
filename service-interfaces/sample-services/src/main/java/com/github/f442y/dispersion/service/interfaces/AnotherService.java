package com.github.f442y.dispersion.service.interfaces;

import com.github.f442y.dispersion.core.service.Service;
import com.github.f442y.dispersion.core.statemachine.StateMachineOutput;

public interface AnotherService extends Service {
    StateMachineOutput<?> dispatch() throws InterruptedException;

    StateMachineOutput<Integer> dispatchWithInput(int input) throws InterruptedException;
}
