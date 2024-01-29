package com.github.f442y.dispersion.samples.TestStateMachineSpring.service;

import com.github.f442y.dispersion.core.application.ApplicationConfig;
import com.github.f442y.dispersion.core.statemachine.StateMachineFuture;
import com.github.f442y.dispersion.core.statemachine.executor.BufferedStateMachineExecutor;
import com.github.f442y.dispersion.samples.TestStateMachineSpring.TestSpringStateMachine;
import com.github.f442y.dispersion.service.interfaces.BufferedBlockingSpawningService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BufferedBlockingSpawningServiceImpl
        extends BufferedStateMachineExecutor<TestSpringStateMachine.StateMachineSpringContext,
        TestSpringStateMachine.States, Integer, Integer>
        implements BufferedBlockingSpawningService {

    @Autowired
    public BufferedBlockingSpawningServiceImpl(TestSpringStateMachine testSpringStateMachine,
                                               ApplicationConfig applicationConfig
    ) {
        super(
                "buffered-blocking",
                testSpringStateMachine,
                Runtime.getRuntime().availableProcessors() * 3,
                applicationConfig
        );
    }

    @Override
    public StateMachineFuture<Integer> dispatch() throws InterruptedException {
        return super.addStateMachineTaskToExecutor(10);
    }

    @Override
    public StateMachineFuture<Integer> dispatchWithInput(int input) throws InterruptedException {
        return super.addStateMachineTaskToExecutor(input);
    }
}
