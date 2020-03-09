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
package org.apache.camel.asyncApi;

import java.util.*;

/**
 * An object representing a message broker, a server or any other kind of computer
 * program capable of sending and/or receiving data. This object is used to capture
 * details such as URIs, protocols and security configuration. Variable substitution
 * can be used so that some details, for example usernames and passwords, can be
 * injected by code generation tools.
 */
public class Aa20Server {

    String url;
    String protocol;
    String protocolVersion;
    String description;
    Map<String, Aa20ServerVariable> variables = new LinkedHashMap<>();
    List<Aa20SecurityRequirement> security = new LinkedList();
    Aa20ServerBindings bindings;


    public Aa20Server(String url, String protocol) {
        this.url = url;
        this.protocol = protocol;
    }

    public String getUrl() {
        return url;
    }

    /**
     * REQUIRED. A URL to the target host. This URL supports Server Variables and MAY
     * be relative, to indicate that the host location is relative to the location where
     * the AsyncAPI document is being served. Variable substitutions will be made when
     * a variable is named in {brackets}.
     */
    public Aa20Server setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getProtocol() {
        return protocol;
    }

    /**
     * REQUIRED. The protocol this URL supports for connection. Supported protocol include,
     * but are not limited to: amqp, amqps, http, https, jms, kafka, kafka-secure, mqtt,
     * secure-mqtt, stomp, stomps, ws, wss.
     */
    public Aa20Server setProtocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    public String getProtocolVersion() {
        return protocolVersion;
    }

    /**
     * The version of the protocol used for connection. For instance: AMQP 0.9.1,
     * HTTP 2.0, Kafka 1.0.0, etc.
     */
    public Aa20Server setProtocolVersion(String protocolVersion) {
        this.protocolVersion = protocolVersion;
        return this;
    }

    public String getDescription() {
        return description;
    }

    /**
     * An optional string describing the host designated by the URL. CommonMark
     * syntax MAY be used for rich text representation.
     */
    public Aa20Server setDescription(String description) {
        this.description = description;
        return this;
    }

    public Map<String, Aa20ServerVariable> getVariables() {
        return variables;
    }

    /**
     * A map between a variable name and its value. The value is used for substitution in the server’s URL template.
     */
    public Aa20Server setVariables(Map<String, Aa20ServerVariable> variables) {
        this.variables = variables;
        return this;
    }

    public Aa20ServerVariable createVariable(String name) {
        Aa20ServerVariable variable = new Aa20ServerVariable();
        this.variables.put(name, variable);
        return variable;
    }

    public List<Aa20SecurityRequirement> getSecurity() {
        return security;
    }

    public void setSecurity(List<Aa20SecurityRequirement> security) {
        this.security = security;
    }

    public Aa20SecurityRequirementApiKey createSecurityRequirementApiKey() {
        Aa20SecurityRequirementApiKey securityRequirement = new Aa20SecurityRequirementApiKey();
        this.security.add(securityRequirement);
        return securityRequirement;
    }

    public Aa20SecurityRequirementAsymetricEncryption createSecurityRequirementAsymetricEncryption() {
        Aa20SecurityRequirementAsymetricEncryption securityRequirement = new Aa20SecurityRequirementAsymetricEncryption();
        this.security.add(securityRequirement);
        return securityRequirement;
    }
    public Aa20SecurityRequirementHttp createSecurityRequirementHttp() {
        Aa20SecurityRequirementHttp securityRequirement = new Aa20SecurityRequirementHttp();
        this.security.add(securityRequirement);
        return securityRequirement;
    }
    public Aa20SecurityRequirementHttpApiKey createSecurityRequirementHttpApiKey() {
        Aa20SecurityRequirementHttpApiKey securityRequirement = new Aa20SecurityRequirementHttpApiKey();
        this.security.add(securityRequirement);
        return securityRequirement;
    }

    public Aa20SecurityRequirementOAuth2 createSecurityRequirementOAuth2() {
        Aa20SecurityRequirementOAuth2 securityRequirement = new Aa20SecurityRequirementOAuth2();
        this.security.add(securityRequirement);
        return securityRequirement;
    }

    public Aa20SecurityRequirementOpenIdConnect createSecurityRequirementOpenIdConnect() {
        Aa20SecurityRequirementOpenIdConnect securityRequirement = new Aa20SecurityRequirementOpenIdConnect();
        this.security.add(securityRequirement);
        return securityRequirement;
    }

    public Aa20SecurityRequirementSymetricEncryption createSecurityRequirementSymetricEncryption() {
        Aa20SecurityRequirementSymetricEncryption securityRequirement = new Aa20SecurityRequirementSymetricEncryption();
        this.security.add(securityRequirement);
        return securityRequirement;
    }

    public Aa20SecurityRequirementUserPassword createSecurityRequirementUserPassword() {
        Aa20SecurityRequirementUserPassword securityRequirement = new Aa20SecurityRequirementUserPassword();
        this.security.add(securityRequirement);
        return securityRequirement;
    }

    public Aa20SecurityRequirementX509Certificate createSecurityRequirementX509Certificate() {
        Aa20SecurityRequirementX509Certificate securityRequirement = new Aa20SecurityRequirementX509Certificate();
        this.security.add(securityRequirement);
        return securityRequirement;
    }

    public Aa20ServerBindings getBindings() {
        return bindings;
    }

    /**
     * A free-form map where the keys describe the name of the protocol and the values describe protocol-specific definitions for the server.
     */
    public Aa20Server setBindings(Aa20ServerBindings bindings) {
        this.bindings = bindings;
        return this;
    }
}
