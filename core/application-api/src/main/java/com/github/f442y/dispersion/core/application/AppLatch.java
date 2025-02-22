package com.github.f442y.dispersion.core.application;

import java.util.concurrent.CountDownLatch;

public interface AppLatch {
    CountDownLatch applicationExitLatch = new CountDownLatch(1);

    static void stopApp() {
        applicationExitLatch.countDown();
    }
}
