package org.apache.camel.component.springSecurity5.keycloak;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

public class KeycloakJwtAuthenticationConverter extends JwtAuthenticationConverter {

    public KeycloakJwtAuthenticationConverter() {
        setJwtGrantedAuthoritiesConverter(new KeycloakRealmRoleConverter());
    }
}
