package com.upeu.connector.handler;

import com.upeu.connector.util.EndpointRegistry;
import org.identityconnectors.framework.common.objects.filter.Filter;

/**
 * Encargado de ejecutar búsquedas con o sin filtros.
 * Delega al handler correspondiente (como PatronHandler).
 */
public class FilterHandler extends BaseHandler {

    public FilterHandler(EndpointRegistry endpointRegistry) {
        super(endpointRegistry);
    }

    /**
     * Ejecuta una búsqueda con filtro.
     * Si no hay filtro, retorna todos los usuarios.
     */
    public Object executeQuery(Filter filter) {
        PatronHandler handler = new PatronHandler(endpointRegistry);

        if (filter == null) {
            return handler.getAll();
        }

        // Delegar al handler que implementa FilterVisitor
        return filter.accept(handler, null);
    }
}
