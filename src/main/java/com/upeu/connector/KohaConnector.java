package com.upeu.connector;

import com.upeu.connector.handler.PatronHandler;
import com.upeu.connector.util.EndpointRegistry;
import org.identityconnectors.framework.common.exceptions.ConnectorException;
import org.identityconnectors.framework.spi.Configuration;
import org.identityconnectors.framework.spi.Connector;

public class KohaConnector implements Connector {

    private EndpointRegistry endpointRegistry;
    private KohaConfiguration configuration;

    @Override
    public void init(Configuration configuration) {
        if (!(configuration instanceof KohaConfiguration)) {
            throw new IllegalArgumentException("Configuración inválida");
        }
        this.configuration = (KohaConfiguration) configuration;
        this.endpointRegistry = new EndpointRegistry(this.configuration);
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

    @Override
    public void dispose() {
        // Nada que liberar aún
    }

    public void test() {
        if (!endpointRegistry.ping()) {
            throw new ConnectorException("La API de Koha no respondió al test.");
        }
    }

    public PatronHandler createPatronHandler() {
        return new PatronHandler(endpointRegistry);
    }
}
