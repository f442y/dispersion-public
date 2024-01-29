package com.github.f442y.dispersion.service.interfaces;

import com.github.f442y.dispersion.core.service.Service;
import com.github.f442y.dispersion.core.statemachine.StateMachineOutput;

public interface BufferedBlockingSpawningService extends Service {
    StateMachineOutput<Integer> dispatch() throws InterruptedException;

    StateMachineOutput<Integer> dispatchWithInput(int input) throws InterruptedException;
}
