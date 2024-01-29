package com.github.f442y.dispersion.core.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SpringEvents {
    @Autowired
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
