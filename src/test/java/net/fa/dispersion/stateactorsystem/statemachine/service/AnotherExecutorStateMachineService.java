package net.fa.dispersion.stateactorsystem.statemachine.service;

import net.fa.dispersion.stateactorsystem.actor.StateMachineActorWithFuture;
import net.fa.dispersion.stateactorsystem.executors.BufferedServiceTaskExecutor;
import net.fa.dispersion.stateactorsystem.statemachine.StateMachine;
import net.fa.dispersion.stateactorsystem.statemachine.context.StateMachineContext;
import org.springframework.stereotype.Service;

@Service
public class AnotherExecutorStateMachineService<CTX extends StateMachineContext<CTX>, RTN> {
    private final BufferedServiceTaskExecutor<CTX, RTN> executor;

    public AnotherExecutorStateMachineService() {
        this.executor = new BufferedServiceTaskExecutor<>("scoped-SM", 160);
    }

    public StateMachineActorWithFuture<RTN> createSMActor(StateMachine<CTX, RTN> stateMachine) throws InterruptedException {
        return executor.addStateMachineActorTaskToExecutor(stateMachine);
    }

}