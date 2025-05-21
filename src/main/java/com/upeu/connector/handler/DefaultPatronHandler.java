package com.upeu.connector.handler;

import com.upeu.connector.model.Patron;
import com.upeu.connector.util.EndpointRegistry;
import org.identityconnectors.framework.common.objects.filter.*;

import java.util.Collections;
import java.util.List;

/**
 * Implementación básica para PatronHandler.
 * Todos los filtros retornan vacío hasta que se implementen.
 */
public class DefaultPatronHandler extends PatronHandler {

    public DefaultPatronHandler(EndpointRegistry endpointRegistry) {
        super(endpointRegistry);
    }

    @Override
    public List<Patron> visitAndFilter(Void param, AndFilter filter) {
        return Collections.emptyList();
    }

    @Override
    public List<Patron> visitContainsFilter(Void param, ContainsFilter filter) {
        return Collections.emptyList();
    }

    @Override
    public List<Patron> visitContainsAllValuesFilter(Void param, ContainsAllValuesFilter filter) {
        return Collections.emptyList();
    }

    @Override
    public List<Patron> visitEqualsFilter(Void param, EqualsFilter filter) {
        return super.visitEqualsFilter(filter, param);
    }

    @Override
    public List<Patron> visitExtendedFilter(Void param, Filter filter) {
        return Collections.emptyList();
    }

    @Override
    public List<Patron> visitGreaterThanFilter(Void param, GreaterThanFilter filter) {
        return Collections.emptyList();
    }

    @Override
    public List<Patron> visitGreaterThanOrEqualFilter(Void param, GreaterThanOrEqualFilter filter) {
        return Collections.emptyList();
    }

    @Override
    public List<Patron> visitLessThanFilter(Void param, LessThanFilter filter) {
        return Collections.emptyList();
    }

    @Override
    public List<Patron> visitLessThanOrEqualFilter(Void param, LessThanOrEqualFilter filter) {
        return Collections.emptyList();
    }

    @Override
    public List<Patron> visitNotFilter(Void param, NotFilter filter) {
        return Collections.emptyList();
    }

    @Override
    public List<Patron> visitOrFilter(Void param, OrFilter filter) {
        return Collections.emptyList();
    }

    @Override
    public List<Patron> visitStartsWithFilter(Void param, StartsWithFilter filter) {
        return Collections.emptyList();
    }

    @Override
    public List<Patron> visitEndsWithFilter(Void param, EndsWithFilter filter) {
        return Collections.emptyList();
    }

    @Override
    public List<Patron> visitEqualsIgnoreCaseFilter(Void param, EqualsIgnoreCaseFilter filter) {
        return Collections.emptyList();
    }
}
