package com.upeu.connector.handler;

import com.upeu.connector.client.KohaClient;
import com.upeu.connector.util.EndpointRegistry;

/**
 * Base genérica para todos los handlers de entidades Koha.
 * Provee autenticación y cliente HTTP listo para usar.
 */
public abstract class BaseHandler {

    protected final KohaClient kohaClient;
    protected final EndpointRegistry endpointRegistry;

    public BaseHandler(EndpointRegistry endpointRegistry) {
        this.endpointRegistry = endpointRegistry;
        this.kohaClient = new KohaClient(endpointRegistry.getConfig());
    }
}
