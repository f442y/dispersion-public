package com.github.f442y.dispersion.orchestrator.state.webapi.model;

import com.github.f442y.dispersion.orchestrator.model.entity.PlayTaskEntityZone;
import com.github.f442y.dispersion.orchestrator.zones.EntityZones;
import io.avaje.jsonb.Json;

public class ZoneCompositionViewer {

    public static ZoneCompositionView convertToView(PlayTaskEntityZone playTaskEntityZone) {
        return new ZoneCompositionView(
                playTaskEntityZone.getId(),
                playTaskEntityZone.getEntityZone()
        );
    }

    @Json
    public record ZoneCompositionView(String zoneCompositionId, EntityZones entityZone) {}
}
