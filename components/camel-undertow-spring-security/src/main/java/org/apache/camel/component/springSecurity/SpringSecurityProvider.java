/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.component.springSecurity;

import io.undertow.server.HttpServerExchange;
import io.undertow.servlet.handlers.ServletRequestContext;
import io.undertow.util.AttachmentKey;
import io.undertow.util.StatusCodes;
import org.apache.camel.component.springSecurity.keycloak.KeycloakRealmRoleConverter;
import org.apache.camel.component.springSecurity.keycloak.KeycloakUsernameSubClaimAdapter;
import org.apache.camel.component.undertow.spi.UndertowSecurityProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.context.support.GenericWebApplicationContext;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;

public class SpringSecurityProvider implements UndertowSecurityProvider {

    public static String PRINCIPAL_NAME_HEADER = SpringSecurityProvider.class.getName() + "_principal";
    private static final AttachmentKey<String> PRINCIPAL_NAME_KEY = AttachmentKey.create(String.class);

    private Filter securityFilter;

    private ClientRegistration clientRegistration;

    @Override
    public void addHeader(BiConsumer<String, Object> consumer, HttpServerExchange httpExchange) throws Exception {
       String principalName = httpExchange.getAttachment(PRINCIPAL_NAME_KEY);
       consumer.accept(PRINCIPAL_NAME_HEADER, principalName);
    }

    @Override
    public int authenticate(HttpServerExchange httpExchange, List<String> allowedRoles) throws Exception {
        ServletRequestContext servletRequestContext = httpExchange.getAttachment(ServletRequestContext.ATTACHMENT_KEY);
        ServletRequest request = servletRequestContext.getServletRequest();
        ServletResponse response = servletRequestContext.getServletResponse();

        FilterChain fc = (servletRequest, servletResponse) -> {
                Authentication a = SecurityContextHolder.getContext().getAuthentication();
                if(a instanceof JwtAuthenticationToken) {
                    boolean allowed = false;
                    Collection<GrantedAuthority> grantedAuthorities = ((JwtAuthenticationToken)a).getAuthorities();
                    for(GrantedAuthority grantedAuthority : grantedAuthorities) {
                        if(allowedRoles.contains(grantedAuthority.getAuthority().replaceFirst(KeycloakRealmRoleConverter.ROLE_PREFIX, ""))) {
                            allowed = true;
                            break;
                        }
                    }

                    if(allowed) {
                        httpExchange.putAttachment(PRINCIPAL_NAME_KEY, ((JwtAuthenticationToken)a).getName());
                        httpExchange.setStatusCode(StatusCodes.OK);
                    } else {
                        httpExchange.setStatusCode(StatusCodes.FORBIDDEN);
                    }
                }
            };
        securityFilter.doFilter(request, response, fc);

        return httpExchange.getStatusCode();
    }


    @Override
    public boolean acceptConfiguration(Object configuration, String endpointUri) throws Exception {
        if(configuration instanceof SpringSecurityConfiguration) {
            SpringSecurityConfiguration conf = (SpringSecurityConfiguration)configuration;
            this.securityFilter = conf.getSecurityFilter();
            this.clientRegistration = conf.getKeycloakRegistration();
            return true;
        }

        return false;
    }

    @Override
    public boolean requireServletContext() {
        return false;
    }


    private void configureBeans(GenericWebApplicationContext context) throws Exception {
        context.registerBean(ClientRegistrationRepository.class, this::clientRegistrationRepository);
        context.registerBean(OAuth2AuthorizedClientService.class, this::authorizedClientService);
        context.registerBean(JwtDecoder.class, jwtDecoderByIssuerUri());
    }

    private JwtDecoder jwtDecoderByIssuerUri() {
        final String jwkSetUri = clientRegistration.getProviderDetails().getJwkSetUri();
        final NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
        // Use preferred_username from claims as authentication name, instead of UUID subject
        jwtDecoder.setClaimSetConverter(new KeycloakUsernameSubClaimAdapter());
        return jwtDecoder;
    }

    private ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(Collections.singletonList(clientRegistration));
    }

    private OAuth2AuthorizedClientService authorizedClientService() {
        return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository());
    }


}
