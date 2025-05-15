package com.upeu.connector.handler;

import com.upeu.connector.client.KohaClient;
import com.upeu.connector.util.EndpointRegistry;
import org.json.JSONObject;

public abstract class BaseHandler {

    protected final KohaClient kohaClient;
    protected final EndpointRegistry endpointRegistry; // <--- AGREGA ESTO

    public BaseHandler(EndpointRegistry endpointRegistry) {
        this.endpointRegistry = endpointRegistry;       // <--- Y ASÍGNALO
        this.kohaClient = new KohaClient(endpointRegistry.getConfig());
    }

    protected JSONObject getJson(String endpoint) {
        try {
            kohaClient.authenticate();
            return kohaClient.getJson(endpoint);
        } catch (Exception e) {
            throw new RuntimeException("Error en GET " + endpoint, e);
        }
    }

    protected JSONObject postJson(String endpoint, JSONObject payload) {
        try {
            kohaClient.authenticate();
            return kohaClient.postJson(endpoint, payload);
        } catch (Exception e) {
            throw new RuntimeException("Error en POST " + endpoint, e);
        }
    }

    protected JSONObject putJson(String endpoint, JSONObject payload) {
        try {
            kohaClient.authenticate();
            return kohaClient.putJson(endpoint, payload);
        } catch (Exception e) {
            throw new RuntimeException("Error en PUT " + endpoint, e);
        }
    }

    protected void delete(String endpoint) {
        try {
            kohaClient.authenticate();
            kohaClient.delete(endpoint);
        } catch (Exception e) {
            throw new RuntimeException("Error en DELETE " + endpoint, e);
        }
    }
}
