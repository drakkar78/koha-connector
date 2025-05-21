package com.upeu.connector;

import com.upeu.connector.operations.KohaCrudOperations;
import com.upeu.connector.util.EndpointRegistry;
import com.upeu.connector.util.SchemaRegistry;
import org.identityconnectors.framework.common.exceptions.ConnectorException;
import org.identityconnectors.framework.common.objects.*;
import org.identityconnectors.framework.common.objects.filter.Filter;
import org.identityconnectors.framework.common.objects.filter.FilterTranslator;
import org.identityconnectors.framework.spi.Connector;
import org.identityconnectors.framework.spi.Configuration;
import org.identityconnectors.framework.spi.operations.*;
import org.identityconnectors.framework.common.objects.ConnectorMessages;

import java.util.List;
import java.util.Set;

public class KohaConnector implements Connector,
        CreateOp, UpdateOp, DeleteOp, SearchOp<Filter>, SchemaOp, TestOp {

    private KohaCrudOperations crudOperations;
    private EndpointRegistry endpointRegistry;
    private KohaConfiguration configuration;
    private SchemaRegistry schemaRegistry;

    @Override
    public void init(Configuration configuration) {
        if (!(configuration instanceof KohaConfiguration)) {
            throw new IllegalArgumentException("Configuración inválida");
        }

        this.configuration = (KohaConfiguration) configuration;
        this.endpointRegistry = new EndpointRegistry(this.configuration);

        // ✅ Obtener mensajes localizados desde la configuración
        ConnectorMessages messages = this.configuration.getConnectorMessages();

        // ✅ Inicializar el registry con los mensajes
        this.schemaRegistry = new SchemaRegistry(messages);

        // ✅ Inicializar operaciones CRUD con el registry
        this.crudOperations = new KohaCrudOperations(endpointRegistry, schemaRegistry);
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

    @Override
    public void dispose() {
        // Nada que liberar aún
    }

    @Override
    public void test() {
        if (!endpointRegistry.ping()) {
            throw new ConnectorException("La API de Koha no respondió al test.");
        }
    }

    @Override
    public FilterTranslator<Filter> createFilterTranslator(ObjectClass objectClass, OperationOptions options) {
        return filter -> List.of(filter); // Retorna el filtro tal como está
    }

    @Override
    public Uid create(ObjectClass objectClass, Set<Attribute> attributes, OperationOptions options) {
        return crudOperations.create(objectClass, attributes, options);
    }

    @Override
    public Uid update(ObjectClass objectClass, Uid uid, Set<Attribute> attributes, OperationOptions options) {
        return crudOperations.update(objectClass, uid, attributes, options);
    }

    @Override
    public void delete(ObjectClass objectClass, Uid uid, OperationOptions options) {
        crudOperations.delete(objectClass, uid, options);
    }

    @Override
    public void executeQuery(ObjectClass objectClass, Filter filter, ResultsHandler handler, OperationOptions options) {
        crudOperations.search(objectClass, filter, handler, options);
    }

    @Override
    public Schema schema() {
        return crudOperations.schema();
    }
}
