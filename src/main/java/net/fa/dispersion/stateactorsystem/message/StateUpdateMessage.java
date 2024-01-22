package net.fa.dispersion.stateactorsystem.message;

public record StateUpdateMessage(StringMessage stringMessage, IntMessage intMessage) implements ActorMessage {
}
