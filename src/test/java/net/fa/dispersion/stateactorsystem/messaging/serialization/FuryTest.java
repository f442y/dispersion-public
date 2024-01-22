package net.fa.dispersion.stateactorsystem.messaging.serialization;

import net.fa.dispersion.stateactorsystem.message.IntMessage;
import net.fa.dispersion.stateactorsystem.message.StateUpdateMessage;
import net.fa.dispersion.stateactorsystem.message.StringMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class FuryTest {

    private static final StateUpdateMessage stateUpdateMessage =
            new StateUpdateMessage(new StringMessage("TEST_message"), new IntMessage(1234567893));
    @Autowired
    private FuryTestService furyTestService;

    @Test
    public void testFury() {
        furyTestService.printInfo();
    }

    @Test
    public void testFuryPerf() {
        for (int i = 0; i < 1000; i++) {
            var ser_msg = furyTestService.serialize(stateUpdateMessage);
            StateUpdateMessage des_msg = furyTestService.deserialize(ser_msg);
            assertEquals(stateUpdateMessage, des_msg);
        }
        System.gc();
    }
}
