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
package org.apache.camel.component.http;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.camel.BindToRegistry;
import org.apache.camel.Exchange;
import org.apache.camel.component.http.handler.AuthenticationValidationHandler;
import org.apache.camel.support.jsse.SSLContextParameters;
import org.apache.camel.test.AvailablePortFinder;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.bootstrap.HttpServer;
import org.apache.http.impl.bootstrap.ServerBootstrap;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.localserver.RequestBasicAuth;
import org.apache.http.localserver.ResponseBasicUnauthorized;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.ImmutableHttpProcessor;
import org.apache.http.protocol.ResponseContent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.apache.camel.component.http.HttpMethods.GET;

public class HttpsAuthenticationTest extends BaseHttpsTest {

    private String user = "camel";
    private String password = "password";
    private HttpServer localServer;
    private int port = AvailablePortFinder.getNextAvailable();

    @BindToRegistry("x509HostnameVerifier")
    private NoopHostnameVerifier hostnameVerifier = new NoopHostnameVerifier();

    @BindToRegistry("sslContextParameters")
    private SSLContextParameters sslContextParameters = new SSLContextParameters();

    @BindToRegistry("basicAuthContext")
    private HttpContext basicAuthContexts = basicAuthContext();

    @BeforeEach
    @Override
    public void setUp() throws Exception {
        localServer = ServerBootstrap.bootstrap().setHttpProcessor(getBasicHttpProcessor())
                .setConnectionReuseStrategy(getConnectionReuseStrategy()).setResponseFactory(getHttpResponseFactory())
                .setExpectationVerifier(getHttpExpectationVerifier()).setSslContext(getSSLContext())
                .registerHandler("/",
                        new AuthenticationValidationHandler(GET.name(), null, null, getExpectedContent(), user, password))
                .setListenerPort(port)
                .create();
        localServer.start();



        super.setUp();
    }


    HttpContext basicAuthContext() {

        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(user, password);
        BasicCredentialsProvider provider = new BasicCredentialsProvider();
        provider.setCredentials(new AuthScope(null, -1), credentials);

        BasicAuthCache authCache = new BasicAuthCache();
        BasicScheme basicAuth = new BasicScheme();
        authCache.put(new HttpHost("localhost", 8083), basicAuth);

        HttpClientContext context = HttpClientContext.create();
        context.setAuthCache(authCache);
        context.setCredentialsProvider(provider);

        return context;
    }

    @AfterEach
    @Override
    public void tearDown() throws Exception {
        super.tearDown();

        if (localServer != null) {
            localServer.stop();
        }
    }

    @Test
    public void httpsGetWithAuthentication() throws Exception {

        Exchange exchange = template.request("https://127.0.0.1:" + localServer.getLocalPort()
                                             + "/?authUsername=camel&authPassword=password&x509HostnameVerifier=#x509HostnameVerifier&sslContextParameters=#sslContextParameters",
                exchange1 -> {
                });

        assertExchange(exchange);
    }

    @Test
    public void httpsGetWithHttpCache() throws Exception {

        Exchange exchange = template.request("https://localhost:" + localServer.getLocalPort()
                                             + "?throwExceptionOnFailure=false&httpContext=#basicAuthContext",
                exchange1 -> {
                });

        assertExchange(exchange);
    }

    @Override
    protected HttpProcessor getBasicHttpProcessor() {
        List<HttpRequestInterceptor> requestInterceptors = new ArrayList<>();
        requestInterceptors.add(new RequestBasicAuth());
        List<HttpResponseInterceptor> responseInterceptors = new ArrayList<>();
        responseInterceptors.add(new ResponseContent());
        responseInterceptors.add(new ResponseBasicUnauthorized());

        return new ImmutableHttpProcessor(requestInterceptors, responseInterceptors);
    }
}
