package com.github.f442y.dispersion.core.application;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Semaphore;

@Component
public class ApplicationStatic implements ApplicationConfig {
    private static final boolean SKIP_STATE_MAP_VALIDATION = false;
    private static final int APPLICATION_PERMIT_LIMIT = 10_000_000;
    private static final Semaphore APPLICATION_SEMAPHORE = new Semaphore(APPLICATION_PERMIT_LIMIT);
    private final Collection<Exception> exceptions = new ArrayList<>();

    public void acquireThreadPermits(Semaphore serviceBufferSemaphore) throws InterruptedException {
        APPLICATION_SEMAPHORE.acquire();
        serviceBufferSemaphore.acquire();
    }

    public void releaseThreadPermits(Semaphore serviceBufferSemaphore) {
        APPLICATION_SEMAPHORE.release();
        serviceBufferSemaphore.release();
    }

    @Override
    public Collection<Exception> bootExceptions() {
        return exceptions;
    }

    @Override
    public void addAllBootExceptions(Collection<? extends Exception> exceptions) {
        this.bootExceptions().addAll(exceptions);
    }

    @Override
    public boolean skipStateMapBootValidation() {
        return SKIP_STATE_MAP_VALIDATION;
    }
}
