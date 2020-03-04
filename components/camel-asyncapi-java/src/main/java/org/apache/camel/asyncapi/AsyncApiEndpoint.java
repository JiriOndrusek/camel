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

import org.apache.camel.*;
import org.apache.camel.spi.*;
import org.apache.camel.support.DefaultEndpoint;
import org.apache.camel.util.HostUtils;
import org.apache.camel.util.ObjectHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.text.html.Option;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * todo from restEndpoint
 */
@UriEndpoint(firstVersion = "3.1",  scheme = "async-api", title = "async-api", syntax = "async-api:path", label = "todo")
public class AsyncApiEndpoint extends DefaultEndpoint {

    public static final String[] DEFAULT_ASYNC_API_CONSUMER_COMPONENTS = new String[]{"undertow"};

    private static final Logger LOG = LoggerFactory.getLogger(AsyncApiEndpoint.class);

    @UriParam(label = "consumer")
    private String consumerComponentName;

    @UriParam(label = "asyncApiComponent")
    private String asyncApiComponent;

    @UriPath(label = "common", enums = "get,post,put,delete,patch,head,trace,connect,options") @Metadata(required = true)
    private String method = "GET"; //todo
    @UriPath(label = "common") @Metadata(required = true)
    private String path = "/api-doc"; //todo
    @UriPath(label = "common")
    private String uriTemplate;
    @UriParam(label = "common")
    private String consumes;
    @UriParam(label = "common")
    private String produces;

    private Map<String, Object> parameters = new HashMap<>();

    public AsyncApiEndpoint(String endpointUri, AsyncApiComponent component) {
        super(endpointUri, component);
    }

    @Override
    public AsyncApiComponent getComponent() {
        return (AsyncApiComponent) super.getComponent();
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

    public String getAsyncApiComponent() {
        return asyncApiComponent;
    }

    /**
     * todo The Camel Rest component to use for (consumer) the REST transport, such as jetty, servlet, undertow.
     * If no component has been explicit configured, then Camel will lookup if there is a Camel component
     * that integrates with the Rest DSL, or if a org.apache.camel.spi.RestConsumerFactory is registered in the registry.
     * If either one is found, then that is being used.
     */
    public void setAsyncApiComponent(String asyncApiComponent) {
        this.asyncApiComponent = asyncApiComponent;
    }

    @Override
    public Producer createProducer() throws Exception {
        Optional<FoundComponent> asyncApiConfigurationOwner = findComponent(getAsyncApiComponent(), AsyncApiConfigurationOwner.class);

        if(!asyncApiConfigurationOwner.isPresent()) {
            throw new IllegalStateException("Cannot find AsyncApiConfigurationOwner");
        }
        AsyncApiConfiguration config = ((AsyncApiConfigurationOwner)asyncApiConfigurationOwner.get().getFactory()).getAsyncApiConfiguration();


            // if no explicit port/host configured, then use port from rest configuration
            String host = "";
            int port = 8081;

//            todo
//             if (config.getApiHost() != null) {
//                host = config.getApiHost();
//            } else if (config.getHost() != null) {
//                host = config.getHost();
//            }
            int num = config.getPort();
            if (num > 0) {
                port = num;
            }

            // if no explicit hostname set then resolve the hostname
            if (ObjectHelper.isEmpty(host)) {
                if (config.getHostNameResolver() == RestConfiguration.RestHostNameResolver.allLocalIp) {
                    host = "0.0.0.0";
                } else if (config.getHostNameResolver() == RestConfiguration.RestHostNameResolver.localHostName) {
                    host = HostUtils.getLocalHostName();
                } else if (config.getHostNameResolver() == RestConfiguration.RestHostNameResolver.localIp) {
                    host = HostUtils.getLocalIp();
                }
//todo
                port = 8081;

                // no host was configured so calculate a host to use
                // there should be no schema in the host (but only port)
                String targetHost = host + (port != 80 ? ":" + port : "");
                getParameters().put("host", targetHost);
            }

            // the base path should start with a leading slash
            String path = getPath();
            if (path != null && !path.startsWith("/")) {
                path = "/" + path;
            }

            // whether listing of the context id's is enabled or not
        //todo
            boolean contextIdListing = false;

            Processor processor = new AsyncApiProcessor(config);
            return new AsyncApiProducer(this, processor);
    }

    @Override
    public Consumer createConsumer(Processor processor) throws Exception {
        Optional<FoundComponent> asyncApiConfigurationOwner = findComponent(getAsyncApiComponent(), AsyncApiConfigurationOwner.class);
        Optional<FoundComponent> asyncApiConsumerFactory= findComponent(getConsumerComponentName(), AsyncApiConsumerFactory.class);

        if(!asyncApiConsumerFactory.isPresent()) {
            throw new IllegalStateException("Cannot find RestConsumerFactory in Registry or as a Component to use");
        } else if(!asyncApiConfigurationOwner.isPresent()) {
            throw new IllegalStateException("Cannot find AsyncApiConfigurationOwner in Registry or as a Component to use");
        } else {
            AsyncApiConsumerFactory factory = (AsyncApiConsumerFactory)asyncApiConsumerFactory.get().getFactory();
            String cname = asyncApiConsumerFactory.get().getCname();

            String scheme = "http";
            String host = "";
            int port = 80;

            AsyncApiConfigurationOwner owner = (AsyncApiConfigurationOwner)asyncApiConfigurationOwner.get().getFactory();
            AsyncApiConfiguration config = owner.getAsyncApiConfiguration();
            if (config.getScheme() != null) {
                scheme = config.getScheme();
            }
            if (config.getHost() != null) {
                host = config.getHost();
            }
            int num = config.getPort();
            if (num > 0) {
                port = num;
            }

            // if no explicit hostname set then resolve the hostname
            if (ObjectHelper.isEmpty(host)) {
                if (config.getHostNameResolver() == RestConfiguration.RestHostNameResolver.allLocalIp) {
                    host = "0.0.0.0";
                } else if (config.getHostNameResolver() == RestConfiguration.RestHostNameResolver.localHostName) {
                    host = HostUtils.getLocalHostName();
                } else if (config.getHostNameResolver() == RestConfiguration.RestHostNameResolver.localIp) {
                    host = HostUtils.getLocalIp();
                }
            }

            // calculate the url to the rest service
            String path = getPath();
            if (!path.startsWith("/")) {
                path = "/" + path;
            }

            // there may be an optional context path configured to help Camel calculate the correct urls for the REST services
            // this may be needed when using camel-servlet where we cannot get the actual context-path or port number of the servlet engine
            // during init of the servlet
            String contextPath = config.getContextPath();
            if (contextPath != null) {
                if (!contextPath.startsWith("/")) {
                    path = "/" + contextPath + path;
                } else {
                    path = contextPath + path;
                }
            }

            String baseUrl = scheme + "://" + host + (port != 80 ? ":" + port : "") + path;

            String url = baseUrl;
            if (uriTemplate != null) {
                // make sure to avoid double slashes
                if (uriTemplate.startsWith("/")) {
                    url = url + uriTemplate;
                } else {
                    url = url + "/" + uriTemplate;
                }
            }

            Consumer consumer = factory.createConsumer(getCamelContext(), processor, getMethod(), getPath(),
                    getUriTemplate(), getConsumes(), getProduces(), config, getParameters());
            configureConsumer(consumer);

            return consumer;
        }
    }


    public  <F> Optional<FoundComponent> findComponent(String componentName, Class<F> interfaceType) throws NoSuchBeanException {
        F factory = null;
        String name = null;
        if (componentName != null) {
            Object comp = getCamelContext().getRegistry().lookupByName(componentName);
            if (comp != null && interfaceType.isAssignableFrom(comp.getClass())) {
                factory = interfaceType.cast(comp);
            } else {
                comp = getCamelContext().getComponent(componentName, true);
                if (comp != null && interfaceType.isAssignableFrom(comp.getClass())) {
                    factory = interfaceType.cast(comp);
                }
            }

            if (factory == null) {
                if (comp != null) {
                    throw new IllegalArgumentException("Component " + getConsumerComponentName() + " is not a AsyncApiConsumerFactory");
                } else {
                    throw new NoSuchBeanException(getConsumerComponentName(), AsyncApiConsumerFactory.class.getName());
                }
            }
            name = componentName;
        }

        // try all components
        if (factory == null) {
            for (String n : getCamelContext().getComponentNames()) {
                Component comp = getCamelContext().getComponent(n);
                if (comp != null && interfaceType.isAssignableFrom(comp.getClass())) {
                    factory = interfaceType.cast(comp);
                    name = n;
                    break;
                }
            }
        }

        // lookup in registry
        if (factory == null) {
            Set<F> factories = getCamelContext().getRegistry().findByType(interfaceType);
            if (factories != null && factories.size() == 1) {
                factory = factories.iterator().next();
            }
        }

        // no explicit factory found then try to see if we can find any of the default rest consumer components
        // and there must only be exactly one so we safely can pick this one
        if (factory == null) {
            F found = null;
            String foundName = null;
            for (String n : DEFAULT_ASYNC_API_CONSUMER_COMPONENTS) {
                Object comp = getCamelContext().getComponent(n, true);
                if (comp != null && interfaceType.isAssignableFrom(comp.getClass())) {
                    if (found == null) {
                        found = interfaceType.cast(comp);
                        foundName = n;
                    } else {
                        throw new IllegalArgumentException("Multiple AsyncApiConsumerFactory found on classpath. Configure explicit which component to use");
                    }
                }
            }
            if (found != null) {
                LOG.debug("Auto discovered {} as AsyncApiConsumerFactory", foundName);
                factory = found;
                name = foundName;
            }
        }
        return factory == null ? Optional.empty() : Optional.of(new FoundComponent((Component)factory, name));
    }

    @Override
    public boolean isLenientProperties() {
        return true;
    }

    private class FoundComponent {
        final private Component factory;
        final private String cname;

        public FoundComponent(Component factory, String cname) {
            this.factory = factory;
            this.cname = cname;
        }

        public Component getFactory() {
            return factory;
        }

        public String getCname() {
            return cname;
        }

    }
    public String getMethod() {
        return method;
    }

    /**
     * HTTP method to use.
     */
    public void setMethod(String method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    /**
     * The base path
     */
    public void setPath(String path) {
        this.path = path;
    }

    public String getUriTemplate() {
        return uriTemplate;
    }

    /**
     * The uri template
     */
    public void setUriTemplate(String uriTemplate) {
        this.uriTemplate = uriTemplate;
    }

    public String getConsumes() {
        return consumes;
    }

    /**
     * Media type such as: 'text/xml', or 'application/json' this REST service accepts.
     * By default we accept all kinds of types.
     */
    public void setConsumes(String consumes) {
        this.consumes = consumes;
    }

    public String getProduces() {
        return produces;
    }

    /**
     * Media type such as: 'text/xml', or 'application/json' this REST service returns.
     */
    public void setProduces(String produces) {
        this.produces = produces;
    }


    public Map<String, Object> getParameters() {
        return parameters;
    }

    /**
     * Additional parameters to configure the consumer of the REST transport for this REST service
     */
    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }
}
