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
package org.apache.camel.component.undertow;

import java.net.URI;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.UndertowOptions;
import io.undertow.server.HandlerWrapper;
import io.undertow.server.handlers.PathHandler;
import io.undertow.server.handlers.PathTemplateHandler;
import io.undertow.server.handlers.sse.ServerSentEventConnectionCallback;
import io.undertow.server.handlers.sse.ServerSentEventHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.server.HttpHandler;
import io.undertow.servlet.api.ServletContainerInitializerInfo;
import io.undertow.servlet.core.DeploymentImpl;
import io.undertow.servlet.util.ImmediateInstanceFactory;
import org.apache.camel.component.undertow.handlers.CamelRootHandler;
import org.apache.camel.component.undertow.handlers.NotFoundHandler;
import org.apache.camel.component.undertow.handlers.RestRootHandler;
import org.apache.camel.component.undertow.spi.UndertowSecurityProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * The default UndertowHost which manages standalone Undertow server.
 */
public class DefaultUndertowHost implements UndertowHost {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultUndertowHost.class);

    private final UndertowHostKey key;
    private final UndertowHostOptions options;
    private final CamelRootHandler rootHandler;
    private final RestRootHandler restHandler;
    private Undertow undertow;
    private String hostString;

    public DefaultUndertowHost(UndertowHostKey key) {
        this(key, null);
    }

    public DefaultUndertowHost(UndertowHostKey key, UndertowHostOptions options) {
        this.key = key;
        this.options = options;
        this.rootHandler = new CamelRootHandler(new NotFoundHandler());
        this.restHandler = new RestRootHandler();
        this.restHandler.init(key.getPort());
    }

    @Override
    public void validateEndpointURI(URI httpURI) {
        // all URIs are good
    }

    @Override
    public synchronized HttpHandler registerHandler(UndertowConsumer consumer, HttpHandlerRegistrationInfo registrationInfo, HttpHandler handler, UndertowSecurityProvider securityProvider) {
        if (undertow == null) {
            Undertow.Builder builder = Undertow.builder();
            if (key.getSslContext() != null) {
                builder.addHttpsListener(key.getPort(), key.getHost(), key.getSslContext());
            } else {
                builder.addHttpListener(key.getPort(), key.getHost());
            }

            if (options != null) {
                if (options.getIoThreads() != null) {
                    builder.setIoThreads(options.getIoThreads());
                }
                if (options.getWorkerThreads() != null) {
                    builder.setWorkerThreads(options.getWorkerThreads());
                }
                if (options.getBufferSize() != null) {
                    builder.setBufferSize(options.getBufferSize());
                }
                if (options.getDirectBuffers() != null) {
                    builder.setDirectBuffers(options.getDirectBuffers());
                }
                if (options.getHttp2Enabled() != null) {
                    builder.setServerOption(UndertowOptions.ENABLE_HTTP2, options.getHttp2Enabled());
                }
            }

            if (consumer != null && consumer.isRest()) {
                // use the rest handler as its a rest consumer
                undertow = builder.setHandler(restHandler).build();
            } else {

                if(securityProvider.securityFilter() != null) {

                    DeploymentInfo deployment = Servlets.deployment();
                    deployment.setDeploymentName("camel-undertow");
                    deployment.setContextPath("/");
                    deployment.setDisplayName("camel-undertow");
                    deployment.setClassLoader(getClass().getClassLoader());

                    Initializer initializer = new Initializer(securityProvider.securityFilter());
                    deployment.addServletContainerInitializers(new ServletContainerInitializerInfo(
                            Initializer.class,
                            new ImmediateInstanceFactory<ServletContainerInitializer>(initializer),
                            Collections.emptySet()));

                    DeploymentManager manager = Servlets.newContainer().addDeployment(deployment);

                    manager.deploy();
                    try {
                        manager.start();
                    } catch (ServletException e) {
                        e.printStackTrace();
                    }

                    undertow = builder.setHandler(manager.getDeployment().getHandler()).build();
                } else {
                    undertow = builder.setHandler(handler).build();
                }

            }
            LOG.info("Starting Undertow server on {}://{}:{}", key.getSslContext() != null ? "https" : "http", key.getHost(), key.getPort());

            try {
                // If there is an exception while starting up, Undertow wraps it
                // as RuntimeException which leaves the consumer in an inconsistent
                // state as a subsequent start if the route (i.e. manually) won't
                // start the Undertow instance as undertow is not null.
                undertow.start();
            } catch (RuntimeException e) {
                LOG.warn("Failed to start Undertow server on {}://{}:{}, reason: {}", key.getSslContext() != null ? "https" : "http", key.getHost(), key.getPort(), e.getMessage());

                // Cleanup any resource that may have been created during start
                // and reset the instance so a subsequent start will trigger the
                // initialization again.
                undertow.stop();
                undertow = null;

                throw e;
            }
        }
        if (consumer != null && consumer.isRest()) {
            restHandler.addConsumer(consumer);
            return restHandler;
        } else {
            return rootHandler.add(registrationInfo.getUri().getPath(), registrationInfo.getMethodRestrict(), registrationInfo.isMatchOnUriPrefix(), handler);
        }
    }


    /**
     * todo
     */
    private static class Initializer implements ServletContainerInitializer {

        private final Filter delegatingFilterProxy;

        Initializer(Filter delegatingFilterProxy) {
            this.delegatingFilterProxy = delegatingFilterProxy;
        }

        @Override
        public void onStartup(Set<Class<?>> classes, ServletContext servletContext) {
            FilterRegistration f = servletContext.addFilter("springSecurityFilterChain", delegatingFilterProxy);
            f.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");;
        }
    }

    @Override
    public synchronized void unregisterHandler(UndertowConsumer consumer, HttpHandlerRegistrationInfo registrationInfo) {
        if (undertow == null) {
            return;
        }

        boolean stop;
        if (consumer != null && consumer.isRest()) {
            restHandler.removeConsumer(consumer);
            stop = restHandler.consumers() <= 0;
        } else {
            rootHandler.remove(registrationInfo.getUri().getPath(), registrationInfo.getMethodRestrict(), registrationInfo.isMatchOnUriPrefix());
            stop = rootHandler.isEmpty();
        }

        if (stop) {
            LOG.info("Stopping Undertow server on {}://{}:{}", key.getSslContext() != null ? "https" : "http", key.getHost(), key.getPort());
            undertow.stop();
            undertow = null;
        }
    }

    @Override
    public String toString() {
        if (hostString == null) {
            hostString = String.format("DefaultUndertowHost[%s://%s:%s]", key.getSslContext() != null ? "https" : "http", key.getHost(), key.getPort());
        }
        return hostString;
    }
}
