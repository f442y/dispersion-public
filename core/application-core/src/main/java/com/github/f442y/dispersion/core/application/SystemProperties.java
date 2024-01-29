package com.github.f442y.dispersion.core.application;

public class SystemProperties {
    public static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
    public static final int DEFAULT_VIRTUAL_THREAD_BUFFER_SIZE = 2 * AVAILABLE_PROCESSORS;
}
