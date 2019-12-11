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

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import org.wildfly.security.auth.server.SecurityIdentity;

/**
 * @author JiriOndrusek
 */
@Component
public class MyCamelRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("elytron:http://localhost:8081/camel_elytron_guest?allowedRoles=guest")
                .process(exchange -> {
                        SecurityIdentity securityIdentity = (SecurityIdentity)exchange.getIn().getHeader("securityIdentity");
                        exchange.getIn().setBody("Page for guests. Hello " + securityIdentity.getPrincipal());
                    });

        from("elytron:http://localhost:8081/camel_elytron_user?allowedRoles=user")
                .process(exchange -> {
                        SecurityIdentity securityIdentity = (SecurityIdentity)exchange.getIn().getHeader("securityIdentity");
                        exchange.getIn().setBody("Page for users. Hello " + securityIdentity.getPrincipal());
                    });

        from("elytron:http://localhost:8081/camel_elytron_admin?allowedRoles=admin")
                .process(exchange -> {
                    SecurityIdentity securityIdentity = (SecurityIdentity)exchange.getIn().getHeader("securityIdentity");
                    exchange.getIn().setBody("Page for admins. Hello " + securityIdentity.getPrincipal());
                });

    }

}
