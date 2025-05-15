package com.upeu.connector.util;

import com.upeu.connector.schema.PatronSchema;
import org.identityconnectors.framework.common.objects.ObjectClassInfo;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Registro de esquemas disponibles para los objetos del conector.
 */
public class SchemaRegistry {

    private final Map<String, ObjectClassInfo> schemaMap = new HashMap<>();

    public SchemaRegistry() {
        loadSchemas();
    }

    private void loadSchemas() {
        PatronSchema patronSchema = new PatronSchema();
        schemaMap.put("account", patronSchema.getPatronSchema());
    }

    /**
     * Obtiene un esquema registrado por su tipo (e.g., "account").
     */
    public ObjectClassInfo getSchema(String type) {
        return schemaMap.get(type);
    }

    /**
     * Retorna todos los esquemas registrados.
     */
    public Map<String, ObjectClassInfo> getAllSchemas() {
        return Collections.unmodifiableMap(schemaMap);
    }

    public void register(PatronSchema patronSchema) {

    }
}
