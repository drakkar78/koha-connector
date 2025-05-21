package com.upeu.connector.schema;

import org.identityconnectors.framework.common.objects.*;
import org.identityconnectors.framework.spi.ConnectorMessages;

public class PatronSchema {

    public static ObjectClassInfo build(ConnectorMessages messages) {
        ObjectClassInfoBuilder builder = new ObjectClassInfoBuilder();
        builder.setType(ObjectClass.ACCOUNT_NAME);

        builder.addAttributeInfo(buildAttr("userid", messages));
        builder.addAttributeInfo(buildAttr("surname", messages));
        builder.addAttributeInfo(buildAttr("firstname", messages));
        builder.addAttributeInfo(buildAttr("email", messages));
        builder.addAttributeInfo(buildAttr("cardnumber", messages));
        builder.addAttributeInfo(buildAttr("categorycode", messages));
        builder.addAttributeInfo(buildAttr("branchcode", messages));
        builder.addAttributeInfo(buildAttr("borrowernumber", messages));

        return builder.build();
    }

    private static AttributeInfo buildAttr(String name, ConnectorMessages messages) {
        AttributeInfoBuilder attr = new AttributeInfoBuilder(name);
        attr.setType(String.class);

        String label = messages.format("attribute." + name, name);
        attr.setDisplayName(label);

        return attr.build();
    }
}
