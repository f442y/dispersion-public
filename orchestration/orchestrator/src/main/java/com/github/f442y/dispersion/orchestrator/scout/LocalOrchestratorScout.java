//package com.github.f442y.dispersion.composer.scout;
//
//import com.github.f442y.dispersion.composer.orchestration.Orchestrator;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.HashMap;
//import java.util.List;
//
//@Component
//@Slf4j
//public class LocalOrchestratorScout {
//
//    private final HashMap<String, Orchestrator<?, ?>> orchestratorHashMap = new HashMap<>();
//
//    @Autowired
//    public LocalOrchestratorScout(List<Orchestrator<?, ?>> orchestratorList) {
//        orchestratorList.forEach(orchestrator -> {
//            orchestratorHashMap.put(orchestrator.getClass().getName(), orchestrator);
//        });
//        System.out.println(String.format("Found orchestrators: \n       " + String.join(
//                "\n       ",
//                orchestratorHashMap.keySet()
//        )));
//    }
//}
