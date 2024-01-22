//package net.fa.dispersion.stateactorsystem.messaging;
//
//import io.fury.Fury;
//import io.fury.config.Language;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.jms.annotation.JmsListener;
//import org.springframework.stereotype.Component;
//
//@Component
//public class MessageConsumer {
//    private static final Logger logger = LoggerFactory.getLogger(MessageConsumer.class);
//
//    private final Fury fury;
//
//    public MessageConsumer() {
//        fury = Fury.builder().withLanguage(Language.JAVA)
//                // Allow to deserialize objects unknown types,
//                // more flexible but less secure.
//                // .withSecureMode(false)
//                .build();
//        fury.register(String.class);
//    }
//
//    @JmsListener(destination = "firstQueue")
//    public void firstQueueMessageReceiver(String content) {
//        String message = content;
//        logger.info("Thread: {} | Message Received on firstQueue: {}", Thread.currentThread().threadId(), message);
//    }
//
//    @JmsListener(destination = "secondQueue")
//    public void secondQueueMessageReceiver(String content) {
//        String message = content;
//        logger.info("Thread: {} | Message Received on secondQueue: {}", Thread.currentThread().threadId(), message);
//    }
//
//    @JmsListener(destination = "furyQueue")
//    public void furyQueueMessageReceiver(byte[] content) {
//        var message = fury.deserialize(content);
//        logger.info("Thread: {} | Message Received on furyQueue: {}", Thread.currentThread().threadId(), message);
//    }
//}
