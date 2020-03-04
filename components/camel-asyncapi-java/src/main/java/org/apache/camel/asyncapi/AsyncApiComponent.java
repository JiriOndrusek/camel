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
package org.apache.camel.asyncapi;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.component.extension.ComponentVerifierExtension;
import org.apache.camel.spi.Metadata;
import org.apache.camel.spi.RestConfiguration;
import org.apache.camel.spi.RestConfiguration.RestBindingMode;
import org.apache.camel.spi.RestConfiguration.RestHostNameResolver;
import org.apache.camel.spi.UriParam;
import org.apache.camel.support.CamelContextHelper;
import org.apache.camel.support.DefaultComponent;
import org.apache.camel.util.FileUtil;
import org.apache.camel.util.StringHelper;
import org.apache.camel.util.URISupport;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * todo from restComponent
 */
@org.apache.camel.spi.annotations.Component("async-api")
//@Metadata(label = "verifiers", enums = "parameters,connectivity")
@Metadata(label = "consumer")
public class AsyncApiComponent extends DefaultComponent {

    public static final String DEFAULT_REST_CONFIGURATION_ID = "asayncapi-configuration";

    @Metadata(label = "consumer", defaultValue = "undertow")
    private String consumerComponentName = "undertow";

    @UriParam(label = "asyncApiComponent")
    private String asyncApiComponentName;

    public AsyncApiComponent() {

    }

    @Override
    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
        String consumerName = getAndRemoveParameter(parameters, "consumerComponentName", String.class, consumerComponentName);

        AsyncApiEndpoint answer = new AsyncApiEndpoint(uri, this);
        answer.setConsumerComponentName(consumerName);

        return answer;
    }

    public String getConsumerComponentName() {
        return consumerComponentName;
    }

    /**
     * The Camel Rest component to use for (consumer) the REST transport, such as jetty, servlet, undertow.
     * If no component has been explicit configured, then Camel will lookup if there is a Camel component
     * that integrates with the Rest DSL, or if a org.apache.camel.spi.RestConsumerFactory is registered in the registry.
     * If either one is found, then that is being used.
     */
    public void setConsumerComponentName(String consumerComponentName) {
        this.consumerComponentName = consumerComponentName;
    }

    public String getAsyncApiComponentName() {
        return asyncApiComponentName;
    }

    /**
     * todo The Camel Rest component to use for (consumer) the REST transport, such as jetty, servlet, undertow.
     * If no component has been explicit configured, then Camel will lookup if there is a Camel component
     * that integrates with the Rest DSL, or if a org.apache.camel.spi.RestConsumerFactory is registered in the registry.
     * If either one is found, then that is being used.
     */
    public void setAsyncApiComponentName(String asyncApiComponentName) {
        this.asyncApiComponentName = asyncApiComponentName;
    }
}
