package com.github.f442y.dispersion.core.orchestration;

public interface OrchestrationZoneTypeAPI<ENTITY_ZONE_ENUM extends Enum<ENTITY_ZONE_ENUM> & OrchestrationZoneTypeAPI<ENTITY_ZONE_ENUM>> {
    Class<? extends OrchestrationZoneAPI<ENTITY_ZONE_ENUM>> getZoneTypeClass();
}
