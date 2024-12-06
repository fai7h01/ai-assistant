package com.assistant.service.impl;

import com.assistant.config.KeycloakProperties;
import com.assistant.service.KeycloakService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
public class KeycloakServiceImpl implements KeycloakService {

    private final WebClient webClient = WebClient.create();
    private final KeycloakProperties keycloakProperties;

    public KeycloakServiceImpl(KeycloakProperties keycloakProperties) {
        this.keycloakProperties = keycloakProperties;
    }

    @Override
    public String getAccessToken() {

        JsonNode tokenResponse = webClient.post()
                .uri(keycloakProperties.getTokenUrl())
                .header("Content-Type", "application/x-www-form-urlencoded")
                .bodyValue("client_id=" + keycloakProperties.getClientId() +
                        "&client_secret=" + keycloakProperties.getClientSecret() +
                        "&grant_type=client_credentials")
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        log.info("\n\n>>>> Access Token in KeycloakService: {}", tokenResponse.get("access_token").asText());
        return tokenResponse.get("access_token").asText();
    }


}
