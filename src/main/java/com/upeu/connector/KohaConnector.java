package com.upeu.connector;

import com.upeu.connector.handler.PatronHandler;
import com.upeu.connector.model.Patron;
import com.upeu.connector.util.EndpointRegistry;
import org.identityconnectors.framework.common.exceptions.ConnectorException;
import org.identityconnectors.framework.common.objects.filter.*;
import org.identityconnectors.framework.spi.Configuration;
import org.identityconnectors.framework.spi.Connector;

import java.util.List;

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
        return new PatronHandler(endpointRegistry) {
            @Override
            public List<Patron> visitAndFilter(Void unused, AndFilter andFilter) {
                return List.of();
            }

            @Override
            public List<Patron> visitContainsFilter(Void unused, ContainsFilter containsFilter) {
                return List.of();
            }

            @Override
            public List<Patron> visitContainsAllValuesFilter(Void unused, ContainsAllValuesFilter containsAllValuesFilter) {
                return List.of();
            }

            @Override
            public List<Patron> visitEqualsFilter(Void unused, EqualsFilter equalsFilter) {
                return List.of();
            }

            @Override
            public List<Patron> visitExtendedFilter(Void unused, Filter filter) {
                return List.of();
            }

            @Override
            public List<Patron> visitGreaterThanFilter(Void unused, GreaterThanFilter greaterThanFilter) {
                return List.of();
            }

            @Override
            public List<Patron> visitGreaterThanOrEqualFilter(Void unused, GreaterThanOrEqualFilter greaterThanOrEqualFilter) {
                return List.of();
            }

            @Override
            public List<Patron> visitLessThanFilter(Void unused, LessThanFilter lessThanFilter) {
                return List.of();
            }

            @Override
            public List<Patron> visitLessThanOrEqualFilter(Void unused, LessThanOrEqualFilter lessThanOrEqualFilter) {
                return List.of();
            }

            @Override
            public List<Patron> visitNotFilter(Void unused, NotFilter notFilter) {
                return List.of();
            }

            @Override
            public List<Patron> visitOrFilter(Void unused, OrFilter orFilter) {
                return List.of();
            }

            @Override
            public List<Patron> visitStartsWithFilter(Void unused, StartsWithFilter startsWithFilter) {
                return List.of();
            }

            @Override
            public List<Patron> visitEndsWithFilter(Void unused, EndsWithFilter endsWithFilter) {
                return List.of();
            }

            @Override
            public List<Patron> visitEqualsIgnoreCaseFilter(Void unused, EqualsIgnoreCaseFilter equalsIgnoreCaseFilter) {
                return List.of();
            }
        };
    }
}
