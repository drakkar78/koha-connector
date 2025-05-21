package com.upeu.connector;

import com.upeu.connector.handler.PatronHandler;
import com.upeu.connector.model.Patron;
import com.upeu.connector.operations.KohaCrudOperations;
import com.upeu.connector.util.EndpointRegistry;
import org.identityconnectors.framework.common.exceptions.ConnectorException;
import org.identityconnectors.framework.common.objects.*;
import org.identityconnectors.framework.common.objects.filter.*;
import org.identityconnectors.framework.spi.*;

import java.util.List;
import java.util.Set;

public class KohaConnector implements Connector,
        CreateOp, UpdateOp, DeleteOp, SearchOp<String>, SchemaOp, TestOp {

    private KohaCrudOperations crudOperations;
    private EndpointRegistry endpointRegistry;
    private KohaConfiguration configuration;

    @Override
    public void init(Configuration configuration) {
        if (!(configuration instanceof KohaConfiguration)) {
            throw new IllegalArgumentException("Configuración inválida");
        }
        this.configuration = (KohaConfiguration) configuration;
        this.endpointRegistry = new EndpointRegistry(this.configuration);
        this.crudOperations = new KohaCrudOperations(endpointRegistry);
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

    // Opcional: para pruebas unitarias o extensiones
    public PatronHandler createPatronHandler() {
        return new PatronHandler(endpointRegistry) {
            @Override
            public List<Patron> visitEqualsFilter(Void unused, EqualsFilter equalsFilter) {
                return List.of(); // implementar si se necesita lógica aquí directamente
            }

            // Demás filtros aún no implementados directamente
        };
    }
}
