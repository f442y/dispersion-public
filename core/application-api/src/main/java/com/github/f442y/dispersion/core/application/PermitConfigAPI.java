package com.github.f442y.dispersion.core.application;

import java.util.concurrent.Semaphore;

public sealed interface PermitConfigAPI permits ApplicationConfig {

    void acquireThreadPermits(Semaphore serviceBufferSemaphore) throws InterruptedException;

    void releaseThreadPermits(Semaphore serviceBufferSemaphore);
}
