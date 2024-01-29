package com.github.f442y.dispersion.applications;

import com.github.f442y.dispersion.core.application.ApplicationStatic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = ApplicationStatic.PACKAGE_ROOT)
public class DispersionSamplesApplication {
    public static void main(String[] args) {
        SpringApplication.run(DispersionSamplesApplication.class, args);
    }
}