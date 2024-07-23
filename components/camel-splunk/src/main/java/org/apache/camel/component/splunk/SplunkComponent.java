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
package org.apache.camel.component.splunk;

import java.util.Map;

import org.apache.camel.Endpoint;
import org.apache.camel.SSLContextParametersAware;
import org.apache.camel.spi.Metadata;
import org.apache.camel.spi.annotations.Component;
import org.apache.camel.support.HealthCheckComponent;
import org.apache.camel.support.jsse.SSLContextParameters;

/**
 * Represents the component that manages {@link SplunkEndpoint}.
 */
@Component("splunk")
public class SplunkComponent extends HealthCheckComponent implements SSLContextParametersAware {

    @Metadata(label = "security")
    private SSLContextParameters sslContextParameters;
    @Metadata(label = "security", defaultValue = "false")
    private boolean useGlobalSslContextParameters;

    @Metadata(label = "advanced")
    private SplunkConfigurationFactory splunkConfigurationFactory = new DefaultSplunkConfigurationFactory();

    public SplunkComponent() {
    }

    @Override
    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
        SplunkConfiguration configuration = splunkConfigurationFactory.parseMap(parameters);
        SplunkEndpoint answer = new SplunkEndpoint(uri, this, configuration);
        setProperties(answer, parameters);
        configuration.setName(remaining);
        return answer;
    }

    public SplunkConfigurationFactory getSplunkConfigurationFactory() {
        return splunkConfigurationFactory;
    }

    /**
     * To use the {@link SplunkConfigurationFactory}
     */
    public void setSplunkConfigurationFactory(SplunkConfigurationFactory splunkConfigurationFactory) {
        this.splunkConfigurationFactory = splunkConfigurationFactory;
    }

    public SSLContextParameters getSslContextParameters() {
        return sslContextParameters;
    }

    /**
     * Sets the default SSL configuration to use for all the endpoints. You can also configure it directly at the
     * endpoint level.
     */
    public void setSslContextParameters(SSLContextParameters sslContextParameters) {
        this.sslContextParameters = sslContextParameters;
    }

    @Override
    public boolean isUseGlobalSslContextParameters() {
        return this.useGlobalSslContextParameters;
    }

    /**
     * Enable usage of global SSL context parameters.
     */
    @Override
    public void setUseGlobalSslContextParameters(boolean useGlobalSslContextParameters) {
        this.useGlobalSslContextParameters = useGlobalSslContextParameters;
    }

}
