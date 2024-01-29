package com.github.f442y.dispersion.core.statemachine.executor;

import com.github.f442y.dispersion.core.application.PermitConfig;
import com.github.f442y.dispersion.core.statemachine.StateMachineCallable;
import com.github.f442y.dispersion.core.statemachine.StateMachineFuture;
import com.github.f442y.dispersion.core.statemachine.context.StateMachineContext;
import com.github.f442y.dispersion.core.statemachine.state.StateKey;
import com.github.f442y.dispersion.core.statemachine.statemachineconfiguration.StateMachineConfigurationWithCallableTriggers;
import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;

@Slf4j
public abstract class BufferedStateMachineExecutor<CONTEXT extends StateMachineContext,
        STATE_KEY extends Enum<STATE_KEY> & StateKey, INPUT, OUTPUT>
        implements StateMachineExecutor {
    private final ExecutorService executorService;
    private final StateMachineConfigurationWithCallableTriggers<CONTEXT, STATE_KEY, INPUT, OUTPUT>
            stateMachineConfiguration;
    private static Semaphore bufferSemaphore;
    private final PermitConfig permitConfig;

    protected BufferedStateMachineExecutor(String serviceThreadName,
                                           StateMachineConfigurationWithCallableTriggers<CONTEXT, STATE_KEY, INPUT,
                                                   OUTPUT> stateMachineConfiguration,
                                           int bufferSize, PermitConfig permitConfig
    ) {
        this.executorService = Executors.newThreadPerTaskExecutor(Thread
                .ofVirtual()
                .name(MessageFormat.format("{0}-bv-", serviceThreadName), 0)
                .factory());
        this.stateMachineConfiguration = stateMachineConfiguration;
        bufferSemaphore = new Semaphore(bufferSize);
        this.permitConfig = permitConfig;
    }

    /**
     * Blocking based on semaphore permits.
     * Creates new thread in executor
     *
     * @throws InterruptedException
     */
    protected StateMachineFuture<OUTPUT> addStateMachineTaskToExecutor(INPUT input) throws InterruptedException {
        // Await a semaphore permit before creating the state-machine callable, this ensures a limited
        // number of threads are awaiting execution, limiting heap size
        permitConfig.acquireThreadPermits(bufferSemaphore);
        // Build the stateMachineCallable, for the state-machine, this also initializes the context for the
        // state-machine
        StateMachineCallable<CONTEXT, STATE_KEY, INPUT, OUTPUT> stateMachineCallable =
                new StateMachineCallable.StateMachineCallableBuilder<CONTEXT, STATE_KEY, INPUT, OUTPUT>()
                        .buffered(bufferSemaphore)
                        .permitConfig(permitConfig)
                        .input(input)
                        .stateMachine(stateMachineConfiguration);
        Future<OUTPUT> future = executorService.submit(stateMachineCallable);
        return new StateMachineFuture<>(stateMachineCallable, future);
    }
}
