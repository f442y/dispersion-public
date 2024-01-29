//package com.github.f442y.dispersion.composer.orchestration;
//
//import com.github.f442y.dispersion.core.orchestration.PlayRunInputToken;
//import com.github.f442y.dispersion.samples.TestStateMachineSpring.TestSpringStateMachine;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.event.EventListener;
//import org.springframework.stereotype.Component;
//
//@Component
//@Slf4j
//public class BasicServiceDispatcher {
//
//    @Autowired
//    private OrchestrationServerNodes.SimpleOrchestrationServiceOrchestratorServerNode serviceOrchestratorServerNode;
//
//    @Autowired
//    TestSpringStateMachine testSpringStateMachine;
//
////    @JmsListener(destination = "firstQueue")
////    public void firstQueueMessageReceiver(String content) {
////        String message = content;
////        log.info("Message Received on firstQueue: {}", message);
////    }
//
//    @EventListener(ApplicationReadyEvent.class)
//    public void doSomethingAfterStartup() {
//
//        if (serviceOrchestratorServerNode.getLocalServiceClient().isEmpty()) {
//            log.error("could not find service locally");
//        } else {
//            log.info("service is locally available");
////            log.info("dispatching play on client node");
////            var output = serviceOrchestratorServerNode.distributeToClients(new PlayRunInputToken());
////            log.info("client output: {}", output);
//        }
////        StateMachineDescription.buildDescriptionFromStateMap(testSpringStateMachine.getStateMap());
//    }
//}