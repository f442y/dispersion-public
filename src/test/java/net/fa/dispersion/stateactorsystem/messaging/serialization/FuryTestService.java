package net.fa.dispersion.stateactorsystem.messaging.serialization;

import io.fury.Fury;
import io.fury.ThreadSafeFury;
import io.fury.config.Language;
import net.fa.dispersion.stateactorsystem.message.StateUpdateMessage;
import org.springframework.stereotype.Service;

@Service
public class FuryTestService {
    private final ThreadSafeFury fury;

    private FuryTestService() {
        this.fury =
                Fury
                        .builder()
                        .withLanguage(Language.JAVA)
                        .requireClassRegistration(false)
                        .buildThreadSafeFuryPool(2, 2);
    }

    public void printInfo() {
        System.out.println(fury.getClassLoader().getName());
    }

    public byte[] serialize(StateUpdateMessage stateUpdateMessage) {
        return fury.serialize(stateUpdateMessage);
    }

    public StateUpdateMessage deserialize(byte[] bytes) {
        return (StateUpdateMessage) fury.deserialize(bytes);
    }

}
