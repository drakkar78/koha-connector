package com.upeu.connector;

import com.upeu.connector.operations.KohaCrudOperations;
import com.upeu.connector.util.EndpointRegistry;
import com.upeu.connector.util.SchemaRegistry;
import org.identityconnectors.framework.common.exceptions.ConnectorException;
import org.identityconnectors.framework.common.objects.*;
import org.identityconnectors.framework.common.objects.filter.Filter;
import org.identityconnectors.framework.common.objects.filter.FilterTranslator;
import org.identityconnectors.framework.spi.Connector;
import org.identityconnectors.framework.spi.ConnectorClass;
import org.identityconnectors.framework.spi.Configuration;
import org.identityconnectors.framework.spi.operations.*;

import java.util.List;
import java.util.Set;

@ConnectorClass(
        displayNameKey = "koha.connector.display",
        configurationClass = KohaConfiguration.class
)
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

        // Cargar mensajes localizados
        ConnectorMessages messages = this.configuration.getConnectorMessages();

        // Registrar schemas localizados
        this.schemaRegistry = new SchemaRegistry(messages);

        // Inicializar operaciones
        this.crudOperations = new KohaCrudOperations(endpointRegistry, schemaRegistry, messages);
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

    @Override
    public void dispose() {
        // Liberación de recursos si es necesario
    }

    @Override
    public void test() {
        if (!endpointRegistry.ping()) {
            throw new ConnectorException("La API de Koha no respondió al test.");
        }
    }

    @Override
    public FilterTranslator<Filter> createFilterTranslator(ObjectClass objectClass, OperationOptions options) {
        return filter -> List.of(filter); // Delega el filtro directamente
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
