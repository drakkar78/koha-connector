package com.upeu.connector.util;

import org.json.JSONObject;

/**
 * Utilidad para validación de datos JSON antes de enviar a Koha.
 */
public class ValidationJsonUtil {

    /**
     * Valida que los campos requeridos estén presentes y no vacíos.
     */
    public static void requireFields(JSONObject object, String... fields) {
        for (String field : fields) {
            if (!object.has(field) || object.isNull(field) || object.getString(field).isBlank()) {
                throw new IllegalArgumentException("Campo requerido faltante o vacío: " + field);
            }
        }
    }

    /**
     * Valida formato de email básico.
     */
    public static void validateEmail(String email) {
        if (email == null || !email.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("Formato de correo inválido: " + email);
        }
    }

    /**
     * Valida longitud mínima de un campo.
     */
    public static void minLength(String fieldValue, String fieldName, int minLength) {
        if (fieldValue == null || fieldValue.length() < minLength) {
            throw new IllegalArgumentException("El campo '" + fieldName + "' debe tener al menos " + minLength + " caracteres.");
        }
    }
}
