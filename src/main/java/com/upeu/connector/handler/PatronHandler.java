package com.upeu.connector.handler;

import com.upeu.connector.model.Patron;
import com.upeu.connector.util.EndpointRegistry;
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

    public Patron getById(String borrowernumber) {
        JSONObject json = getJson("/api/v1/patrons/" + borrowernumber);
        return Patron.fromJson(json);
    }

    public Patron create(Patron patron) {
        JSONObject payload = patron.toJson();
        JSONObject created = postJson("/api/v1/patrons", payload);
        return Patron.fromJson(created);
    }

    public Patron update(String borrowernumber, Patron patron) {
        JSONObject payload = patron.toJson();
        JSONObject updated = putJson("/api/v1/patrons/" + borrowernumber, payload);
        return Patron.fromJson(updated);
    }

    public boolean deleteByBorrowerNumber(String borrowernumber) {
        delete("/api/v1/patrons/" + borrowernumber);
        return true;
    }
}
