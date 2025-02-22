package com.github.f442y.dispersion.core.orchestration;

import java.util.stream.Stream;

public interface OrchestrationContextAPI {
    interface ContextPlugin {}

    static Stream<Class<?>> getContextPluginInterfaces(Class<?> type) {
        return Stream
                .of(type.getInterfaces())
                .filter(ContextPlugin.class::isAssignableFrom)
                .filter(interfaceType -> !ContextPlugin.class.equals(interfaceType))
                .flatMap(interfaceType -> Stream.concat(
                        Stream.of(interfaceType),
                        getContextPluginInterfaces(interfaceType)
                ));
    }
}
