package net.fa.dispersion.stateactorsystem.executors;

import lombok.extern.slf4j.Slf4j;
import net.fa.dispersion.stateactorsystem.actors.StateMachineActorWithFuture;
import net.fa.dispersion.stateactorsystem.actors.StateMachineCallableActor;
import net.fa.dispersion.stateactorsystem.statemachine.NestedStateMachine;
import net.fa.dispersion.stateactorsystem.statemachine.context.StateMachineContext;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.StructuredTaskScope;
import java.util.stream.Collectors;

@Slf4j
public class NestedTaskScope<CTX extends StateMachineContext<CTX>,
        PARENT_CTX extends StateMachineContext<PARENT_CTX>> extends
        StructuredTaskScope<Void> {

    ConcurrentHashMap<Subtask<?>, String> completionPolicyMap = new ConcurrentHashMap<>();

    public NestedTaskScope(String serviceScopeThreadName) {
        super(serviceScopeThreadName,
                Thread.ofVirtual().name(String.format("%s-vt-", serviceScopeThreadName), 0).factory()
        );
    }

    @SafeVarargs
    public final void addStateMachinesActorTaskToFork(NestedStateMachine<CTX, PARENT_CTX>... stateMachines) {
        var stateMachineActors = Arrays.stream(stateMachines)
                // each state-machine is made into an actor (callable)
                .map(stateMachine -> new StateMachineCallableActor.StateMachineCallableActorBuilder<CTX, Void>().stateMachine(
                        stateMachine))
                // each actor (callable) is added to the scope (using '.fork()') creating a subtask
                // the actor and subtask (converted to future) are wrapped as a StateMachineActorWithFuture
                .map(stateMachineCallableActor -> new StateMachineActorWithFuture<>(stateMachineCallableActor,
                        CompletableFuture.supplyAsync(this.fork(stateMachineCallableActor))
                )).collect(Collectors.toSet());
        log.trace("Thread: {} | All nested subtasks added to fork", Thread.currentThread().threadId());
    }


    @Override
    protected void handleComplete(StructuredTaskScope.Subtask<? extends Void> subtask) {
        switch (subtask.state()) {
            case SUCCESS, UNAVAILABLE -> {
                log.trace("Thread: {} | Nested Scope subtask finished [{}]", Thread.currentThread().threadId(),
                        subtask.state()
                );
            }
            case FAILED -> {
                //get fail policy

            }
        }
    }
}
