package com.github.f442y.dispersion.core.orchestration;

public interface OrchestrationZoneAPI<ENTITY_ZONE_ENUM extends Enum<ENTITY_ZONE_ENUM> & OrchestrationZoneTypeAPI<ENTITY_ZONE_ENUM>> {
    ENTITY_ZONE_ENUM getEntityTypeEnum();

    String getId();

    void setId(String id);
}
