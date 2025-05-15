package com.upeu.connector.client;

import com.upeu.connector.KohaConfiguration;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpStatus;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class KohaClient {

    private final KohaConfiguration config;
    private String token;

    public KohaClient(KohaConfiguration config) {
        this.config = config;
    }

    /**
     * Realiza autenticación contra Koha y guarda el token.
     */
    public void authenticate() throws Exception {
        String url = config.getBaseUrl() + "/api/v1/authentication";
        HttpPost post = new HttpPost(url);
        post.setHeader("Content-Type", "application/json");

        JSONObject payload = new JSONObject();
        payload.put("userid", config.getUsername());
        payload.put("password", config.getPassword());

        post.setEntity(new StringEntity(payload.toString()));

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            ClassicHttpResponse response = (ClassicHttpResponse) client.execute(post);

            int status = response.getCode();
            if (status != HttpStatus.SC_OK) {
                throw new RuntimeException("Autenticación fallida: HTTP " + status);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            JSONObject json = new JSONObject(result.toString());
            this.token = json.getString("token");
        }
    }

    /**
     * Retorna el token de autenticación actual.
     */
    public String getToken() {
        return token;
    }

    /**
     * Ejemplo de GET a /patrons con token autenticado.
     */
    public String getAllPatrons() throws Exception {
        String url = config.getBaseUrl() + "/api/v1/patrons";
        HttpGet get = new HttpGet(url);
        get.setHeader("Authorization", "Bearer " + token);
        get.setHeader("Accept", "application/json");

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            ClassicHttpResponse response = (ClassicHttpResponse) client.execute(get);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            return result.toString();
        }
    }
}
