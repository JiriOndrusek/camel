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
package org.apache.camel.component.undertow.spi;

import io.undertow.server.HttpServerExchange;
import org.apache.camel.component.undertow.UndertowConsumer;

import java.util.List;
import java.util.function.BiConsumer;

/**
 * SPI interface. Camel-undertow component will locate all available providers and will use first of then who
 * returns true from method acceptConfiguration.
 *
 * Instance of that provider is initialized by previous call of acceptConfiguration and then will be used to authenticate requests.
 */
public interface UndertowSecurityProvider {


    /**
     * Provider can add properties into Camel's exchange. Method is called right after creation of Camel's exchange.
     * Typical usage is to add authentication information into message (eg. authenticated principal)
     *
     * @param consumer BiConsumer is the only way how to add parameter into exchange (it accepts pair String, Object)
     * @param httpExchange Undertow exchange (could contain information from security provider)
     */
    void addProperty(BiConsumer<String, Object> consumer, HttpServerExchange httpExchange) throws Exception;

    /**
     * Method to handle incoming request for security purposes. Method returns true if handling can continue (authorized)
     * and false if request is forbidden.
     *
     * @param httpExchange Undertow exchange
     * @param consumer Undertow consumer
     * @return True if handling can continue.
     */
    boolean authenticate(HttpServerExchange httpExchange, UndertowConsumer consumer) throws Exception;

    /**
     * Initialization of securityProvider from configuration.
     * Object passed to camel-undertow as 'securityConfiguration' should be tested here, if it is mean for this
     * securityProvider and provider should initialize its state from it.
     * If configuration is not acceptable, return false.
     *
     * @param configuration Object which contain connfiguration passed to camel-undertow
     * @param allowedRoles List of allowed roles defined on endpoint.
     * @param endpointUri Uri of endpoint (could be important for intialization)
     * @return True if securityProvider is initialized from data and is able to authenticate requests.
     */
    boolean acceptConfiguration(Object configuration, List<String> allowedRoles, String endpointUri) throws Exception;
}
