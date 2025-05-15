package com.upeu.connector.client;

import com.upeu.connector.KohaConfiguration;
import com.upeu.connector.util.ErrorHandler;
import org.apache.hc.client5.http.classic.methods.*;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Cliente HTTP para comunicarse con la API REST de Koha.
 */
public class KohaClient {

    private final KohaConfiguration config;
    private String token;

    public KohaClient(KohaConfiguration config) {
        this.config = config;
    }

    /**
     * Realiza autenticación contra Koha y guarda el token JWT.
     */
    public void authenticate() throws Exception {
        String url = config.getBaseUrl() + "/api/v1/authentication";
        HttpPost post = new HttpPost(url);
        post.setHeader("Content-Type", "application/json");

        JSONObject payload = new JSONObject();
        payload.put("userid", config.getUsername());
        payload.put("password", config.getPassword());
        post.setEntity(new StringEntity(payload.toString()));

        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(post)) {

            String result = readResponse(response);
            int status = response.getCode();

            if (status != HttpStatus.SC_OK) {
                ErrorHandler.handleKohaError(status, result);
            }

            JSONObject json = new JSONObject(result);
            this.token = json.getString("token");
        }
    }

    /**
     * Verifica si ya hay un token disponible.
     */
    private boolean isAuthenticated() {
        return token != null && !token.isBlank();
    }

    /**
     * Asegura que la autenticación esté lista antes de cualquier operación.
     */
    private void ensureAuthenticated() throws Exception {
        if (!isAuthenticated()) {
            authenticate();
        }
    }

    public String getToken() {
        return token;
    }

    public String getAllPatrons() throws Exception {
        ensureAuthenticated();
        return get(config.getBaseUrl() + "/api/v1/patrons");
    }

    public JSONObject getJson(String endpoint) throws Exception {
        ensureAuthenticated();
        String result = get(config.getBaseUrl() + endpoint);
        return new JSONObject(result);
    }

    public JSONObject postJson(String endpoint, JSONObject payload) throws Exception {
        ensureAuthenticated();
        HttpPost post = new HttpPost(config.getBaseUrl() + endpoint);
        post.setHeader("Authorization", "Bearer " + token);
        post.setHeader("Content-Type", "application/json");
        post.setEntity(new StringEntity(payload.toString()));

        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(post)) {

            String body = readResponse(response);
            int status = response.getCode();

            if (status != HttpStatus.SC_CREATED && status != HttpStatus.SC_OK) {
                ErrorHandler.handleKohaError(status, body);
            }

            return new JSONObject(body);
        }
    }

    public JSONObject putJson(String endpoint, JSONObject payload) throws Exception {
        ensureAuthenticated();
        HttpPut put = new HttpPut(config.getBaseUrl() + endpoint);
        put.setHeader("Authorization", "Bearer " + token);
        put.setHeader("Content-Type", "application/json");
        put.setEntity(new StringEntity(payload.toString()));

        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(put)) {

            String body = readResponse(response);
            int status = response.getCode();

            if (status != HttpStatus.SC_OK) {
                ErrorHandler.handleKohaError(status, body);
            }

            return new JSONObject(body);
        }
    }

    public void delete(String endpoint) throws Exception {
        ensureAuthenticated();
        HttpDelete delete = new HttpDelete(config.getBaseUrl() + endpoint);
        delete.setHeader("Authorization", "Bearer " + token);

        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(delete)) {

            String body = readResponse(response);
            int status = response.getCode();

            if (status != HttpStatus.SC_NO_CONTENT && status != HttpStatus.SC_OK) {
                ErrorHandler.handleKohaError(status, body);
            }
        }
    }

    private String get(String fullUrl) throws Exception {
        HttpGet get = new HttpGet(fullUrl);
        get.setHeader("Authorization", "Bearer " + token);
        get.setHeader("Accept", "application/json");

        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(get)) {

            String body = readResponse(response);
            int status = response.getCode();

            if (status != HttpStatus.SC_OK) {
                ErrorHandler.handleKohaError(status, body);
            }

            return body;
        }
    }

    private String readResponse(CloseableHttpResponse response) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }
}
