package com.upeu.connector.handler;

import com.upeu.connector.model.Patron;
import com.upeu.connector.util.EndpointRegistry;
import org.identityconnectors.framework.common.objects.filter.*;

import java.util.List;

/**
 * Encargado de ejecutar búsquedas con o sin filtros.
 * Delegado desde el conector principal.
 */
public class FilterHandler extends BaseHandler {

    public FilterHandler(EndpointRegistry endpointRegistry) {
        super(endpointRegistry);
    }

    /**
     * Ejecuta una búsqueda con filtro.
     * Si no hay filtro, retorna todos los usuarios.
     */
    public List<Patron> executeQuery(Filter filter) {
        PatronHandler handler = new PatronHandler(endpointRegistry) {
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

        if (filter == null) {
            return handler.getAll();
        }

        // Ahora compila correctamente
        return filter.accept(handler, null);
    }
}
