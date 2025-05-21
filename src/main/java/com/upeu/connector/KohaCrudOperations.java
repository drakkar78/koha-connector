package com.upeu.connector.operations;

import com.upeu.connector.handler.PatronHandler;
import com.upeu.connector.model.Patron;
import com.upeu.connector.util.EndpointRegistry;
import org.identityconnectors.framework.common.objects.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class KohaCrudOperations {

    private static final Logger LOG = LoggerFactory.getLogger(KohaCrudOperations.class);

    private final EndpointRegistry endpointRegistry;

    public KohaCrudOperations(EndpointRegistry endpointRegistry) {
        this.endpointRegistry = endpointRegistry;
    }

    public Uid create(ObjectClass objectClass, Set<Attribute> attributes, OperationOptions options) {
        if (!objectClass.is(ObjectClass.ACCOUNT_NAME)) {
            throw new IllegalArgumentException("Soporte solo para tipo __ACCOUNT__");
        }

        PatronHandler handler = new PatronHandler(endpointRegistry) {
            // Puede extender más adelante si hace falta filtros
        };

        Patron patron = Patron.fromAttributes(attributes);
        Patron created = handler.create(patron);

        // IMPORTANTE: borrowernumber será usado como Uid, cardnumber como Name
        return new Uid(String.valueOf(created.getBorrowerNumber()));
    }

    public Uid update(ObjectClass objectClass, Uid uid, Set<Attribute> attributes, OperationOptions options) {
        if (!objectClass.is(ObjectClass.ACCOUNT_NAME)) {
            throw new IllegalArgumentException("Solo se soporta el tipo __ACCOUNT__");
        }

        PatronHandler handler = new PatronHandler(endpointRegistry) {
            // Se puede extender si hace falta
        };

        Patron updatedPatron = Patron.fromAttributes(attributes);

        // Se espera que el Uid sea el borrowernumber (ID interno de Koha)
        String borrowerNumber = uid.getUidValue();

        Patron result = handler.update(borrowerNumber, updatedPatron);

        // Devuelve el mismo Uid (borrowernumber)
        return new Uid(String.valueOf(result.getBorrowerNumber()));
    }

}
