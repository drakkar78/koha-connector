package com.upeu.connector.handler;

import com.upeu.connector.model.Patron;
import com.upeu.connector.util.EndpointRegistry;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Lógica de gestión de usuarios (patrons) en Koha.
 */
public class PatronHandler extends BaseHandler {

    public PatronHandler(EndpointRegistry endpointRegistry) {
        super(endpointRegistry);
    }

    public List<Patron> getAll() throws Exception {
        JSONArray jsonArray = new JSONArray(kohaClient.getAllPatrons());
        List<Patron> patrons = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            patrons.add(Patron.fromJson(jsonArray.getJSONObject(i)));
        }
        return patrons;
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
