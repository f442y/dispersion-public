//package com.github.f442y.dispersion.composer.scout;
//
//import com.github.f442y.dispersion.core.statemachine.executor.StateMachineExecutor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.HashMap;
//import java.util.List;
//
//@Component
//@Slf4j
//public class LocalExecutorScout {
//
//    private final HashMap<String, StateMachineExecutor> stateMachineExecutors = new HashMap<>();
//
//    @Autowired
//    public LocalExecutorScout(List<StateMachineExecutor> stateMachineExecutorsList) {
//        stateMachineExecutorsList.forEach(stateMachineExecutor -> {
//            stateMachineExecutors.put(stateMachineExecutor.getClass().getName(), stateMachineExecutor);
//        });
//        System.out.println(String.format("Found executors: \n       " + String.join(
//                "\n       ",
//                stateMachineExecutors.keySet()
//        )));
//    }
//
//    public StateMachineExecutor getExecutor(String name) {
//        return stateMachineExecutors.get(name);
//    }
//}
