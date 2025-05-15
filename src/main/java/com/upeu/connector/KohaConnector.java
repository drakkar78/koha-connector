package com.upeu.connector;

import com.upeu.connector.handler.PatronHandler;
import com.upeu.connector.util.EndpointRegistry;
import org.identityconnectors.framework.common.exceptions.ConnectorException;
import org.identityconnectors.framework.spi.Connector;

public class KohaConnector implements Connector {

    private EndpointRegistry endpointRegistry;

    /**
     * Punto de entrada para test.
     */
    public void test() {
        if (!endpointRegistry.ping()) {
            throw new ConnectorException("La API de Koha no respondió al test.");
        }
    }

    /**
     * Punto de acceso al handler de usuarios.
     */
    public PatronHandler createPatronHandler() {
        return new PatronHandler(endpointRegistry);
    }

    // Getters/Setters para inyectar la configuración, si aplica
    public void setEndpointRegistry(EndpointRegistry endpointRegistry) {
        this.endpointRegistry = endpointRegistry;
    }
}
