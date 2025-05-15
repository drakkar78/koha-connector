package com.upeu.connector.util;

import com.upeu.connector.KohaConfiguration;

/**
 * Encapsula la URL base y lógica para construir endpoints de Koha.
 */
public class EndpointRegistry {

    private final KohaConfiguration configuration;
    private final String baseUrl;

    public EndpointRegistry(KohaConfiguration config) {
        this.configuration = config;
        this.baseUrl = config.getBaseUrl();
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public KohaConfiguration getConfig() {
        return configuration;
    }

    public boolean ping() {
        // Lógica real de ping vendrá luego. Por ahora, simula OK.
        return baseUrl != null && !baseUrl.isBlank();
    }
}
