package com.github.f442y.dispersion.orchestrator.state.webapi.model;

import com.github.f442y.dispersion.orchestration.spi.entities.EntitySPI;
import com.github.f442y.dispersion.orchestrator.model.entity.PlayTaskEntityDependency;
import com.github.f442y.dispersion.orchestrator.zones.EntityZones;
import io.avaje.jsonb.Json;

public class EntityDependencyViewer {
    public static EntityDependencyView convertToView(PlayTaskEntityDependency playTaskEntityDependency) {
        return new EntityDependencyView(
                playTaskEntityDependency.getId(),
                playTaskEntityDependency.getPlayTaskToRun().getId(),
                playTaskEntityDependency.getRunEntityType(),
                playTaskEntityDependency.getRunEntityZone(),
                playTaskEntityDependency.getPlayTaskSource().getId(),
                playTaskEntityDependency.getSourceEntityType(),
                playTaskEntityDependency.getSourceEntityZone(),
                playTaskEntityDependency.getRunEntityType().equals(playTaskEntityDependency.getSourceEntityType()),
                playTaskEntityDependency.getRunEntityZone().equals(playTaskEntityDependency.getSourceEntityZone())
        );
    }

    public static SourceDependencyView convertToSourceView(PlayTaskEntityDependency playTaskEntityDependency) {
        return new SourceDependencyView(
                playTaskEntityDependency.getId(),
                playTaskEntityDependency.getPlayTaskSource().getId(),
                playTaskEntityDependency.getRunEntityType(),
                playTaskEntityDependency.getRunEntityZone(),
                playTaskEntityDependency.getSourceEntityType(),
                playTaskEntityDependency.getSourceEntityZone(),
                playTaskEntityDependency.getRunEntityType().equals(playTaskEntityDependency.getSourceEntityType()),
                playTaskEntityDependency.getRunEntityZone().equals(playTaskEntityDependency.getSourceEntityZone())
        );
    }

    public static ForwardDependencyView convertToForwardView(PlayTaskEntityDependency playTaskEntityDependency) {
        return new ForwardDependencyView(
                playTaskEntityDependency.getId(),
                playTaskEntityDependency.getPlayTaskToRun().getId(),
                playTaskEntityDependency.getRunEntityType(),
                playTaskEntityDependency.getRunEntityZone(),
                playTaskEntityDependency.getSourceEntityType(),
                playTaskEntityDependency.getSourceEntityZone(),
                playTaskEntityDependency.getRunEntityType().equals(playTaskEntityDependency.getSourceEntityType()),
                playTaskEntityDependency.getRunEntityZone().equals(playTaskEntityDependency.getSourceEntityZone())
        );
    }

    @Json
    public record EntityDependencyView(String entityDependencyId, String playTaskToRunId, EntitySPI runEntityType,
                                       EntityZones runEntityZone, String playTaskSourceId, EntitySPI sourceEntityType,
                                       EntityZones sourceEntityZone, Boolean typeMatches, Boolean zoneMatches) {}

    @Json
    public record SourceDependencyView(String entityDependencyId, String playTaskSourceId, EntitySPI runEntityType,
                                       EntityZones runEntityZone, EntitySPI sourceEntityType,
                                       EntityZones sourceEntityZone, Boolean typeMatches, Boolean zoneMatches) {}

    @Json
    public record ForwardDependencyView(String entityDependencyId, String playTaskToRunId, EntitySPI runEntityType,
                                        EntityZones runEntityZone, EntitySPI sourceEntityType,
                                        EntityZones sourceEntityZone, Boolean typeMatches, Boolean zoneMatches) {}
}
