package com.upeu.connector.handler;

import com.upeu.connector.client.KohaClient;
import com.upeu.connector.model.Patron;
import com.upeu.connector.util.EndpointRegistry;
import org.identityconnectors.framework.common.objects.filter.*;
import org.json.JSONObject;

import java.util.List;

/**
 * Base genérica para todos los handlers de entidades Koha.
 * Provee autenticación y cliente HTTP listo para usar.
 */
public abstract class BaseHandler {

    protected final KohaClient kohaClient;
    protected final EndpointRegistry endpointRegistry;

    public BaseHandler(EndpointRegistry endpointRegistry) {
        this.endpointRegistry = endpointRegistry;
        this.kohaClient = new KohaClient(endpointRegistry.getConfig());
        try {
            this.kohaClient.authenticate(); // Autenticación única al inicializar handler
        } catch (Exception e) {
            throw new RuntimeException("Error autenticando con Koha API", e);
        }
    }

    /**
     * Atajo para realizar GET de JSON autenticado.
     */
    protected JSONObject getJson(String endpoint) {
        try {
            return kohaClient.getJson(endpoint);
        } catch (Exception e) {
            throw new RuntimeException("GET falló en " + endpoint, e);
        }
    }

    /**
     * Atajo para realizar POST de JSON autenticado.
     */
    protected JSONObject postJson(String endpoint, JSONObject body) {
        try {
            return kohaClient.postJson(endpoint, body);
        } catch (Exception e) {
            throw new RuntimeException("POST falló en " + endpoint, e);
        }
    }

    /**
     * Atajo para realizar PUT de JSON autenticado.
     */
    protected JSONObject putJson(String endpoint, JSONObject body) {
        try {
            return kohaClient.putJson(endpoint, body);
        } catch (Exception e) {
            throw new RuntimeException("PUT falló en " + endpoint, e);
        }
    }

    /**
     * Atajo para realizar DELETE autenticado.
     */
    protected void delete(String endpoint) {
        try {
            kohaClient.delete(endpoint);
        } catch (Exception e) {
            throw new RuntimeException("DELETE falló en " + endpoint, e);
        }
    }

    public abstract List<Patron> visitEqualsFilter(EqualsFilter filter, Void param);

    // Los siguientes métodos no están implementados aún
    public abstract List<Patron> visitAndFilter(Filter left, Filter right, Void param);

    public abstract List<Patron> visitOrFilter(Filter left, Filter right, Void param);

    public abstract List<Patron> visitNotFilter(Filter filter, Void param);

    public abstract List<Patron> visitContainsFilter(ContainsFilter filter, Void param);

    public abstract List<Patron> visitStartsWithFilter(StartsWithFilter filter, Void param);

    public abstract List<Patron> visitEndsWithFilter(EndsWithFilter filter, Void param);

    public abstract List<Patron> visitGreaterThanFilter(GreaterThanFilter filter, Void param);

    public abstract List<Patron> visitGreaterThanOrEqualFilter(GreaterThanOrEqualFilter filter, Void param);

    public abstract List<Patron> visitLessThanFilter(LessThanFilter filter, Void param);

    public abstract List<Patron> visitLessThanOrEqualFilter(LessThanOrEqualFilter filter, Void param);

    public abstract List<Patron> visitContainsAllValuesFilter(ContainsAllValuesFilter filter, Void param);
}
