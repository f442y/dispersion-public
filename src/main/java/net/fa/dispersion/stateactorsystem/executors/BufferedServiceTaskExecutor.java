package net.fa.dispersion.stateactorsystem.executors;

import net.fa.dispersion.stateactorsystem.actor.StateMachineActorWithFuture;
import net.fa.dispersion.stateactorsystem.actor.StateMachineSupplierActor;
import net.fa.dispersion.stateactorsystem.statemachine.StateMachine;
import net.fa.dispersion.stateactorsystem.statemachine.context.StateMachineContext;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class BufferedServiceTaskExecutor<CTX extends StateMachineContext<CTX>, RTN> {

    private final Semaphore bufferSemaphore;

    private final ExecutorService executorService;

    public BufferedServiceTaskExecutor(String serviceScopeThreadName, int bufferSize) {
        this.executorService = Executors.newThreadPerTaskExecutor(
                Thread.ofVirtual().name(STR."\{serviceScopeThreadName}-bfrd-vt-", 0).factory());
        this.bufferSemaphore = new Semaphore(bufferSize);
    }

    public StateMachineActorWithFuture<RTN> addStateMachineActorTaskToExecutor(StateMachine<CTX, RTN> stateMachine) throws InterruptedException {
        // Await a semaphore permit before creating the state-machine callable actor, this ensures a limited
        // number of threads are awaiting execution, limiting heap size
        bufferSemaphore.acquire();
        // Build the actor, for the state-machine, this also initializes the context for the state-machine
        try (var supplierActor = new StateMachineSupplierActor.StateMachineSupplierActorBuilder<CTX, RTN>()
                .buffered(bufferSemaphore)
                .stateMachine(stateMachine)) {
            return new StateMachineActorWithFuture<>(supplierActor,
                    CompletableFuture.supplyAsync(supplierActor, executorService)
            );
        }
    }
}
