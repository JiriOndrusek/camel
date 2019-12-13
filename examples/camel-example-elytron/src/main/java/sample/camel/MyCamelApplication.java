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

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.wildfly.security.WildFlyElytronProvider;
import org.wildfly.security.auth.permission.LoginPermission;
import org.wildfly.security.auth.realm.SimpleMapBackedSecurityRealm;
import org.wildfly.security.auth.realm.SimpleRealmEntry;
import org.wildfly.security.auth.realm.token.TokenSecurityRealm;
import org.wildfly.security.auth.realm.token.validator.JwtValidator;
import org.wildfly.security.auth.server.SecurityDomain;
import org.wildfly.security.authz.MapAttributes;
import org.wildfly.security.authz.RoleMapper;
import org.wildfly.security.authz.Roles;
import org.wildfly.security.credential.PasswordCredential;
import org.wildfly.security.password.PasswordFactory;
import org.wildfly.security.password.spec.ClearPasswordSpec;
import org.wildfly.security.permission.PermissionVerifier;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.nimbusds.jwt.JWTClaimsSet;

import static org.wildfly.security.password.interfaces.ClearPassword.ALGORITHM_CLEAR;

//CHECKSTYLE:OFF
/**
 */
@SpringBootApplication
@ImportResource({"classpath:spring/camel-context.xml"})
public class MyCamelApplication {

    private static final WildFlyElytronProvider elytronProvider = new WildFlyElytronProvider();
    @Autowired
    private KeyPair keyPair;

    /**
     * A main method to start this application.
     */
    public static void main(String[] args) {
        SpringApplication.run(MyCamelApplication.class, args);
    }


    @Bean(name = "mySecurityDomainBuilder")
    public SecurityDomain.Builder createSecurityDomainBuilder() throws Exception {

            PasswordFactory passwordFactory = PasswordFactory.getInstance(ALGORITHM_CLEAR, elytronProvider);

            Map<String, SimpleRealmEntry> passwordMap = new HashMap<>();
            passwordMap.put("admin",
                    new SimpleRealmEntry(Collections.singletonList(new PasswordCredential(passwordFactory.generatePassword(new ClearPasswordSpec("admin".toCharArray())))),
                    new MapAttributes(Collections.singletonMap("Roles", Arrays.asList("user", "admin")))));
            passwordMap.put("user",
                    new SimpleRealmEntry(Collections.singletonList(new PasswordCredential(passwordFactory.generatePassword(new ClearPasswordSpec("user".toCharArray())))),
                    new MapAttributes(Collections.singletonMap("Roles", Collections.singletonList("user")))));
            passwordMap.put("guest",
                     new SimpleRealmEntry(Collections.singletonList(new PasswordCredential(passwordFactory.generatePassword(new ClearPasswordSpec("guest".toCharArray()))))));

            SimpleMapBackedSecurityRealm simpleRealm = new SimpleMapBackedSecurityRealm(() -> new Provider[] { elytronProvider });
            simpleRealm.setPasswordMap(passwordMap);

            SecurityDomain.Builder builder = SecurityDomain.builder()
                    .setDefaultRealmName("bearerRealm");

//            builder.addRealm("simpleRealm", simpleRealm).build();

            addBearerRealm(builder, "bearerRealm");

            builder.setPermissionMapper((principal, roles) -> PermissionVerifier.from(new LoginPermission()));
            builder.setRoleMapper(RoleMapper.constant(Roles.of("guest")).or((roles) -> roles));


            return builder;
    }

    private void addBearerRealm(SecurityDomain.Builder builder, String name) throws Exception {
        builder.addRealm(name, TokenSecurityRealm.builder().principalClaimName("username")
                .validator(JwtValidator.builder().publicKey(getKeyPair().getPublic()).build()).build())
                .build();
    }

    @Bean(name = "myKeyPair")
    public KeyPair getKeyPair() throws NoSuchAlgorithmException {
        if (keyPair == null) {
            keyPair = generateKeyPair();
        }
        return keyPair;
    }

    private KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        return KeyPairGenerator.getInstance("RSA").generateKeyPair();
    }


}
//CHECKSTYLE:ON
