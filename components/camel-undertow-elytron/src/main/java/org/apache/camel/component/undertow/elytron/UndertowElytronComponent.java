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
package org.apache.camel.component.undertow.elytron;

import io.undertow.security.handlers.AuthenticationCallHandler;
import io.undertow.security.handlers.AuthenticationConstraintHandler;
import io.undertow.server.HttpHandler;
import org.apache.camel.CamelContext;
import org.apache.camel.component.undertow.HttpHandlerRegistrationInfo;
import org.apache.camel.component.undertow.UndertowComponent;
import org.apache.camel.component.undertow.UndertowEndpoint;
import org.apache.camel.spi.annotations.Component;
import org.wildfly.elytron.web.undertow.server.ElytronContextAssociationHandler;
import org.wildfly.elytron.web.undertow.server.ElytronRunAsHandler;
import org.wildfly.security.WildFlyElytronProvider;
import org.wildfly.security.auth.permission.LoginPermission;
import org.wildfly.security.auth.realm.SimpleMapBackedSecurityRealm;
import org.wildfly.security.auth.realm.SimpleRealmEntry;
import org.wildfly.security.auth.server.HttpAuthenticationFactory;
import org.wildfly.security.auth.server.MechanismConfiguration;
import org.wildfly.security.auth.server.MechanismConfigurationSelector;
import org.wildfly.security.auth.server.MechanismRealmConfiguration;
import org.wildfly.security.auth.server.SecurityDomain;
import org.wildfly.security.credential.PasswordCredential;
import org.wildfly.security.http.HttpAuthenticationException;
import org.wildfly.security.http.HttpServerAuthenticationMechanismFactory;
import org.wildfly.security.http.util.FilterServerMechanismFactory;
import org.wildfly.security.http.util.SecurityProviderServerMechanismFactory;
import org.wildfly.security.password.PasswordFactory;
import org.wildfly.security.password.spec.ClearPasswordSpec;
import org.wildfly.security.permission.PermissionVerifier;

import javax.net.ssl.SSLContext;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Provider;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.wildfly.security.password.interfaces.ClearPassword.ALGORITHM_CLEAR;

/**
 *
 * @author JiriOndrusek
 */
@Component("undertow-elytron")
public class UndertowElytronComponent extends UndertowComponent {

    private static final WildFlyElytronProvider elytronProvider = new WildFlyElytronProvider();
    SecurityDomain securityDomain;

    public UndertowElytronComponent() {
        try {
            securityDomain = createSecurityDomain();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public UndertowElytronComponent(CamelContext context) {

        super(context);


    }

    private static SecurityDomain createSecurityDomain() throws Exception {
        PasswordFactory passwordFactory = PasswordFactory.getInstance(ALGORITHM_CLEAR, elytronProvider);

        Map<String, SimpleRealmEntry> passwordMap = new HashMap<>();
        passwordMap.put("elytron", new SimpleRealmEntry(Collections.singletonList(new PasswordCredential(passwordFactory.generatePassword(new ClearPasswordSpec("elytron".toCharArray()))))));

        SimpleMapBackedSecurityRealm simpleRealm = new SimpleMapBackedSecurityRealm(() -> new Provider[] { elytronProvider });
        simpleRealm.setPasswordMap(passwordMap);

        SecurityDomain.Builder builder = SecurityDomain.builder()
                .setDefaultRealmName("TestRealm");

        builder.addRealm("TestRealm", simpleRealm).build();
        builder.setPermissionMapper((principal, roles) -> PermissionVerifier.from(new LoginPermission()));

        return builder.build();
    }


    @Override
    protected String getComponentName() {
        return "undertow-elytron";
    }


    @Override
    public HttpHandler registerEndpoint(HttpHandlerRegistrationInfo registrationInfo, SSLContext sslContext, HttpHandler handler) {
        return super.registerEndpoint(registrationInfo, sslContext, wrap(handler, securityDomain));
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
        HttpServerAuthenticationMechanismFactory providerFactory = new SecurityProviderServerMechanismFactory(() -> new Provider[] {elytronProvider});
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

    @Override
    protected UndertowEndpoint createEndpointInstance(URI endpointUri, UndertowComponent component) throws URISyntaxException {
        return new UndertowElytronEndpoint(endpointUri.toString(), component);
    }
}
