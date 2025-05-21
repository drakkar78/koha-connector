package com.upeu.connector.util;

import com.upeu.connector.schema.PatronSchema;
import org.identityconnectors.framework.common.objects.ObjectClassInfo;
import org.identityconnectors.framework.spi.localization.ConnectorMessages;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SchemaRegistry {

    private final Map<String, ObjectClassInfo> schemaMap = new HashMap<>();

    public SchemaRegistry(ConnectorMessages messages) {
        loadSchemas(messages);
    }

    private void loadSchemas(ConnectorMessages messages) {
        ObjectClassInfo patronSchema = PatronSchema.build(messages);
        schemaMap.put("account", patronSchema);
    }

    public ObjectClassInfo getSchema(String type) {
        return schemaMap.get(type);
    }

    public Map<String, ObjectClassInfo> getAllSchemas() {
        return Collections.unmodifiableMap(schemaMap);
    }
}
