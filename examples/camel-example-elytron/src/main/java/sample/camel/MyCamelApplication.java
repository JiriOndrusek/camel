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

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.wildfly.security.WildFlyElytronProvider;
import org.wildfly.security.auth.permission.LoginPermission;
import org.wildfly.security.auth.realm.SimpleMapBackedSecurityRealm;
import org.wildfly.security.auth.realm.SimpleRealmEntry;
import org.wildfly.security.auth.server.SecurityDomain;
import org.wildfly.security.credential.PasswordCredential;
import org.wildfly.security.password.PasswordFactory;
import org.wildfly.security.password.spec.ClearPasswordSpec;
import org.wildfly.security.permission.PermissionVerifier;

import java.security.Provider;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.wildfly.security.password.interfaces.ClearPassword.ALGORITHM_CLEAR;

//CHECKSTYLE:OFF
/**
 */
@SpringBootApplication
@ImportResource({"classpath:spring/camel-context.xml"})
public class MyCamelApplication {

    private static final WildFlyElytronProvider elytronProvider = new WildFlyElytronProvider();

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
            passwordMap.put("camel", new SimpleRealmEntry(Collections.singletonList(new PasswordCredential(passwordFactory.generatePassword(new ClearPasswordSpec("changeit".toCharArray()))))));

            SimpleMapBackedSecurityRealm simpleRealm = new SimpleMapBackedSecurityRealm(() -> new Provider[] { elytronProvider });
            simpleRealm.setPasswordMap(passwordMap);

            SecurityDomain.Builder builder = SecurityDomain.builder()
                    .setDefaultRealmName("TestRealm");

            builder.addRealm("TestRealm", simpleRealm).build();
            builder.setPermissionMapper((principal, roles) -> PermissionVerifier.from(new LoginPermission()));

            return builder;
    }
}
//CHECKSTYLE:ON
