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
            public List<Patron> visitEqualsFilter(Void unused, EqualsFilter filter) {
                try {
                    Attribute attr = filter.getAttribute();
                    String attrName = attr.getName();
                    String attrValue = attr.getValue().get(0).toString();

                    List<Patron> result = new ArrayList<>();
                    JSONArray jsonArray = new JSONArray(kohaClient.getAllPatrons());

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject json = jsonArray.getJSONObject(i);
                        if (json.has(attrName) && attrValue.equals(json.optString(attrName))) {
                            result.add(Patron.fromJson(json));
                        }
                    }

                    return result;
                } catch (Exception e) {
                    throw new RuntimeException("Error aplicando filtro de igualdad", e);
                }
            }

            // Los demás filtros aún no están implementados
            @Override public List<Patron> visitAndFilter(Void unused, AndFilter f) { return List.of(); }
            @Override public List<Patron> visitOrFilter(Void unused, OrFilter f) { return List.of(); }
            @Override public List<Patron> visitNotFilter(Void unused, NotFilter f) { return List.of(); }
            @Override public List<Patron> visitStartsWithFilter(Void unused, StartsWithFilter f) { return List.of(); }
            @Override public List<Patron> visitEndsWithFilter(Void unused, EndsWithFilter f) { return List.of(); }
            @Override public List<Patron> visitContainsFilter(Void unused, ContainsFilter f) { return List.of(); }
            @Override public List<Patron> visitGreaterThanFilter(Void unused, GreaterThanFilter f) { return List.of(); }
            @Override public List<Patron> visitGreaterThanOrEqualFilter(Void unused, GreaterThanOrEqualFilter f) { return List.of(); }
            @Override public List<Patron> visitLessThanFilter(Void unused, LessThanFilter f) { return List.of(); }
            @Override public List<Patron> visitLessThanOrEqualFilter(Void unused, LessThanOrEqualFilter f) { return List.of(); }
            @Override public List<Patron> visitContainsAllValuesFilter(Void unused, ContainsAllValuesFilter f) { return List.of(); }
            @Override public List<Patron> visitEqualsIgnoreCaseFilter(Void unused, EqualsIgnoreCaseFilter f) { return List.of(); }
            @Override public List<Patron> visitExtendedFilter(Void unused, Filter f) { return List.of(); }
        };

        if (filter == null) {
            return handler.getAll();
        }

        return filter.accept(handler, null);
    }
}
