//package net.fa.dispersion.stateactorsystem.messaging;
//
//import io.fury.Fury;
//import io.fury.config.Language;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.jms.core.JmsTemplate;
//
//import java.util.concurrent.TimeUnit;
//
//@SpringBootTest
//public class MessageTest {
//
//    @Autowired
//    MessageConsumer produceAndConsume;
//
//    @Autowired
//    private JmsTemplate jmsTemplate;
//
//    @Test
//    public void messageTest() throws InterruptedException {
//        for (int i = 0; i < 20; i++) {
//            jmsTemplate.convertAndSend("firstQueue", "simple message " + i);
//            jmsTemplate.convertAndSend("secondQueue", "simple message " + i);
//            TimeUnit.MILLISECONDS.sleep(100);
//        }
//    }
//
//    @Test
//    public void messageTestFury() throws InterruptedException {
//        String string = "Fury Serialized and Deserialized String";
//        // Note that Fury instances should be reused between
//        // multiple serializations of different objects.
//        Fury fury = Fury.builder().withLanguage(Language.JAVA)
//                // Allow to deserialize objects unknown types,
//                // more flexible but less secure.
//                // .withSecureMode(false)
//                .build();
//        // Registering types can reduce class name serialization overhead, but not mandatory.
//        // If secure mode enabled, all custom types must be registered.
//        fury.register(String.class);
//        byte[] bytes = fury.serialize(string);
//        for (int i = 0; i < 20; i++) {
//            jmsTemplate.convertAndSend("furyQueue", bytes);
//            TimeUnit.MILLISECONDS.sleep(100);
//        }
//    }
//
//}
