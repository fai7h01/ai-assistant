package com.assistant.service.impl;

import com.assistant.service.KeycloakService;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KeycloakServiceImpl implements KeycloakService {

    @Override
    public String getAccessToken() {
        KeycloakAuthenticationToken keycloakAuthenticationToken = getAuthentication();
        return "Bearer " + keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getTokenString();
    }

    private KeycloakAuthenticationToken getAuthentication() {
        return (KeycloakAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
    }


}
