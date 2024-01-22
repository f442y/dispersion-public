package net.fa.dispersion.stateactorsystem.statemachine.service;

import net.fa.dispersion.stateactorsystem.actors.StateMachineActorWithFuture;
import net.fa.dispersion.stateactorsystem.executors.BufferedServiceTaskScope;
import net.fa.dispersion.stateactorsystem.statemachine.StateMachine;
import net.fa.dispersion.stateactorsystem.statemachine.context.StateMachineContext;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

@Aspect
@Service
public class ScopedStateMachineService<CTX extends StateMachineContext<CTX>, RTN> {
    private final BufferedServiceTaskScope<CTX, RTN> scope;

    public ScopedStateMachineService() {
        this.scope = new BufferedServiceTaskScope<>("scoped-SM", 160);

    }


    public StateMachineActorWithFuture<RTN> createSMActor(StateMachine<CTX, RTN> stateMachine) throws InterruptedException {
        return scope.addStateMachineActorTaskToFork(stateMachine);
    }

}