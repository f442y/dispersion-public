package com.github.f442y.dispersion.orchestrator.zones;

import com.github.f442y.dispersion.core.orchestration.OrchestrationZoneAPI;
import com.github.f442y.dispersion.core.orchestration.OrchestrationZoneTypeAPI;
import com.github.f442y.dispersion.orchestrator.zones.interfaces.TestZoneAPI;
import com.github.f442y.dispersion.orchestrator.zones.interfaces.Zone2API;

// todo: change Zone to Partition
public enum EntityZones implements OrchestrationZoneTypeAPI<EntityZones> {
    TestZone(TestZoneAPI.class), Zone2(Zone2API.class);

    private final Class<? extends OrchestrationZoneAPI<EntityZones>> orchestrationZoneType;

    EntityZones(Class<? extends OrchestrationZoneAPI<EntityZones>> orchestrationZoneType) {
        this.orchestrationZoneType = orchestrationZoneType;
    }

    @Override
    public Class<? extends OrchestrationZoneAPI<EntityZones>> getZoneTypeClass() {
        return orchestrationZoneType;
    }
}
