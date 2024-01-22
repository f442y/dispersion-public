package net.fa.dispersion.stateactorsystem.actors;

import net.fa.dispersion.stateactorsystem.message.ActorMessage;
import org.springframework.lang.NonNull;

public interface Actor<M extends ActorMessage> {
    Actor<M> tell(@NonNull M msg);
}
