package com.upeu.connector.handler;

import com.upeu.connector.client.KohaClient;
import com.upeu.connector.model.Patron;
import com.upeu.connector.util.EndpointRegistry;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Lógica de gestión de usuarios (patrons) en Koha.
 */
public class PatronHandler {

    private final KohaClient kohaClient;

    public PatronHandler(EndpointRegistry endpointRegistry) {
        this.kohaClient = new KohaClient(endpointRegistry.getConfig());
    }

    /**
     * Lista todos los usuarios registrados en Koha.
     */
    public List<Patron> getAll() {
        try {
            kohaClient.authenticate();
            String jsonResponse = kohaClient.getAllPatrons();
            JSONArray jsonArray = new JSONArray(jsonResponse);
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
     * Obtiene un usuario por su borrowernumber (ID interno de Koha).
     */
    public Patron getById(String borrowernumber) {
        try {
            kohaClient.authenticate();
            JSONObject json = kohaClient.getJson("/api/v1/patrons/" + borrowernumber);
            return Patron.fromJson(json);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el usuario con ID " + borrowernumber, e);
        }
    }

    /**
     * Crea un nuevo usuario.
     */
    public Patron create(Patron patron) {
        try {
            kohaClient.authenticate();
            JSONObject payload = patron.toJson();
            JSONObject created = kohaClient.postJson("/api/v1/patrons", payload);
            return Patron.fromJson(created);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear usuario", e);
        }
    }

    /**
     * Actualiza un usuario existente por su ID.
     */
    public Patron update(String borrowernumber, Patron patron) {
        try {
            kohaClient.authenticate();
            JSONObject payload = patron.toJson();
            JSONObject updated = kohaClient.putJson("/api/v1/patrons/" + borrowernumber, payload);
            return Patron.fromJson(updated);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar usuario con ID " + borrowernumber, e);
        }
    }

    /**
     * Elimina un usuario por su borrowernumber.
     */
    public boolean delete(String borrowernumber) {
        try {
            kohaClient.authenticate();
            kohaClient.delete("/api/v1/patrons/" + borrowernumber);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar usuario con ID " + borrowernumber, e);
        }
    }
}
