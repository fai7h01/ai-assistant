package com.assistant.service.impl;

import com.assistant.service.KeycloakService;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class KeycloakServiceImpl implements KeycloakService {

    private static final Logger log = LoggerFactory.getLogger(KeycloakServiceImpl.class);
    private final WebClient webClient = WebClient.create();
    private final String keycloakTokenUrl = "http://localhost:8080/auth/realms/e-invoices/protocol/openid-connect/token";
    @Value("${keycloak.resource}")
    private String clientId;
    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    @Override
    public String getAccessToken() {

        JsonNode tokenResponse = webClient.post()
                .uri(keycloakTokenUrl)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .bodyValue("client_id=" + clientId +
                        "&client_secret=" + clientSecret +
                        "&grant_type=client_credentials")
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        log.info("\n\n>>>> Access Token in KeycloakService: {}", tokenResponse.get("access_token").asText());
        return tokenResponse.get("access_token").asText();
    }


}
