package com.github.f442y.dispersion.core.application;

import java.util.Collection;

public interface BootExceptions {
    Collection<? extends Exception> bootExceptions();

    void addAllBootExceptions(Collection<? extends Exception> exceptions);

    boolean skipStateMapBootValidation();
}
