package com.github.f442y.dispersion.orchestration.spi.providers;

import com.github.f442y.dispersion.core.orchestration.OrchestrationProviderTypeSPI;
import jakarta.annotation.Nonnull;

import java.util.HashMap;
import java.util.Objects;

public enum ProviderSPI implements OrchestrationProviderTypeSPI {
    SampleProvider(com.github.f442y.dispersion.orchestration.spi.providers.SampleProvider.PROVIDER_CODE);

    private final String providerCode;
    private static final HashMap<String, ProviderSPI> LOOKUP_MAP = new HashMap<>(ProviderSPI.values().length);

    static {
        for (ProviderSPI providerSPI : ProviderSPI.values()) {
            // null check
            if (providerSPI.providerCode() == null || Objects.equals("", providerSPI.providerCode)) {
                throw new IllegalArgumentException("Provider Code should not be null or empty string");
            }
            if (LOOKUP_MAP.containsKey(providerSPI.providerCode())) {
                throw new IllegalArgumentException("Provider Code should be unique for every entity type");
            }
            LOOKUP_MAP.put(providerSPI.providerCode(), providerSPI);
        }
    }

    ProviderSPI(@Nonnull String providerCode) {
        this.providerCode = providerCode;
    }

    @Override
    public String providerCode() {
        return this.providerCode;
    }

    public static ProviderSPI getFromCode(String entityTypeCode) {
        return LOOKUP_MAP.get(entityTypeCode);
    }
}