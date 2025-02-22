package com.github.f442y.dispersion.orchestration.spi.entities;

import com.github.f442y.dispersion.core.orchestration.OrchestrationEntityTypeSPI;
import jakarta.annotation.Nonnull;

import java.util.HashMap;
import java.util.Objects;

public enum EntitySPI implements OrchestrationEntityTypeSPI {
    Sample_OE(SampleOrchestrationEntity.TYPE_CODE);

    private final String entityTypeCode;
    private static final HashMap<String, EntitySPI> LOOKUP_MAP = new HashMap<>(EntitySPI.values().length);

    static {
        for (EntitySPI entitySPI : EntitySPI.values()) {
            // null check
            if (entitySPI.entityTypeCode() == null || Objects.equals("", entitySPI.entityTypeCode)) {
                throw new IllegalArgumentException("Entity Type Code should not be null or empty string");
            }
            if (LOOKUP_MAP.containsKey(entitySPI.entityTypeCode())) {
                throw new IllegalArgumentException("Entity Type Code should be unique for every entity type");
            }
            LOOKUP_MAP.put(entitySPI.entityTypeCode(), entitySPI);
        }
    }

    EntitySPI(@Nonnull String entityTypeCode) {
        this.entityTypeCode = entityTypeCode;
    }

    @Override
    public String entityTypeCode() {
        return this.entityTypeCode;
    }

    public static EntitySPI getFromCode(String entityTypeCode) {
        return LOOKUP_MAP.get(entityTypeCode);
    }
}