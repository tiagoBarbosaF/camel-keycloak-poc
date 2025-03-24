package com.example.camelkeycloakpoc.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.fluent.Form;
import org.apache.hc.client5.http.fluent.Request;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class KeycloakTokenService {
    private static final String TOKEN_URL = "http://localhost:8090/realms/master/protocol/openid-connect/token";
    private static final String CLIENT_ID = "cliente-api-collection";
    private static final String CLIENT_SECRET = "fpFQZUcz2q1FEIirzmrcjWlLus4Pi8TQ";

    public String getAccessToken() {
        try {
            String response = Request.post(TOKEN_URL)
                    .bodyForm(
                            Form.form()
                                    .add("grant_type", "client_credentials")
                                    .add("client_id", CLIENT_ID)
                                    .add("client_secret", CLIENT_SECRET)
                                    .build()
                    )
                    .execute()
                    .returnContent()
                    .asString();

            return new ObjectMapper().readTree(response).get("access_token").asText();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao obter token do keycloak: " + e.getMessage(), e);
        }
    }
}
