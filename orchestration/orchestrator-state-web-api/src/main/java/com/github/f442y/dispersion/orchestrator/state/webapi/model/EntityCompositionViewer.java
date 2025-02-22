package com.github.f442y.dispersion.orchestrator.state.webapi.model;

import com.github.f442y.dispersion.orchestration.spi.entities.EntitySPI;
import com.github.f442y.dispersion.orchestrator.model.entity.PlayTaskEntityType;
import io.avaje.jsonb.Json;

public class EntityCompositionViewer {

    public static EntityCompositionView convertToView(PlayTaskEntityType playTaskEntityType) {
        return new EntityCompositionView(playTaskEntityType.getId(), playTaskEntityType.getEntityType());
    }

    @Json
    public record EntityCompositionView(String entityCompositionId, EntitySPI entityType) {}
}
