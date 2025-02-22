package com.github.f442y.dispersion.orchestration.spi.entities;

import com.github.f442y.dispersion.core.orchestration.OrchestrationContextAPI;
import com.github.f442y.dispersion.core.orchestration.OrchestrationEntityAPI;

public interface SampleOrchestrationEntity {
    String TYPE_CODE = "SampleOrchestrationEntity";

    interface EntityAPI extends OrchestrationEntityAPI {
        @Override
        default String getOrchestrationEntityTypeCode() {
            return TYPE_CODE;
        }
    }

    interface EntityContextPlugin extends OrchestrationContextAPI.ContextPlugin {
        EntityAPI contextSampleOrchestrationEntity();
    }

    interface SingleEntityContextAPI extends OrchestrationContextAPI, EntityContextPlugin {}
}