package net.fa.dispersion.stateactorsystem.actor;

import net.fa.dispersion.stateactorsystem.message.ActorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class StateMachineActorWithFuture<RTN> {

    private static final Logger logger = LoggerFactory.getLogger(StateMachineActorWithFuture.class);
    public final CompletableFuture<RTN> completableFuture;
    private Actor<ActorMessage> actor;

    public StateMachineActorWithFuture(Actor<ActorMessage> actor, CompletableFuture<RTN> completableFuture) {
        this.actor = actor;
        this.completableFuture = completableFuture;
    }

    public StateMachineActorWithFuture<RTN> tell(ActorMessage message) {
        if (completableFuture
                .state()
                .equals(Future.State.RUNNING)) {
            actor = actor.tell(message);
        } else {
            logger.info("Thread: {} | Cannot message actor as it no longer exists. Actor state: [{}]", Thread
                    .currentThread()
                    .threadId(), completableFuture.state());
        }
        return this;
    }
}
