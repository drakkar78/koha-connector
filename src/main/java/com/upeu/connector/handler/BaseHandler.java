package com.upeu.connector.handler;

import com.upeu.connector.client.KohaClient;
import com.upeu.connector.util.EndpointRegistry;
import org.json.JSONObject;

public abstract class BaseHandler {

    protected final KohaClient kohaClient;
    protected final EndpointRegistry endpointRegistry;

    public BaseHandler(EndpointRegistry endpointRegistry) {
        this.endpointRegistry = endpointRegistry;
        this.kohaClient = new KohaClient(endpointRegistry.getConfig());
    }

    protected JSONObject getJson(String endpoint) {
        return retryAuthIfNeeded(() -> kohaClient.getJson(endpoint), "GET", endpoint);
    }

    protected JSONObject postJson(String endpoint, JSONObject payload) {
        return retryAuthIfNeeded(() -> kohaClient.postJson(endpoint, payload), "POST", endpoint);
    }

    protected JSONObject putJson(String endpoint, JSONObject payload) {
        return retryAuthIfNeeded(() -> kohaClient.putJson(endpoint, payload), "PUT", endpoint);
    }

    protected void delete(String endpoint) {
        retryAuthIfNeeded(() -> {
            kohaClient.delete(endpoint);
            return null;
        }, "DELETE", endpoint);
    }

    // Genérico: intenta ejecutar operación, si da error 401, reautentica
    private <T> T retryAuthIfNeeded(Operation<T> op, String method, String endpoint) {
        try {
            if (kohaClient.getToken() == null) {
                kohaClient.authenticate();
            }
            return op.run();
        } catch (Exception first) {
            if (first.getMessage() != null && first.getMessage().contains("401")) {
                try {
                    kohaClient.authenticate(); // Retry con nuevo token
                    return op.run();
                } catch (Exception second) {
                    throw new RuntimeException("Error tras reintentar " + method + " " + endpoint, second);
                }
            } else {
                throw new RuntimeException("Error en " + method + " " + endpoint, first);
            }
        }
    }

    @FunctionalInterface
    private interface Operation<T> {
        T run() throws Exception;
    }
}
