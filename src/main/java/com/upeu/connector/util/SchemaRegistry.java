package com.upeu.connector.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Registra y gestiona todos los esquemas disponibles del conector.
 */
public class SchemaRegistry {

    private final List<Object> schemas = new ArrayList<>();

    public void register(Object schema) {
        schemas.add(schema);
    }

    public List<Object> getSchemas() {
        return schemas;
    }
}
