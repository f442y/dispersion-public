package net.fa.dispersion.stateactorsystem.messaging;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = {"TOPIC_TWO"})
@Slf4j
public class KafkaTestTwo {
    private final CountDownLatch countDownLatch = new CountDownLatch(2);
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "TOPIC_TWO", groupId = "groupOne")
    public void listenGroupFoo(String message) {
        log.info(STR."Received Message in group foo: \{message}");
        countDownLatch.countDown();
    }

    @Test
    public void test() throws InterruptedException {
        kafkaTemplate.send("TOPIC_TWO", "TEST_Message | KafkaTestTwo");
        kafkaTemplate.send("TOPIC_TWO", "TEST_Message | KafkaTestTwo");

        assertThat(countDownLatch.await(10L, TimeUnit.SECONDS)).isTrue();
    }
}
