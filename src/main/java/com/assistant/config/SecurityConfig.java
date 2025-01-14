package com.assistant.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.*;

@Configuration
public class SecurityConfig {

    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//
//        return httpSecurity
//                .authorizeHttpRequests(httpRequests -> httpRequests
////                        .anyRequest().permitAll())
//                        .requestMatchers("/api/v1.0/assistant/**").hasAuthority("Admin")
//                        .anyRequest().authenticated())
//                .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer.jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(jwt -> {
//
//                    Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//
//                    System.out.println("JWT Content: " + jwt.getTokenValue());
//
//                    Map<String, Map<String, Collection<String>>> resourceAccess = jwt.getClaim("resource_access");
//
//                    resourceAccess.forEach((resource, resourceClaims) -> {
//
//                        if (resource.equals("ai-assistant")) {
//
//                            Collection<String> roles = resourceClaims.get("roles");
//
//                            roles.forEach(role -> grantedAuthorities.add(new SimpleGrantedAuthority(role)));
//                        }
//                    });
//
//                    System.out.println("Extracted roles from JWT: " + grantedAuthorities);
//
//                    return new JwtAuthenticationToken(jwt, grantedAuthorities);
//
//                })))
//                .build();
//
//    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}


