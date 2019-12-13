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

import java.security.PrivateKey;
import java.util.Date;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import io.undertow.util.Headers;
import org.apache.camel.CamelExecutionException;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.http.common.HttpOperationFailedException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wildfly.security.authz.RoleDecoder;

public class ElytronBearerTokenTest extends BaseElytronTest {
    private static final Logger LOG = LoggerFactory.getLogger(ElytronBearerTokenTest.class);

    @Test
    public void testBearerToken() throws Exception {
        MockEndpoint mockEndpoint = getMockEndpoint("mock:myapp");
        mockEndpoint.expectedHeaderReceived(Exchange.HTTP_METHOD, "POST");
        LOG.debug("Number of exchanges in mock:myapp " + mockEndpoint.getExchanges().size());

        String response = template.requestBodyAndHeader("elytron:http://localhost:{{port}}/myapp",
                "empty body",
                Headers.AUTHORIZATION.toString(),
                "Bearer " + createToken("alice", "user",  new Date(new Date().getTime() + 10000), getKeyPair().getPrivate()),
                String.class);
        assertNotNull(response);
        assertEquals("Hello alice!", response);
    }

    @Test
    public void testBearerTokenBadRole() throws Exception {
        MockEndpoint mockEndpoint = getMockEndpoint("mock:myapp");
        mockEndpoint.expectedHeaderReceived(Exchange.HTTP_METHOD, "POST");
        LOG.debug("Number of exchanges in mock:myapp " + mockEndpoint.getExchanges().size());
        HttpOperationFailedException httpOperationFailedException = null;
        try {
            String response = template.requestBodyAndHeader("elytron:http://localhost:{{port}}/myapp",
                    "empty body",
                    Headers.AUTHORIZATION.toString(),
                    "Bearer " + createToken("alice", "guest", new Date(new Date().getTime() + 10000), getKeyPair().getPrivate()),
                    String.class);

        } catch (CamelExecutionException exception) {
            if (exception.getCause() instanceof  HttpOperationFailedException) {
                httpOperationFailedException = (HttpOperationFailedException)exception.getCause();
            }
        }

        assertNotNull(httpOperationFailedException);
        assertEquals(403, httpOperationFailedException.getStatusCode());
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            public void configure() {
                from("elytron:http://localhost:{{port}}/myapp?allowedRoles=user")
                        .transform(simple("Hello ${in.header.securityIdentity.principal}!"));
            }
        };
    }


    private String createToken(String userName, String roles,  Date expirationDate, PrivateKey signingKey) {
        JWTClaimsSet.Builder claimsSet = new JWTClaimsSet.Builder();

        claimsSet.subject("123445667");
        claimsSet.claim("username", userName);
        claimsSet.audience("resource-server");
        claimsSet.issuer("elytron.org");
        claimsSet.claim(RoleDecoder.KEY_ROLES, roles);
        claimsSet.expirationTime(expirationDate);

        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.RS256), claimsSet.build());

        try {
            signedJWT.sign(new RSASSASigner(signingKey));
        } catch (JOSEException e) {
            e.printStackTrace();
        }

        return signedJWT.serialize();
    }
}
