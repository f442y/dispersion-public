package com.github.f442y.dispersion.core.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.concurrent.CountDownLatch;

@SpringBootApplication(scanBasePackages = ApplicationStatic.PACKAGE_ROOT)
@EnableJpaRepositories(basePackages = ApplicationStatic.PACKAGE_ROOT)
@EntityScan(basePackages = ApplicationStatic.PACKAGE_ROOT)
//@EnableJms
public class DispersionApplication {
    private static final CountDownLatch applicationExitLatch = new CountDownLatch(1);
//    public static final RuntimeBootValidation RUNTIME_BOOT_VALIDATION = new RuntimeBootValidation();

    public static void startApp(String[] args) throws InterruptedException {
        SpringApplication.run(DispersionApplication.class, args);
        applicationExitLatch.await();
    }

    public static void stopApp() {
        applicationExitLatch.countDown();
    }

//    @EventListener(ApplicationReadyEvent.class)
//    public void doSomethingAfterStartup() {
//        System.out.println("hello world, I have just started up");
//    }
    //    @Bean
//    public OpenTelemetry openTelemetry() {
//        return AutoConfiguredOpenTelemetrySdk.initialize().getOpenTelemetrySdk();
//    }
//    @Bean
//    public OpenTelemetry openTelemetry() {
//        Resource resource = Resource
//                .getDefault()
//                .toBuilder()
//                .put(ResourceAttributes.SERVICE_NAME, "monolith server")
//                .put(ResourceAttributes.SERVICE_VERSION, "0.1.0")
//                .build();
//
//        SdkTracerProvider sdkTracerProvider = SdkTracerProvider
//                .builder()
//                .addSpanProcessor(SimpleSpanProcessor.create(LoggingSpanExporter.create()))
//                .setResource(resource)
//                .build();
//
//        SdkMeterProvider sdkMeterProvider = SdkMeterProvider
//                .builder()
//                .registerMetricReader(PeriodicMetricReader.builder(LoggingMetricExporter.create()).build())
//                .setResource(resource)
//                .build();
//
//        SdkLoggerProvider sdkLoggerProvider = SdkLoggerProvider
//                .builder()
//                .addLogRecordProcessor(BatchLogRecordProcessor.builder(SystemOutLogRecordExporter.create()).build())
//                .setResource(resource)
//                .build();
//
//        OpenTelemetry openTelemetry = OpenTelemetrySdk
//                .builder()
//                .setTracerProvider(sdkTracerProvider)
//                .setMeterProvider(sdkMeterProvider)
//                .setLoggerProvider(sdkLoggerProvider)
//                .setPropagators(ContextPropagators.create(TextMapPropagator.composite(
//                        W3CTraceContextPropagator.getInstance(),
//                        W3CBaggagePropagator.getInstance()
//                )))
//                .buildAndRegisterGlobal();
//
//        OpenTelemetryAppender.install(openTelemetry);
//
//        return openTelemetry;
//    }

}
