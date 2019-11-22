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

import java.util.List;
import java.util.function.BiConsumer;

public interface UndertowSecurityProvider {


    /**
     * todo allows to add parameters into the exchange
     * @param consumer
     * @param httpExchange
     * @throws Exception
     */
    void addPropertiesIntoExchange(BiConsumer<String, Object> consumer, HttpServerExchange httpExchange) throws Exception;

    /**
     * todo returns true if request should continue
     *
     * @param httpExchange
     * @return
     * @throws Exception
     */
    boolean handleAuthentication(HttpServerExchange httpExchange) throws Exception;

    /**
     * todo returns true, if provider could handle configuration object
     * @param configuration
     * @param allowedRoles
     * @param endpointUri
     * @return
     * @throws Exception
     */
    boolean acceptConfiguration(Object configuration, List<String> allowedRoles, String endpointUri) throws Exception;
}
