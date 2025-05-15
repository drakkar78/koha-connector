package com.upeu.connector.handler;

import com.upeu.connector.model.Patron;
import com.upeu.connector.util.EndpointRegistry;
import org.identityconnectors.framework.common.objects.Attribute;
import org.identityconnectors.framework.common.objects.filter.EqualsFilter;
import org.identityconnectors.framework.common.objects.filter.FilterVisitor;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Lógica de gestión de usuarios (patrons) en Koha.
 */
public abstract class PatronHandler extends BaseHandler implements FilterVisitor<List<Patron>, Void> {

    public PatronHandler(EndpointRegistry endpointRegistry) {
        super(endpointRegistry);
    }

    /**
     * Lista todos los usuarios registrados en Koha.
     */
    public List<Patron> getAll() {
        try {
            JSONArray jsonArray = new JSONArray(kohaClient.getAllPatrons());
            List<Patron> patrons = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject patronJson = jsonArray.getJSONObject(i);
                patrons.add(Patron.fromJson(patronJson));
            }

            return patrons;
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener lista de usuarios", e);
        }
    }

    /**
     * Obtiene un usuario por su número de tarjeta.
     */
    public Patron getById(String borrowernumber) {
        try {
            JSONObject json = kohaClient.getJson("/api/v1/patrons/" + borrowernumber);
            return Patron.fromJson(json);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener usuario con ID " + borrowernumber, e);
        }
    }

    /**
     * Crea un nuevo usuario en Koha.
     */
    public Patron create(Patron patron) {
        try {
            JSONObject payload = patron.toJson();
            JSONObject created = kohaClient.postJson("/api/v1/patrons", payload);
            return Patron.fromJson(created);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear usuario", e);
        }
    }

    /**
     * Actualiza un usuario existente.
     */
    public Patron update(String borrowernumber, Patron patron) {
        try {
            JSONObject payload = patron.toJson();
            JSONObject updated = kohaClient.putJson("/api/v1/patrons/" + borrowernumber, payload);
            return Patron.fromJson(updated);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar usuario con ID " + borrowernumber, e);
        }
    }

    /**
     * Elimina un usuario por número.
     */
    public boolean deleteByBorrowerNumber(String borrowernumber) {
        try {
            kohaClient.delete("/api/v1/patrons/" + borrowernumber);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar usuario con ID " + borrowernumber, e);
        }
    }

    /**
     * Soporte básico para filtro de igualdad (MidPoint).
     */

    public List<Patron> visitEqualsFilter(EqualsFilter filter, Void param) {
        Attribute attr = filter.getAttribute();
        String attrName = attr.getName();
        String attrValue = attr.getValue().get(0).toString();

        List<Patron> results = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(kohaClient.getAllPatrons());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                if (json.has(attrName) && attrValue.equals(json.optString(attrName))) {
                    results.add(Patron.fromJson(json));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error aplicando filtro de igualdad", e);
        }

        return results;
    }
}
