package com.upeu.connector;

import com.upeu.connector.handler.PatronHandler;
import com.upeu.connector.schema.PatronSchema;
import com.upeu.connector.util.EndpointRegistry;
import com.upeu.connector.util.SchemaRegistry;
import org.identityconnectors.framework.common.exceptions.ConnectorException;
import org.identityconnectors.framework.spi.Configuration;
import org.identityconnectors.framework.spi.Connector;

/**
 * Conector Koha compatible con ConnId (MidPoint).
 */
public class KohaConnector implements Connector {

    private KohaConfiguration configuration;
    private EndpointRegistry endpointRegistry;
    private SchemaRegistry schemaRegistry;

    @Override
    public void init(Configuration config) {
        this.configuration = (KohaConfiguration) config;
        this.endpointRegistry = new EndpointRegistry(configuration);
        this.schemaRegistry = new SchemaRegistry();

        this.schemaRegistry.register(new PatronSchema());
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

    @Override
    public void dispose() {
        // Limpiar recursos si es necesario
    }

    public void test() {
        if (!endpointRegistry.ping()) {
            throw new ConnectorException("La API de Koha no respondió al test.");
        }
    }

    // Opcional: punto de acceso al handler de patrons
    public PatronHandler createPatronHandler() {
        return new PatronHandler(endpointRegistry);
    }

    public SchemaRegistry getSchemaRegistry() {
        return schemaRegistry;
    }

    public EndpointRegistry getEndpointRegistry() {
        return endpointRegistry;
    }
}
