//package net.fa.dispersion.stateactorsystem.executors;
//
//import lombok.extern.slf4j.Slf4j;
//import net.fa.dispersion.stateactorsystem.actors.StateMachineActorWithFuture;
//import net.fa.dispersion.stateactorsystem.actors.StateMachineCallableActor;
//import net.fa.dispersion.stateactorsystem.statemachine.StateMachine;
//import net.fa.dispersion.stateactorsystem.statemachine.context.StateMachineContext;
//
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.Semaphore;
//import java.util.concurrent.StructuredTaskScope;
//
//@Slf4j
//public class BufferedServiceTaskScope<CTX extends StateMachineContext<CTX>, RTN> extends StructuredTaskScope<RTN> {
//    private final Semaphore bufferSemaphore;
//
//    public BufferedServiceTaskScope(String serviceScopeThreadName, int bufferSize) {
//        super(serviceScopeThreadName, Thread.ofVirtual().name(STR."\{serviceScopeThreadName}-bfrd-vt-", 0).factory());
//        this.bufferSemaphore = new Semaphore(bufferSize);
//    }
//
//    public StateMachineActorWithFuture<RTN> addStateMachineActorTaskToFork(StateMachine<CTX, RTN> stateMachine)
//    throws InterruptedException {
//        // Await a semaphore permit before creating the state-machine callable actor, this ensures a limited
//        // number of threads are awaiting execution, limiting heap size
//        bufferSemaphore.acquire();
//        // Build the callable actor, for the state-machine, this also initializes the context for the state-machine
//        var callableActor =
//                new StateMachineCallableActor.StateMachineCallableActorBuilder<CTX, RTN>().stateMachine(stateMachine);
//        // Add/Fork the callable actor to the scope, creating a virtual thread and beginning execution
//        // The thread factory passed into the 'super()' of this class is used to create the thread
//        // Returns a subtask, which is a Supplier
//        var subtask = this.fork(callableActor);
//        // The subtask is converted into a CompletableFuture and bundled into a tuple type with the callable actor
//        // This allows the actor to be messaged with a check to determine if the future has completed
//        return new StateMachineActorWithFuture<>(callableActor, CompletableFuture.supplyAsync(subtask));
//    }
//
//    @Override
//    protected void handleComplete(Subtask<? extends RTN> subtask) {
//        // When a subtask completes, a semaphore permit should be released,
//        // Allows a new callable actor to be created and added to the scope
//        bufferSemaphore.release();
//
//        // This scope is used for thread grouping and cancellation, success and failure should be managed within the
//        // state-machine and/or callable actor
//        switch (subtask.state()) {
//            case SUCCESS, FAILED, UNAVAILABLE -> {
//                log.trace("Thread: {} | Service subtask finished [{}]", Thread.currentThread().threadId(),
//                        subtask.state()
//                );
//
//            }
//        }
//    }
//}