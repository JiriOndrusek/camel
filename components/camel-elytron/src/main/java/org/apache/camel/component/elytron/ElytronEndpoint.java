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


import io.undertow.server.HttpServerExchange;
import org.apache.camel.Consumer;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.undertow.UndertowComponent;
import org.apache.camel.component.undertow.UndertowEndpoint;
import org.apache.camel.spi.UriEndpoint;
import org.apache.camel.spi.UriParam;
import org.wildfly.security.auth.server.SecurityIdentity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author JiriOndrusek
 */
//TODO
@UriEndpoint(firstVersion = "2.16.0", scheme = "elytron", title = "Elytron", syntax = "elytron:httpURI",
        label = "http,websocket", lenientProperties = true)
public class ElytronEndpoint extends UndertowEndpoint {

    public ElytronEndpoint(String uri, UndertowComponent component) {
        super(uri, component);
    }

    public ElytronComponent getElytronComponent() {
        return (ElytronComponent) super.getComponent();
    }

    @UriParam(label = "allowedRoles")
    private List<String> allowedRoles = Collections.emptyList();

    @Override
    public Consumer createConsumer(Processor processor) throws Exception {
        return new ElytronConsumer(this, processor);
    }

    @Override
    public Exchange createExchange(HttpServerExchange httpExchange) throws Exception {
        Exchange exchange = super.createExchange(httpExchange);

        SecurityIdentity securityIdentity = getElytronComponent().getSecurityDomain().getCurrentSecurityIdentity();
        //add security principal to headers
        exchange.getIn().setHeader("securityIdentity", securityIdentity);

        return exchange;
    }

    /**
     * TODO Comma separated lost of allowed roles.
     * @return
     */
    public List<String> getAllowedRoles() {
        return allowedRoles;
    }

    public void setAllowedRoles(List<String> allowedRoles) {
        this.allowedRoles = allowedRoles;
    }

    public void setAllowedRoles(String allowedRoles) {
        this.allowedRoles = allowedRoles == null ? null : Arrays.asList(allowedRoles.split("\\s*,\\s*"));
    }
}
