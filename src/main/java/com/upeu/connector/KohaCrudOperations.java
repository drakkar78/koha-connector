package com.upeu.connector.operations;

import com.upeu.connector.KohaConnector;
import com.upeu.connector.handler.DefaultPatronHandler;
import com.upeu.connector.handler.PatronHandler;
import com.upeu.connector.model.Patron;
import com.upeu.connector.schema.PatronSchema;
import com.upeu.connector.util.EndpointRegistry;
import com.upeu.connector.util.SchemaRegistry;
import org.identityconnectors.framework.common.objects.*;
import org.identityconnectors.framework.common.objects.filter.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

public class KohaCrudOperations {

    private static final Logger LOG = LoggerFactory.getLogger(KohaCrudOperations.class);

    private final EndpointRegistry endpointRegistry;
    private final SchemaRegistry schemaRegistry;
    private final ConnectorMessages messages;

    public KohaCrudOperations(EndpointRegistry endpointRegistry, ConnectorMessages messages) {
        this.endpointRegistry = endpointRegistry;
        this.schemaRegistry = new SchemaRegistry(messages);
        this.messages = messages;
    }

    public Uid create(ObjectClass objectClass, Set<Attribute> attributes, OperationOptions options) {
        validateAccountType(objectClass);

        PatronHandler handler = new DefaultPatronHandler(endpointRegistry);
        Patron patron = Patron.fromAttributes(attributes);
        Patron created = handler.create(patron);

        return new Uid(String.valueOf(created.getBorrowernumber()));
    }

    public Uid update(ObjectClass objectClass, Uid uid, Set<Attribute> attributes, OperationOptions options) {
        validateAccountType(objectClass);

        PatronHandler handler = new DefaultPatronHandler(endpointRegistry);
        Patron updatedPatron = Patron.fromAttributes(attributes);
        String borrowerNumber = uid.getUidValue();

        Patron result = handler.update(borrowerNumber, updatedPatron);

        return new Uid(String.valueOf(result.getBorrowernumber()));
    }

    public void delete(ObjectClass objectClass, Uid uid, OperationOptions options) {
        validateAccountType(objectClass);

        PatronHandler handler = new DefaultPatronHandler(endpointRegistry);
        String borrowerNumber = uid.getUidValue();

        boolean deleted = handler.deleteByBorrowerNumber(borrowerNumber);
        if (!deleted) {
            throw new RuntimeException(messages.format("error.delete.failed", borrowerNumber));
        }
    }

    public void search(ObjectClass objectClass, Filter filter, ResultsHandler handler, OperationOptions options) {
        validateAccountType(objectClass);

        PatronHandler patronHandler = new DefaultPatronHandler(endpointRegistry);

        List<Patron> results = (filter == null)
                ? patronHandler.getAll()
                : filter.accept(patronHandler, null);

        for (Patron patron : results) {
            ConnectorObject object = buildConnectorObject(patron);
            handler.handle(object);
        }
    }

    public Schema schema() {
        SchemaBuilder builder = new SchemaBuilder(KohaConnector.class);
        builder.defineObjectClass(schemaRegistry.getSchema(ObjectClass.ACCOUNT_NAME));
        return builder.build();
    }

    private ConnectorObject buildConnectorObject(Patron patron) {
        ConnectorObjectBuilder builder = new ConnectorObjectBuilder();

        builder.setObjectClass(ObjectClass.ACCOUNT);
        builder.setUid(patron.getBorrowernumber());
        builder.setName(patron.getCardnumber());

        builder.addAttribute("userid", patron.getUserid());
        builder.addAttribute("surname", patron.getSurname());
        builder.addAttribute("firstname", patron.getFirstname());
        builder.addAttribute("email", patron.getEmail());
        builder.addAttribute("categorycode", patron.getCategorycode());
        builder.addAttribute("branchcode", patron.getBranchcode());
        builder.addAttribute("cardnumber", patron.getCardnumber());
        builder.addAttribute("borrowernumber", patron.getBorrowernumber());

        return builder.build();
    }

    private void validateAccountType(ObjectClass objectClass) {
        if (!objectClass.is(ObjectClass.ACCOUNT_NAME)) {
            String msg = messages.format("error.unsupported.objectclass", objectClass.getObjectClassValue());
            throw new IllegalArgumentException(msg);
        }
    }
}
