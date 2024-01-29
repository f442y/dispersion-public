//package com.github.f442y.dispersion.composer.orchestration;
//
//import com.github.f442y.dispersion.core.orchestration.OrchestrationService;
//import com.github.f442y.dispersion.service.interfaces.SimpleOrchestrationService;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Test;
//import org.reflections.Reflections;
//import org.reflections.util.ClasspathHelper;
//import org.reflections.util.ConfigurationBuilder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.lang.reflect.ParameterizedType;
//import java.util.Collections;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@SpringBootTest
//@Slf4j
//public class OrchestrationServerNodesTest {
//    @Autowired
//    List<OrchestratorServerNode<?>> orchestratorServerNodeList;
//
//    @Test
//    public void testAllOrchestrationServiceInterfacesHaveImplementation() {
//        Reflections reflections =
//                new Reflections(new ConfigurationBuilder().setUrls(Collections.singletonList(ClasspathHelper.forClass(
//                        SimpleOrchestrationService.class))));
//
//
//        var subTypes = reflections
//                .getSubTypesOf(OrchestrationService.class)
//                .stream()
//                .filter(Class::isInterface)
//                .map(Class::toString)
//                .collect(Collectors.toSet());
//
//        var orchestrationServiceSet = orchestratorServerNodeList
//                .stream()
//                .map(orchestratorServerNode -> ((ParameterizedType) orchestratorServerNode
//                        .getClass()
//                        .getGenericSuperclass()).getActualTypeArguments()[1].toString())
//                .collect(Collectors.toSet());
//
//        subTypes.removeAll(orchestrationServiceSet);
//
//        try {
//            assertEquals(0, subTypes.size());
//        } catch (AssertionError assertionError) {
//            log.error("Component not implemented for at least one OrchestrationService: {}", subTypes);
//            throw assertionError;
//        }
//
//    }
//
//
//}