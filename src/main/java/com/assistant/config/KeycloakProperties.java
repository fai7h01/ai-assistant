package com.assistant.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Getter
@Component
@Profile("prod")
public class KeycloakProperties {

    @Value("${keycloak-token.url}")
    private String tokenUrl;

    @Value("${keycloak.resource}")
    private String clientId;
    @Value("${keycloak.credentials.secret}")
    private String clientSecret;
    @Value("${master.user.username}")
    private String masterUser;
    @Value("${master.user.password}")
    private String masterUserPswd;
    @Value("${master.realm}")
    private String masterRealm;
    @Value("${master.client}")
    private String masterClient;


}
