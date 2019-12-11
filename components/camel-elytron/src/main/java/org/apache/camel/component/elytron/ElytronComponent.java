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
package org.apache.camel.component.elytron;

import io.undertow.security.handlers.AuthenticationCallHandler;
import io.undertow.security.handlers.AuthenticationConstraintHandler;
import io.undertow.server.HttpHandler;
import org.apache.camel.CamelContext;
import org.apache.camel.component.undertow.HttpHandlerRegistrationInfo;
import org.apache.camel.component.undertow.UndertowComponent;
import org.apache.camel.component.undertow.UndertowEndpoint;
import org.apache.camel.spi.Metadata;
import org.apache.camel.spi.annotations.Component;
import org.wildfly.elytron.web.undertow.server.ElytronContextAssociationHandler;
import org.wildfly.elytron.web.undertow.server.ElytronRunAsHandler;
import org.wildfly.security.WildFlyElytronProvider;
import org.wildfly.security.auth.server.HttpAuthenticationFactory;
import org.wildfly.security.auth.server.MechanismConfiguration;
import org.wildfly.security.auth.server.MechanismConfigurationSelector;
import org.wildfly.security.auth.server.MechanismRealmConfiguration;
import org.wildfly.security.auth.server.SecurityDomain;
import org.wildfly.security.http.HttpAuthenticationException;
import org.wildfly.security.http.HttpServerAuthenticationMechanismFactory;
import org.wildfly.security.http.util.FilterServerMechanismFactory;
import org.wildfly.security.http.util.SecurityProviderServerMechanismFactory;

import javax.net.ssl.SSLContext;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Provider;
import java.util.Collections;

/**
 *
 * @author JiriOndrusek
 */
//TODO
@Metadata(label = "verifiers", enums = "parameters,connectivity")
@Component("elytron")
public class ElytronComponent extends UndertowComponent {

    private static final WildFlyElytronProvider elytronProvider = new WildFlyElytronProvider();

    @Metadata(label = "elytron")
    private SecurityDomain.Builder securityDomainBuilder;

    private SecurityDomain securityDomain;

    public ElytronComponent() {
    }

    public ElytronComponent(CamelContext context) {
        super(context);
    }

    @Override
    protected String getComponentName() {
        return "elytron";
    }


    @Override
    protected UndertowEndpoint createEndpointInstance(URI endpointUri, UndertowComponent component) throws URISyntaxException {
        return new ElytronEndpoint(endpointUri.toString(), component);
    }

    @Override
    public HttpHandler registerEndpoint(HttpHandlerRegistrationInfo registrationInfo, SSLContext sslContext, HttpHandler handler) {
        //injecting elytron
        return super.registerEndpoint(registrationInfo, sslContext, wrap(handler, getSecurityDomain()));
    }

    /**
     * TODO
     * @return
     */
    public SecurityDomain.Builder getSecurityDomainBuilder() {
        return securityDomainBuilder;
    }

    public void setSecurityDomainBuilder(SecurityDomain.Builder securityDomainBuilder) {
        this.securityDomainBuilder = securityDomainBuilder;
    }

    SecurityDomain getSecurityDomain() {
        if(securityDomain == null) {
            securityDomain = securityDomainBuilder.build();
        }

        return securityDomain;
    }

    private static HttpHandler wrap(final HttpHandler toWrap, final SecurityDomain securityDomain) {
        HttpAuthenticationFactory httpAuthenticationFactory = createHttpAuthenticationFactory(securityDomain);

        HttpHandler rootHandler = new ElytronRunAsHandler(toWrap);

        /*
         * In this example we know the ElytronRunAsHandler is calling a single handler that is not going to switch to blocking,
         * as the ElytronRunAsHandler is associating the identity with a ThreadLocal if it was possible the handler could switch
         * from non-blocking to blocking we would insert the BlockingHandler here.
         */

        rootHandler = new AuthenticationCallHandler(rootHandler);
        rootHandler = new AuthenticationConstraintHandler(rootHandler);

        return ElytronContextAssociationHandler.builder()
                .setNext(rootHandler)
                .setMechanismSupplier(() -> {
                    try {
                        return Collections.singletonList(httpAuthenticationFactory.createMechanism("BASIC"));
                    } catch (HttpAuthenticationException e) {
                        throw new RuntimeException(e);
                    }
                }).build();
    }


    private static HttpAuthenticationFactory createHttpAuthenticationFactory(final SecurityDomain securityDomain) {
        HttpServerAuthenticationMechanismFactory providerFactory = new SecurityProviderServerMechanismFactory(() -> new Provider[]{elytronProvider});
        HttpServerAuthenticationMechanismFactory httpServerMechanismFactory = new FilterServerMechanismFactory(providerFactory, true, "BASIC");

        return HttpAuthenticationFactory.builder()
                .setSecurityDomain(securityDomain)
                .setMechanismConfigurationSelector(MechanismConfigurationSelector.constantSelector(
                        MechanismConfiguration.builder()
                                .addMechanismRealm(MechanismRealmConfiguration.builder().setRealmName("Elytron Realm").build())
                                .build()))
                .setFactory(httpServerMechanismFactory)
                .build();
    }


}
