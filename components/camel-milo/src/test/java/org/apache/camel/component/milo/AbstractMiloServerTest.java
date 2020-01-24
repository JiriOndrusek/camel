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
package org.apache.camel.component.milo;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.function.Consumer;

import org.apache.camel.CamelContext;
import org.apache.camel.component.milo.server.MiloServerComponent;
import org.apache.camel.component.mock.AssertionClause;
import org.apache.camel.test.AvailablePortFinder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.eclipse.milo.opcua.stack.core.security.CertificateManager;
import org.eclipse.milo.opcua.stack.core.security.CertificateValidator;
import org.eclipse.milo.opcua.stack.core.security.SecurityPolicy;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;

public abstract class AbstractMiloServerTest extends CamelTestSupport {

    private int serverPort;

    @Override
    protected void doPreSetup() throws Exception {
        super.doPreSetup();
        this.serverPort = AvailablePortFinder.getNextAvailable();
    }

    public int getServerPort() {
        return this.serverPort;
    }

    protected boolean isAddServer() {
        return true;
    }

    /**
     * Replace the port placeholder with the dynamic server port
     * 
     * @param uri the URI to process
     * @return the result, may be {@code null} if the input is {@code null}
     */
    protected String resolve(String uri) {
        if (uri == null) {
            return uri;
        }

        return uri.replace("@@port@@", Integer.toString(this.serverPort));
    }

    public static void testBody(final AssertionClause clause, final Consumer<DataValue> valueConsumer) {
        testBody(clause, DataValue.class, valueConsumer);
    }

    public static <T> void testBody(final AssertionClause clause, final Class<T> bodyClass, final Consumer<T> valueConsumer) {
        clause.predicate(exchange -> {
            final T body = exchange.getIn().getBody(bodyClass);
            valueConsumer.accept(body);
            return true;
        });
    }

    public static Consumer<DataValue> assertGoodValue(final Object expectedValue) {
        return value -> {
            assertNotNull(value);
            assertEquals(expectedValue, value.getValue().getValue());
            assertTrue(value.getStatusCode().isGood());
            assertFalse(value.getStatusCode().isBad());
        };
    }

    @Override
    protected CamelContext createCamelContext() throws Exception {
        final CamelContext context = super.createCamelContext();
        configureContext(context);
        return context;
    }

    protected void configureContext(final CamelContext context) throws Exception {
        if (isAddServer()) {
            final MiloServerComponent server = context.getComponent("milo-server", MiloServerComponent.class);
            configureMiloServer(server);
        }
    }

    protected void configureMiloServer(final MiloServerComponent server) throws Exception {
        server.setBindAddresses("localhost");
        server.setBindPort(this.serverPort);
        server.setUserAuthenticationCredentials("foo:bar,foo2:bar2");
//        server.setEnableAnonymousAuthentication(true);
        server.setUsernameSecurityPolicyUri(SecurityPolicy.None);
        server.setSecurityPoliciesById("None");
    }
    private static final String CLIENT_ALIAS = "client-test-certificate";
    private static final String SERVER_ALIAS = "server-test-certificate";
    private static final char[] PASSWORD = "test".toCharArray();

    X509Certificate clientCertificate = null;
    byte[] clientCertificateBytes;
    KeyPair clientKeyPair = null;

    X509Certificate serverCertificate = null;
    byte[] serverCertificateBytes;
    KeyPair serverKeyPair = null;

    CertificateManager serverCertificateManager;
    CertificateValidator serverCertificateValidator;

    /**
     * Create a default key store for testing
     *
     * @return always returns a key store
     */
    protected void loadKeys() {

        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");

            keyStore.load(getClass().getClassLoader().getResourceAsStream("test-keystore.pfx"), PASSWORD);

            Key clientPrivateKey = keyStore.getKey("client-test-certificate", "test".toCharArray());
            int i = keyStore.size();

            if (clientPrivateKey instanceof PrivateKey) {
                clientCertificate = (X509Certificate) keyStore.getCertificate("client-test-certificate");
                clientCertificateBytes = clientCertificate.getEncoded();

                PublicKey clientPublicKey = clientCertificate.getPublicKey();
                clientKeyPair = new KeyPair(clientPublicKey, (PrivateKey) clientPrivateKey);
            }

            Key serverPrivateKey = keyStore.getKey(SERVER_ALIAS, PASSWORD);
            if (serverPrivateKey instanceof PrivateKey) {
                serverCertificate = (X509Certificate) keyStore.getCertificate(SERVER_ALIAS);
                serverCertificateBytes = serverCertificate.getEncoded();

                PublicKey serverPublicKey = serverCertificate.getPublicKey();
                serverKeyPair = new KeyPair(serverPublicKey, (PrivateKey) serverPrivateKey);
            }
            serverCertificateManager = new TestCertificateManager(
                    serverKeyPair,
                    serverCertificate
            );

            serverCertificateValidator = new TestCertificateValidator(clientCertificate);

//
//            final KeyStoreLoader loader = new KeyStoreLoader();
//            loader.setUrl("file:src/test/resources/cert/test.keystore");
//            loader.setType("JKS");
////            loader.setUrl("file:src/test/resources/cert/validation-certs.pfx");
////            loader.setKeyStorePassword("pwd1");
//           loader.setKeyStorePassword("password");
////            loader.setKeyPassword("pwd1");
//            loader.setKeyPassword("password");
//            loader.setKeyAlias("test");
//            return loader.load();
        } catch (final GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    public CertificateManager getServerCertificateManager() {
        if(serverCertificateManager == null) {
            loadKeys();
        }
        return serverCertificateManager;
    }

    public CertificateValidator getServerCertificateValidator() {
        if (serverCertificateValidator == null) {
            loadKeys();
        }
        return serverCertificateValidator;
    }

    public X509Certificate getServerCertificate() {
        if (serverCertificate == null) {
            loadKeys();
        }
        return serverCertificate;
    }
}
