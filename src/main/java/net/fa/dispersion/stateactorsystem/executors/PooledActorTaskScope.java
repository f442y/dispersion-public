//package net.fa.dispersion.stateactorsystem.scope;
//
//import lombok.extern.slf4j.Slf4j;
//import net.fa.dispersion.stateactorsystem.actors.StateMachineActorWithFuture;
//import net.fa.dispersion.stateactorsystem.statemachine.StateMachine;
//
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.StructuredTaskScope;
//
//@Slf4j
//public class PooledActorTaskScope<T> extends StructuredTaskScope<T> {
//    private final StateMachine stateMachine;
//    private final ConcurrentHashMap<Subtask<T>, StateMachineActorWithFuture<T>> actorPool = new ConcurrentHashMap<>();
//
//    public PooledActorTaskScope(String serviceScopeThreadName, int poolSize, StateMachine stateMachine) {
//        super(serviceScopeThreadName,
//              Thread.ofVirtual().name(String.format("%s-pool-vt-", serviceScopeThreadName), 0).factory()
//        );
//        this.stateMachine = stateMachine;
//        for (int i = 0; i < poolSize; i++) {
//            addStateMachineActorTask();
//        }
//    }
//
//    private void addStateMachineActorTask() {
////        var callableActor = new StateMachineCallablePoolActor<T, Void, Void>(_ -> stateMachine.initialState);
////        var subtask = this.fork(callableActor);
////        logger.trace("Thread: {} | Pooled StateMachine subtask added to fork", Thread.currentThread().threadId());
////        var actor = new StateMachineActorWithFuture<>(callableActor, CompletableFuture.supplyAsync(subtask));
////        actorPool.put(subtask, actor);
//    }
//
//    @Override
//    protected void handleComplete(StructuredTaskScope.Subtask<? extends T> subtask) {
//        switch (subtask.state()) {
//            case SUCCESS, FAILED, UNAVAILABLE -> {
//                log.info("Thread: {} | Pooled Scope subtask finished [{}]", Thread.currentThread().threadId(),
//                         subtask.state()
//                );
//            }
//        }
//        // If a state-machine actor completes, then the actor should be removed from the pool
//        actorPool.remove(subtask);
//        // A new state-machine actor should be added to the pool, to replace the complete/removed actor
//        addStateMachineActorTask();
//    }
//
////    public StateMachineActorWithFuture<T> getStateMachineActor() throws InterruptedException {
////        semaphore.acquire();
////    }
//}
