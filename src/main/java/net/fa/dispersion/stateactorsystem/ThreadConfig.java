//package net.fa.dispersion.stateactorsystem;
//
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.task.AsyncTaskExecutor;
//import org.springframework.core.task.support.TaskExecutorAdapter;
//import org.springframework.scheduling.annotation.EnableAsync;
//
//import java.util.concurrent.Executors;
//
//@EnableAsync
//@Configuration
//@ConditionalOnProperty(value = "spring.thread-executor", havingValue = "virtual")
//public class ThreadConfig {
//    @Bean(TaskExecutionAutoConfiguration.APPLICATION_TASK_EXECUTOR_BEAN_NAME)
//    public AsyncTaskExecutor asyncTaskExecutor() {
//        return new TaskExecutorAdapter(Executors.newVirtualThreadPerTaskExecutor());
//    }
//
////    @Bean
////    public TomcatProtocolHandlerCustomizer<?> protocolHandlerVirtualThreadExecutorCustomizer() {
////        return protocolHandler -> {
////            protocolHandler.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
////        };
////    }
//}