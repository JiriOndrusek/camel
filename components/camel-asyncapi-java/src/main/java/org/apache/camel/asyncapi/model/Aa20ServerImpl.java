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

import java.util.*;

public class Aa20ServerImpl extends AbstractAa20SpecificationExtensionImpl implements Aa20Server {

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

    public static Builder newBuilder() {
        return new Builder();
    }

    private Aa20ServerImpl() {
        super(null);
    }

    private Aa20ServerImpl(Builder b) {
        super(b);
        this.url = b.url;
        this.protocol = b.protocol;
        this.protocolVersion = b.protocolVersion;
        this.description = b.description;
        this.security = b.security;
        this.variables = b.variables;
        this.bindings = b.bindings;

        computeSecurity();
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

    public static class Builder extends AbstractSpecificationExtensionsBuilder<Builder, Aa20Server> {
        private String url;
        private String protocol;
        private String protocolVersion;
        private String description;
        private Map<String, Aa20ServerVariable> variables;
        private Aa20SecurityRequirement security;
        private Aa20ServerBindings bindings;

        private Builder() {

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

        public Builder withVariable(String name, Aa20ServerVariable variable) {
            if(variables == null) {
                variables = new LinkedHashMap<>();
            }
            this.variables.put(name, variable);
            return this;
        }


        public Builder withSecurity(Aa20SecurityRequirement security) {
            this.security = security;
            return this;
        }

        public Builder withBindings(Aa20ServerBindings bindings) {
            this.bindings = bindings;
            return this;
        }

        @Override
        public Aa20Server done() {
            return new Aa20ServerImpl(this);
        }
    }

    // --------------------------------------- computed fields ---------------------------------------------------------

    private void computeSecurity() {
        computedSecurity = new LinkedList<>();
        if(security == null) {
            return;
        }
        //json structure has to be an array of one type securityRequirementa
        //this method creates a new SecurityRequirement for each type
        if(security.getUserPassword() != null) {
            computedSecurity.add(Aa20SecurityRequirementImpl.newBuilder().withUserPassword(security.getUserPassword()).done());
        }

        if(security.getApiKey() != null) {
            computedSecurity.add(Aa20SecurityRequirementImpl.newBuilder().withApiKey(security.getApiKey()).done());
        }

        if(security.getX509() != null) {
            computedSecurity.add(Aa20SecurityRequirementImpl.newBuilder().withX509(security.getX509()).done());
        }

        if(security.getSymmetricEncryption() != null) {
            computedSecurity.add(Aa20SecurityRequirementImpl.newBuilder().withSymmetricEncryption(security.getSymmetricEncryption()).done());
        }

        if(security.getAsymmetricEncryption() != null) {
            computedSecurity.add(Aa20SecurityRequirementImpl.newBuilder().withAsymmetricEncryption(security.getAsymmetricEncryption()).done());
        }

        if(security.getHttpApiKey() != null) {
            computedSecurity.add(Aa20SecurityRequirementImpl.newBuilder().withHttpApiKey(security.getHttpApiKey()).done());
        }

        if(security.getHttp() != null) {
            computedSecurity.add(Aa20SecurityRequirementImpl.newBuilder().withHttp(security.getHttp()).done());
        }

        if(security.getOauth2() != null) {
            computedSecurity.add(Aa20SecurityRequirementImpl.newBuilder().withOAuth2(security.getOauth2()).done());
        }

        if(security.getOpenIdConnect() != null) {
            computedSecurity.add(Aa20SecurityRequirementImpl.newBuilder().withOpenIdConnect(security.getOpenIdConnect()).done());
        }


    }

    public List<Aa20SecurityRequirement> getComputedSecurity() {
        return computedSecurity;
    }

    public void setComputedSecurity(List<Aa20SecurityRequirement> computedSecurity) {
        this.computedSecurity = computedSecurity;

        //fill security
        if(!computedSecurity.isEmpty()) {
            Aa20SecurityRequirementImpl.Builder securityBuilder = Aa20SecurityRequirementImpl.newBuilder();
            for (Aa20SecurityRequirement req: computedSecurity) {
                if(req.getOpenIdConnect() != null) {
                    securityBuilder.withOpenIdConnect(req.getOpenIdConnect());
                }
                if(req.getOauth2() != null) {
                    securityBuilder.withOAuth2(req.getOauth2());
                }
                if(req.getHttpApiKey() != null) {
                    securityBuilder.withHttpApiKey(req.getHttpApiKey());
                }
                if(req.getHttp() != null) {
                    securityBuilder.withHttp(req.getHttp());
                }
                if(req.getSymmetricEncryption() != null) {
                    securityBuilder.withSymmetricEncryption(req.getSymmetricEncryption());
                }
                if(req.getX509() != null) {
                    securityBuilder.withX509(req.getX509());
                }
                if(req.getAsymmetricEncryption() != null) {
                    securityBuilder.withAsymmetricEncryption(req.getAsymmetricEncryption());
                }
                if(req.getUserPassword() != null) {
                    securityBuilder.withUserPassword(req.getUserPassword());
                }
                if(req.getApiKey() != null) {
                    securityBuilder.withApiKey(req.getApiKey());
                }
            }
            this.security = security;
        }
    }
}
