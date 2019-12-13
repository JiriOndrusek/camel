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
package sample.camel;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.util.Date;

import io.undertow.util.Headers;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author JiriOndrusek
 */
@Component
public class MyCamelRouter extends RouteBuilder {

    @Autowired
    private KeyPair keyPair;

    @Override
    public void configure() throws Exception {

        from("elytron:http://localhost:8082/undertow?allowedRoles=user")
                .transform(simple("Hello ${in.header.securityIdentity.principal}!"));

        from("timer://runOnce?delay=1000")
                .setHeader(Headers.AUTHORIZATION.toString(), () -> "Bearer " + createToken("alice", new Date(new Date().getTime() + 1000000), keyPair.getPrivate()))
                .to("elytron:http://localhost:8082/undertow")
                .log("${body}");
    }

    private String createToken(String userName, Date expirationDate, PrivateKey signingKey)  {
        JWTClaimsSet.Builder claimsSet = new JWTClaimsSet.Builder();

        claimsSet.subject("123445667");
        claimsSet.claim("username", userName);
        claimsSet.audience("resource-server");
        claimsSet.issuer("elytron.org");
        claimsSet.claim(RoleDecoder.KEY_ROLES, "user");
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
