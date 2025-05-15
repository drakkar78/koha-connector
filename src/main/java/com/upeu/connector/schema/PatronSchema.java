package com.upeu.connector.schema;

import org.identityconnectors.framework.common.objects.AttributeInfo;
import org.identityconnectors.framework.common.objects.AttributeInfoBuilder;
import org.identityconnectors.framework.common.objects.ObjectClassInfo;
import org.identityconnectors.framework.common.objects.ObjectClassInfoBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Define el esquema de atributos del objeto Patron (usuario) en Koha.
 */
public class PatronSchema {

    private final ObjectClassInfo patronSchema;

    public PatronSchema() {
        ObjectClassInfoBuilder builder = new ObjectClassInfoBuilder();

        builder.setType("account"); // Tipo estándar ConnId para usuarios

        Set<AttributeInfo> attributes = new HashSet<>();

        attributes.add(AttributeInfoBuilder.build("userid", String.class));           // nombre de usuario
        attributes.add(AttributeInfoBuilder.build("surname", String.class));          // apellido
        attributes.add(AttributeInfoBuilder.build("firstname", String.class));        // nombre
        attributes.add(AttributeInfoBuilder.build("email", String.class));            // correo electrónico
        attributes.add(AttributeInfoBuilder.build("cardnumber", String.class));       // número de tarjeta
        attributes.add(AttributeInfoBuilder.build("categorycode", String.class));     // categoría de usuario
        attributes.add(AttributeInfoBuilder.build("branchcode", String.class));       // biblioteca
        attributes.add(AttributeInfoBuilder.build("borrowernumber", String.class));   // ID interno

        builder.addAllAttributeInfo(attributes);

        this.patronSchema = builder.build();
    }

    public ObjectClassInfo getPatronSchema() {
        return patronSchema;
    }
}