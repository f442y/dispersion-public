package com.github.f442y.dispersion.core.application;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

@Named
public class SpringEvents {
    private static final Logger log = LoggerFactory.getLogger(SpringEvents.class);
    @Inject
    ApplicationStatic applicationStatic;

    @EventListener({ContextRefreshedEvent.class})
    void contextRefreshedEvent(ContextRefreshedEvent contextRefreshedEvent) {
        log.info("context refreshed");
        if (!applicationStatic.bootExceptions().isEmpty()) {
            log.error("Runtime Boot Validation Exceptions Detected");
            log.info("Exiting Application.");
            System.exit(SpringApplication.exit(contextRefreshedEvent.getApplicationContext()));
        }
    }
}
