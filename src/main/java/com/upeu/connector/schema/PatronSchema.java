package com.upeu.connector.schema;

import org.identityconnectors.framework.common.objects.*;
import org.identityconnectors.framework.common.objects.ConnectorMessages;

public class PatronSchema {

    public static ObjectClassInfo build(ConnectorMessages messages) {
        ObjectClassInfoBuilder builder = new ObjectClassInfoBuilder();
        builder.setType(ObjectClass.ACCOUNT_NAME);

        builder.addAttributeInfo(buildAttr("userid", true, messages));
        builder.addAttributeInfo(buildAttr("surname", true, messages));
        builder.addAttributeInfo(buildAttr("firstname", true, messages));
        builder.addAttributeInfo(buildAttr("email", false, messages));
        builder.addAttributeInfo(buildAttr("cardnumber", true, messages));
        builder.addAttributeInfo(buildAttr("categorycode", false, messages));
        builder.addAttributeInfo(buildAttr("branchcode", false, messages));
        builder.addAttributeInfo(buildAttr("borrowernumber", false, messages));

        return builder.build();
    }

    private static AttributeInfo buildAttr(String name, boolean required, ConnectorMessages messages) {
        AttributeInfoBuilder attr = new AttributeInfoBuilder(name);
        attr.setRequired(required);
        attr.setType(String.class);

        // Usa mensajes localizados
        String label = messages != null ? messages.format("attribute." + name, name) : name;
        attr.setNativeName(label); // ✅ Usar nativeName en lugar de displayName

        return attr.build();
    }
}
