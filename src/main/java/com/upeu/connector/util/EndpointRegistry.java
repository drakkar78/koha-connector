package com.upeu.connector.util;

import com.upeu.connector.KohaConfiguration;

/**
 * Encapsula la URL base y lógica para construir endpoints de Koha.
 */
public class EndpointRegistry {

    private final String baseUrl;

    public EndpointRegistry(KohaConfiguration config) {
        this.baseUrl = config.getBaseUrl();
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public boolean ping() {
        // Lógica real de ping vendrá luego. Por ahora, simula OK.
        return baseUrl != null && !baseUrl.isBlank();
    }
}
