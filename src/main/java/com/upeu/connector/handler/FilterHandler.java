package com.upeu.connector.handler;

import com.upeu.connector.model.Patron;
import com.upeu.connector.util.EndpointRegistry;
import org.identityconnectors.framework.common.objects.Attribute;
import org.identityconnectors.framework.common.objects.filter.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Maneja filtros de búsqueda recibidos desde MidPoint (ConnId).
 * Actualmente solo soporta búsquedas exactas (EqualsFilter).
 */
public abstract class FilterHandler extends BaseHandler implements FilterVisitor<List<Patron>, Void> {

    public FilterHandler(EndpointRegistry endpointRegistry) {
        super(endpointRegistry);
    }

    /**
     * Ejecuta una consulta de filtrado.
     * Si no hay filtro, devuelve todos los usuarios.
     */
    public List<Patron> executeQuery(Filter filter) {
        if (filter == null) {
            try {
                return new PatronHandler(endpointRegistry) {
                    @Override
                    public List<Patron> visitEqualsFilter(EqualsFilter filter, Void param) {
                        return List.of();
                    }

                    @Override
                    public List<Patron> visitAndFilter(Filter left, Filter right, Void param) {
                        return List.of();
                    }

                    @Override
                    public List<Patron> visitOrFilter(Filter left, Filter right, Void param) {
                        return List.of();
                    }

                    @Override
                    public List<Patron> visitNotFilter(Filter filter, Void param) {
                        return List.of();
                    }

                    @Override
                    public List<Patron> visitContainsFilter(ContainsFilter filter, Void param) {
                        return List.of();
                    }

                    @Override
                    public List<Patron> visitStartsWithFilter(StartsWithFilter filter, Void param) {
                        return List.of();
                    }

                    @Override
                    public List<Patron> visitEndsWithFilter(EndsWithFilter filter, Void param) {
                        return List.of();
                    }

                    @Override
                    public List<Patron> visitGreaterThanFilter(GreaterThanFilter filter, Void param) {
                        return List.of();
                    }

                    @Override
                    public List<Patron> visitGreaterThanOrEqualFilter(GreaterThanOrEqualFilter filter, Void param) {
                        return List.of();
                    }

                    @Override
                    public List<Patron> visitLessThanFilter(LessThanFilter filter, Void param) {
                        return List.of();
                    }

                    @Override
                    public List<Patron> visitLessThanOrEqualFilter(LessThanOrEqualFilter filter, Void param) {
                        return List.of();
                    }

                    @Override
                    public List<Patron> visitContainsAllValuesFilter(ContainsAllValuesFilter filter, Void param) {
                        return List.of();
                    }
                }.getAll();
            } catch (Exception e) {
                throw new RuntimeException("Error al recuperar usuarios", e);
            }
        }
        return filter.accept(this, null);
    }

    /**
     * Aplica un filtro de igualdad (attribute == value).
     */
    @Override
    public List<Patron> visitEqualsFilter(EqualsFilter filter, Void param) {
        Attribute attr = filter.getAttribute();
        String attrName = attr.getName();
        String attrValue = attr.getValue().get(0).toString();

        List<Patron> results = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(kohaClient.getAllPatrons());

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                if (json.has(attrName) && attrValue.equals(json.getString(attrName))) {
                    results.add(Patron.fromJson(json));
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Error aplicando filtro", e);
        }

        return results;
    }

    // Los siguientes métodos no están implementados aún
    @Override
    public List<Patron> visitAndFilter(Filter left, Filter right, Void param) {
        throw new UnsupportedOperationException("Filtro AND no soportado.");
    }

    @Override
    public List<Patron> visitOrFilter(Filter left, Filter right, Void param) {
        throw new UnsupportedOperationException("Filtro OR no soportado.");
    }

    @Override
    public List<Patron> visitNotFilter(Filter filter, Void param) {
        throw new UnsupportedOperationException("Filtro NOT no soportado.");
    }

    @Override
    public List<Patron> visitContainsFilter(ContainsFilter filter, Void param) {
        throw new UnsupportedOperationException("Filtro CONTAINS no soportado.");
    }

    @Override
    public List<Patron> visitStartsWithFilter(StartsWithFilter filter, Void param) {
        throw new UnsupportedOperationException("Filtro STARTSWITH no soportado.");
    }

    @Override
    public List<Patron> visitEndsWithFilter(EndsWithFilter filter, Void param) {
        throw new UnsupportedOperationException("Filtro ENDSWITH no soportado.");
    }

    @Override
    public List<Patron> visitGreaterThanFilter(GreaterThanFilter filter, Void param) {
        throw new UnsupportedOperationException("Filtro GREATERTHAN no soportado.");
    }

    @Override
    public List<Patron> visitGreaterThanOrEqualFilter(GreaterThanOrEqualFilter filter, Void param) {
        throw new UnsupportedOperationException("Filtro GREATERTHANOREQUAL no soportado.");
    }

    @Override
    public List<Patron> visitLessThanFilter(LessThanFilter filter, Void param) {
        throw new UnsupportedOperationException("Filtro LESSTHAN no soportado.");
    }

    @Override
    public List<Patron> visitLessThanOrEqualFilter(LessThanOrEqualFilter filter, Void param) {
        throw new UnsupportedOperationException("Filtro LESSTHANOREQUAL no soportado.");
    }

    @Override
    public List<Patron> visitContainsAllValuesFilter(ContainsAllValuesFilter filter, Void param) {
        throw new UnsupportedOperationException("Filtro CONTAINSALL no soportado.");
    }
}
