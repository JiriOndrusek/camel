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
package org.apache.camel.spi;

import java.util.Map;


public class ApiConfiguration {

    private String component;
    private String scheme;
    private String host;
    private int port;
    private String contextPath;

    /**
     * Gets the name of the Camel component to use as the REST consumer
     *
     * @return the component name, or <tt>null</tt> to let Camel search the {@link Registry} to find suitable implementation
     */
    public String getComponent() {
        return component;
    }

    /**
     * Sets the name of the Camel component to use as the REST consumer
     *
     * @param componentName the name of the component (such as netty-http, jetty, servlet, undertow, etc.)
     */
    public void setComponent(String componentName) {
        this.component = componentName;
    }

    /**
     * Gets the hostname to use by the REST consumer
     *
     * @return the hostname, or <tt>null</tt> to use default hostname
     */
    public String getHost() {
        return host;
    }

    /**
     * Sets the hostname to use by the REST consumer
     *
     * @param host the hostname
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * Gets the scheme to use by the REST consumer
     *
     * @return the scheme, or <tt>null</tt> to use default scheme
     */
    public String getScheme() {
        return scheme;
    }

    /**
     * Sets the scheme to use by the REST consumer
     *
     * @param scheme the scheme
     */
    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    /**
     * Gets the port to use by the REST consumer
     *
     * @return the port, or <tt>0</tt> or <tt>-1</tt> to use default port
     */
    public int getPort() {
        return port;
    }

    /**
     * Sets the port to use by the REST consumer
     *
     * @param port the port number
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Gets the configured context-path
     *
     * @return the context path, or <tt>null</tt> if none configured.
     */
    public String getContextPath() {
        return contextPath;
    }

    /**
     * Sets a leading context-path the REST services will be using.
     * <p/>
     * This can be used when using components such as <tt>camel-servlet</tt> where the deployed web application
     * is deployed using a context-path. Or for components such as <tt>camel-jetty</tt> or <tt>camel-netty-http</tt>
     * that includes a HTTP server.
     *
     * @param contextPath the context path
     */
    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

}
