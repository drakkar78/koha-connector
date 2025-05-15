package com.upeu.connector.util;

import org.identityconnectors.framework.common.exceptions.*;
import org.json.JSONObject;

/**
 * Traduce respuestas de error de Koha a excepciones estándar ConnId.
 */
public class ErrorHandler {

    /**
     * Interpreta la respuesta de error de Koha y lanza excepción adecuada.
     *
     * @param statusCode código HTTP
     * @param body contenido de error opcional (puede ser JSON o texto)
     */
    public static void handleKohaError(int statusCode, String body) {
        String message = extractMessage(body);

        switch (statusCode) {
            case 400:
                throw new ConnectorException("Solicitud inválida: " + message);
            case 401:
            case 403:
                throw new InvalidCredentialException("Acceso denegado: " + message);
            case 404:
                throw new UnknownUidException("Recurso no encontrado: " + message);
            case 409:
                throw new AlreadyExistsException("Conflicto: ya existe un recurso: " + message);
            case 500:
                throw new ConnectorIOException("Error interno del servidor Koha: " + message);
            default:
                throw new ConnectorException("Error inesperado (" + statusCode + "): " + message);
        }
    }

    /**
     * Extrae mensaje de error desde respuesta (puede ser JSON o texto plano).
     */
    private static String extractMessage(String body) {
        if (body == null || body.isBlank()) {
            return "(sin mensaje)";
        }

        try {
            JSONObject json = new JSONObject(body);
            if (json.has("error")) {
                return json.getString("error");
            } else if (json.has("message")) {
                return json.getString("message");
            }
        } catch (Exception ignored) {
            // No es JSON válido
        }

        return body.trim();
    }
}
