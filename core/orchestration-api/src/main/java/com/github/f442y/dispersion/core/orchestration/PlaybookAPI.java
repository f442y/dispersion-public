package com.github.f442y.dispersion.core.orchestration;

import java.util.Collection;

public interface PlaybookAPI {
    Collection<? extends PlayTaskAPI> getAllPlayTaskIds();
}
