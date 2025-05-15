package com.upeu.connector;

import org.identityconnectors.framework.spi.Configuration;
import org.identityconnectors.framework.spi.ConfigurationProperty;
import org.identityconnectors.framework.common.objects.ConnectorMessages;

/**
 * Configuración del conector Koha.
 */
public class KohaConfiguration implements Configuration {

    private String baseUrl;
    private String username;
    private String password;
    private ConnectorMessages connectorMessages;

    @ConfigurationProperty(order = 1, displayMessageKey = "koha.config.baseUrl", helpMessageKey = "koha.config.baseUrl.help", required = true)
    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @ConfigurationProperty(order = 2, displayMessageKey = "koha.config.username", helpMessageKey = "koha.config.username.help", required = true)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @ConfigurationProperty(order = 3, displayMessageKey = "koha.config.password", helpMessageKey = "koha.config.password.help", required = true, confidential = true)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void validate() {
        if (baseUrl == null || baseUrl.isBlank()) {
            throw new IllegalArgumentException("Koha baseUrl no puede ser vacío.");
        }
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username requerido.");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password requerido.");
        }
    }

    @Override
    public void setConnectorMessages(ConnectorMessages messages) {
        this.connectorMessages = messages;
    }

    @Override
    public ConnectorMessages getConnectorMessages() {
        return connectorMessages;
    }
}
