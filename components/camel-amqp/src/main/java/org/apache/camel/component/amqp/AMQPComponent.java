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
package org.apache.camel.component.amqp;

import java.util.Map;
import java.util.Set;

import javax.jms.ConnectionFactory;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.asyncApi.Aa20Object;
import org.apache.camel.asyncapi.model.Aa20ObjectImpl;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.component.jms.JmsConfiguration;
import org.apache.camel.component.jms.JmsEndpoint;
import org.apache.camel.spi.AsyncApiConfiguration;
import org.apache.camel.spi.AsyncApiProvider;
import org.apache.camel.spi.annotations.Component;
import org.apache.camel.support.component.PropertyConfigurerSupport;
import org.apache.qpid.jms.JmsConnectionFactory;

/**
 * Messaging with AMQP protocol using Apache QPid Client.
 */
@Component("amqp")
public class AMQPComponent extends JmsComponent implements AsyncApiProvider {

    // Constructors

    public AMQPComponent() {
        super();
    }

    public AMQPComponent(JmsConfiguration configuration) {
        super(configuration);
    }

    public AMQPComponent(CamelContext context) {
        super(context);
    }

    public AMQPComponent(ConnectionFactory connectionFactory) {
        setConnectionFactory(connectionFactory);
    }

    // Factory methods

    public static AMQPComponent amqpComponent(String uri) {
        JmsConnectionFactory connectionFactory = new JmsConnectionFactory(uri);
        connectionFactory.setTopicPrefix("topic://");
        return new AMQPComponent(connectionFactory);
    }

    public static AMQPComponent amqpComponent(String uri, String username, String password) {
        JmsConnectionFactory connectionFactory = new JmsConnectionFactory(username, password, uri);
        connectionFactory.setTopicPrefix("topic://");
        return new AMQPComponent(connectionFactory);
    }

    // Life-cycle

    @Override
    protected void doStart() throws Exception {
        Set<AMQPConnectionDetails> connectionDetails = getCamelContext().getRegistry().findByType(AMQPConnectionDetails.class);
        if (connectionDetails.size() == 1) {
            AMQPConnectionDetails details = connectionDetails.iterator().next();
            JmsConnectionFactory connectionFactory = new JmsConnectionFactory(details.username(), details.password(), details.uri());
            if (details.setTopicPrefix()) {
                connectionFactory.setTopicPrefix("topic://");
            }
            setConnectionFactory(connectionFactory);
        }
        super.doStart();
    }

    @Override
    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
        JmsEndpoint endpoint = (JmsEndpoint) super.createEndpoint(uri, remaining, parameters);
        endpoint.setBinding(new AMQPJmsBinding(endpoint));
        return endpoint;
    }

    /**
     * Factory method to create the default configuration instance
     *
     * @return a newly created configuration object which can then be further
     *         customized
     */
    @Override
    protected JmsConfiguration createConfiguration() {
        return new AMQPConfiguration();
    }

    // Properties

    /**
     * Whether to include AMQP annotations when mapping from AMQP to Camel Message.
     * Setting this to true will map AMQP message annotations to message headers.
     * Due to limitations in Apache Qpid JMS API, currently delivery annotations
     * are ignored.
     */
    public void setIncludeAmqpAnnotations(boolean includeAmqpAnnotations) {
        if (getConfiguration() instanceof AMQPConfiguration) {
            ((AMQPConfiguration) getConfiguration()).setIncludeAmqpAnnotations(includeAmqpAnnotations);
        }
    }

    @Override
    protected void setProperties(Endpoint bean, Map<String, Object> parameters) throws Exception {
        Object includeAmqpAnnotations = parameters.remove("includeAmqpAnnotations");
        if (includeAmqpAnnotations != null) {
            ((AMQPConfiguration) ((JmsEndpoint) bean).getConfiguration())
                    .setIncludeAmqpAnnotations(PropertyConfigurerSupport.property(getCamelContext(), boolean.class, includeAmqpAnnotations));
        }
        super.setProperties(bean, parameters);
    }

    @Override
    public AsyncApiConfiguration getAsyncApiConfiguration() {
//         AsyncApiConfiguration conf =  new AsyncApiConfiguration();
//         conf.setPort(8081);
//         return conf;
return null;
    }

    @Override
    public Aa20Object createAsyncAPIDefinition() {

        Aa20Object model = Aa20ObjectImpl.newBuilder()
                .withAsyncApi("2.0.0")
                .addInfo(info -> info
                        .withTitle("Streetlights API")
                        .withDescription("The Smartylighting Streetlights API allows you to remotely manage the city lights." +
                                "" +
                                "### Check out its awesome features:\n" +
                                "\n" +
                                "    * Turn a specific streetlight on/off \uD83C\uDF03\n" +
                                "    * Dim a specific streetlight \uD83D\uDE0E\n" +
                                "    * Receive real-time information about environmental lighting conditions \uD83D\uDCC8")
                        .withVersion("1.0.0")
                        .addLicense(license -> license
                                .withName("Apache 2.0")
                                .withUrl("https://www.apache.org/licenses/LICENSE-2.0")
                        )
                ).done();



//object.getComponents().getMessages().get("testMessage").asObject(Aa20Message.class).getPayload();
//object.getComponents().getMessages().get("testMessage").asReference().get$ref();
        return model;
    }
}
