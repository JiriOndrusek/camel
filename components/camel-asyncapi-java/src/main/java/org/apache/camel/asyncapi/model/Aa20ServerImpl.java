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
package org.apache.camel.asyncapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.camel.asyncApi.*;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Consumer;

public class Aa20ServerImpl implements Aa20Server {

    String url;
    String protocol;
    String protocolVersion;
    String description;
    Map<String, Aa20ServerVariable> variables = new LinkedHashMap<>();
    @JsonIgnore
    Aa20SecurityRequirement security;
    @JsonProperty("security")
    List<Aa20SecurityRequirement> computedSecurity;
    Aa20ServerBindings bindings;

    public static Aa20ServerImpl.Builder newBuilder(Aa20ObjectImpl.Builder parent, Consumer<Aa20Server> consumer) {
        return new Aa20ServerImpl.Builder(parent, consumer);
    }

    private Aa20ServerImpl() {
    }

    @Override
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @Override
    public String getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(String protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Map<String, Aa20ServerVariable> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Aa20ServerVariable> variables) {
        this.variables = variables;
    }

    @Override
    public Aa20SecurityRequirement getSecurity() {
        return security;
    }

    public void setSecurity(Aa20SecurityRequirement security) {
        this.security = security;
        computeSecurity();
    }

    @Override
    public Aa20ServerBindings getBindings() {
        return bindings;
    }

    public void setBindings(Aa20ServerBindings bindings) {
        this.bindings = bindings;
    }

    // --------------------------------------- builder ---------------------------------------------------------

    public static class Builder extends NestedBuilder<Aa20ObjectImpl.Builder, Aa20Server> {
        private String url;
        private String protocol;
        private String protocolVersion;
        private String description;
        private Map<String, Aa20ServerVariable> aa20ServerVariables = new LinkedHashMap<>();
        private Aa20SecurityRequirement aa20SecurityRequirement;
        private Aa20ServerBindings aa20ServerBindings;

        private Builder(Aa20ObjectImpl.Builder parent, Consumer<Aa20Server> consumer) {
            super(parent, consumer);
        }

        public Builder withUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder withProtocol(String protocol) {
            this.protocol = protocol;
            return this;
        }

        public Builder withProtocolVersion(String protocolVersion) {
            this.protocolVersion = protocolVersion;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withAa20ServerVariable(String name, Aa20ServerVariable variable) {
            this.aa20ServerVariables.put(name, variable);
            return this;
        }


        public Builder withAa20SecurityRequirement(Aa20SecurityRequirement aa20SecurityRequirement) {
            this.aa20SecurityRequirement = aa20SecurityRequirement;
            return this;
        }

        public Builder withAa20ServerBindings(Aa20ServerBindings aa20ServerBindings) {
            this.aa20ServerBindings = aa20ServerBindings;
            return this;
        }

        public Aa20SecurityRequirementImpl.Builder addSecurity() {
            return Aa20SecurityRequirementImpl.newBuilder(this, o -> withAa20SecurityRequirement(o));
        }

        public Aa20ServerVariableImpl.Builder addVariable(String name) {
            return Aa20ServerVariableImpl.newBuilder(this, o -> withAa20ServerVariable(name, o));
        }

        public Aa20ServerBindingsImpl.Builder addBindings() {
            return Aa20ServerBindingsImpl.newBuilder(this, o -> withAa20ServerBindings(o));
        }

        @Override
        public Aa20Server build() {
            Aa20ServerImpl server = new Aa20ServerImpl();
            server.setUrl(this.url);
            server.setProtocol(this.protocol);
            server.setProtocolVersion(this.protocolVersion);
            server.setDescription(this.description);
            server.setSecurity(this.aa20SecurityRequirement);
            server.setVariables(this.aa20ServerVariables);
            server.setBindings(this.aa20ServerBindings);
            return server;
        }

   }

    // --------------------------------------- computed fields ---------------------------------------------------------

    private void computeSecurity() {
        computedSecurity = new LinkedList<>();
        //json structure has to be an array of one type securityRequirementa
        //this method creates a new SecurityRequirement for each type
        if(security.getUserPassword() != null) {
            Aa20SecurityRequirementImpl securityRequirement = new Aa20SecurityRequirementImpl();
            securityRequirement.setUserPassword(security.getUserPassword());
            computedSecurity.add(securityRequirement);
        }

        if(security.getApiKey() != null) {
            Aa20SecurityRequirementImpl securityRequirement = new Aa20SecurityRequirementImpl();
            securityRequirement.setApiKey(security.getApiKey());
            computedSecurity.add(securityRequirement);
        }

        if(security.getX509() != null) {
            Aa20SecurityRequirementImpl securityRequirement = new Aa20SecurityRequirementImpl();
            securityRequirement.setX509(security.getX509());
            computedSecurity.add(securityRequirement);
        }

        if(security.getSymmetricEncryption() != null) {
            Aa20SecurityRequirementImpl securityRequirement = new Aa20SecurityRequirementImpl();
            securityRequirement.setSymmetricEncryption(security.getSymmetricEncryption());
            computedSecurity.add(securityRequirement);
        }

        if(security.getAsymmetricEncryption() != null) {
            Aa20SecurityRequirementImpl securityRequirement = new Aa20SecurityRequirementImpl();
            securityRequirement.setAsymmetricEncryption(security.getAsymmetricEncryption());
            computedSecurity.add(securityRequirement);
        }

        if(security.getHttpApiKey() != null) {
            Aa20SecurityRequirementImpl securityRequirement = new Aa20SecurityRequirementImpl();
            securityRequirement.setHttpApiKey(security.getHttpApiKey());
            computedSecurity.add(securityRequirement);
        }

        if(security.getHttp() != null) {
            Aa20SecurityRequirementImpl securityRequirement = new Aa20SecurityRequirementImpl();
            securityRequirement.setHttp(security.getHttp());
            computedSecurity.add(securityRequirement);
        }

        if(security.getOauth2() != null) {
            Aa20SecurityRequirementImpl securityRequirement = new Aa20SecurityRequirementImpl();
            securityRequirement.setOauth2(security.getOauth2());
            computedSecurity.add(securityRequirement);
        }

        if(security.getOpenIdConnect() != null) {
            Aa20SecurityRequirementImpl securityRequirement = new Aa20SecurityRequirementImpl();
            securityRequirement.setOpenIdConnect(security.getOpenIdConnect());
            computedSecurity.add(securityRequirement);
        }


    }

    public List<Aa20SecurityRequirement> getComputedSecurity() {
        return computedSecurity;
    }

    public void setComputedSecurity(List<Aa20SecurityRequirement> computedSecurity) {
        this.computedSecurity = computedSecurity;

        //fill security
        if(!computedSecurity.isEmpty()) {
            Aa20SecurityRequirementImpl security = new Aa20SecurityRequirementImpl();
            for (Aa20SecurityRequirement req: computedSecurity) {
                if(req.getOpenIdConnect() != null) {
                    security.setOpenIdConnect(req.getOpenIdConnect());
                }
                if(req.getOauth2() != null) {
                    security.setOauth2(req.getOauth2());
                }
                if(req.getHttpApiKey() != null) {
                    security.setHttpApiKey(req.getHttpApiKey());
                }
                if(req.getHttp() != null) {
                    security.setHttp(req.getHttp());
                }
                if(req.getSymmetricEncryption() != null) {
                    security.setSymmetricEncryption(req.getSymmetricEncryption());
                }
                if(req.getX509() != null) {
                    security.setX509(req.getX509());
                }
                if(req.getAsymmetricEncryption() != null) {
                    security.setAsymmetricEncryption(req.getAsymmetricEncryption());
                }
                if(req.getUserPassword() != null) {
                    security.setUserPassword(req.getUserPassword());
                }
                if(req.getApiKey() != null) {
                    security.setApiKey(req.getApiKey());
                }
            }
            this.security = security;
        }
    }
}
