package com.upeu.connector.client;

import com.upeu.connector.KohaConfiguration;
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
     * Autenticación contra Koha y guarda el token.
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

            int status = response.getCode();
            if (status != HttpStatus.SC_OK) {
                throw new RuntimeException("Autenticación fallida: HTTP " + status);
            }

            String result = readResponse(response);
            JSONObject json = new JSONObject(result);
            this.token = json.getString("token");
        }
    }

    public String getToken() {
        return token;
    }

    public String getAllPatrons() throws Exception {
        return get(config.getBaseUrl() + "/api/v1/patrons");
    }

    public JSONObject getJson(String endpoint) throws Exception {
        String result = get(config.getBaseUrl() + endpoint);
        return new JSONObject(result);
    }

    public JSONObject postJson(String endpoint, JSONObject payload) throws Exception {
        HttpPost post = new HttpPost(config.getBaseUrl() + endpoint);
        post.setHeader("Authorization", "Bearer " + token);
        post.setHeader("Content-Type", "application/json");
        post.setEntity(new StringEntity(payload.toString()));

        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(post)) {
            return new JSONObject(readResponse(response));
        }
    }

    public JSONObject putJson(String endpoint, JSONObject payload) throws Exception {
        HttpPut put = new HttpPut(config.getBaseUrl() + endpoint);
        put.setHeader("Authorization", "Bearer " + token);
        put.setHeader("Content-Type", "application/json");
        put.setEntity(new StringEntity(payload.toString()));

        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(put)) {
            return new JSONObject(readResponse(response));
        }
    }

    public void delete(String endpoint) throws Exception {
        HttpDelete delete = new HttpDelete(config.getBaseUrl() + endpoint);
        delete.setHeader("Authorization", "Bearer " + token);

        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(delete)) {
            // Puede revisar el status si lo deseas
        }
    }

    private String get(String fullUrl) throws Exception {
        HttpGet get = new HttpGet(fullUrl);
        get.setHeader("Authorization", "Bearer " + token);
        get.setHeader("Accept", "application/json");

        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(get)) {
            return readResponse(response);
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
