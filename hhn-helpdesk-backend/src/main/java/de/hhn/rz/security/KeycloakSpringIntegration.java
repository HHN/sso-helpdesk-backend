package de.hhn.rz.security;

import org.keycloak.OAuth2Constants;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
public class KeycloakSpringIntegration {

    @Bean
    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<>(new HttpSessionEventPublisher());
    }

    @Bean
    public KeycloakConfigResolver keycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }

    @Bean
    public Keycloak keycloak(
            @Value("${hhn.keycloak.admin.url}") String url,
            @Value("${hhn.keycloak.admin.realm}") String realm,
            @Value("${hhn.keycloak.admin.client.id}") String clientId,
            @Value("${hhn.keycloak.admin.client.secret}") String clientSecret) {
        return KeycloakBuilder.builder()
                .serverUrl(url)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .realm(realm)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .build();
    }

    @Bean
    public RealmResource keycloakRealmResource(
            Keycloak keycloak,
            @Value("${hhn.keycloak.admin.target.realm}") String realm) {
        return keycloak.realm(realm);
    }
}
