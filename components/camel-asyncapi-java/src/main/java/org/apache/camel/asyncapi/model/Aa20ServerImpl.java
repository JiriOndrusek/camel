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

import org.apache.camel.asyncApi.*;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Aa20ServerImpl implements Aa20Server {

    String url;
    String protocol;
    String protocolVersion;
    String description;
    Map<String, Aa20ServerVariable> variables = new LinkedHashMap<>();
    Aa20SecurityRequirement security;
    Aa20ServerBindings bindings;

    public static Aa20ServerImpl.Builder newBuilder(String name) {
        return new Aa20ServerImpl.Builder(name);
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
        private final String _name;
        private String url;
        private String protocol;
        private String protocolVersion;
        private String description;
        private Map<String, Aa20ServerVariable> aa20ServerVariables = new LinkedHashMap<>();
        private Aa20SecurityRequirementImpl aa20SecurityRequirementImpl;
        private Aa20ServerBindingsImpl aa20ServerBindingsImpl;

        private Builder(String name) {
            this._name = name;
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


        public Builder withAa20SecurityRequirementImpl(Aa20SecurityRequirementImpl aa20SecurityRequirementImpl) {
            this.aa20SecurityRequirementImpl = aa20SecurityRequirementImpl;
            return this;
        }

        public Builder withAa20ServerBindingsImpl(Aa20ServerBindingsImpl aa20ServerBindingsImpl) {
            this.aa20ServerBindingsImpl = aa20ServerBindingsImpl;
            return this;
        }

        //
//        public void withAa20ContactImpl(Aa20ContactImpl contact) {
//            this.contact = contact;
//        }
//
//        public void withAa20LicenseImpl(Aa20LicenseImpl license) {
//            this.license = license;
//        }
//
//        public Aa20ContactImpl.Builder addContact() {
//            return contactBuilder;
//        }
//
        public Aa20SecurityRequirementImpl.Builder addSecurityRequirement() {
            return Aa20SecurityRequirementImpl.newBuilder().withParentBuilder(this);
        }

        public Aa20ServerVariableImpl.Builder addVariable(String name) {
            return Aa20ServerVariableImpl.newBuilder(name).withParentBuilder(this);
        }

        public Aa20ServerBindingsImpl.Builder addBindings() {
            return Aa20ServerBindingsImpl.newBuilder().withParentBuilder(this);
        }

        public Aa20ServerBindingsImpl.Builder addSecurity() {
            return Aa20ServerBindingsImpl.newBuilder().withParentBuilder(this);
        }

        @Override
        public Aa20Server build() {
            Aa20ServerImpl server = new Aa20ServerImpl();
            server.setUrl(this.url);
            server.setProtocol(this.protocol);
            server.setProtocolVersion(this.protocolVersion);
            server.setDescription(this.description);
            server.setSecurity(this.aa20SecurityRequirementImpl);
            server.setVariables(this.aa20ServerVariables);
            server.setBindings(this.aa20ServerBindingsImpl);
            return server;
        }

        @Override
        public Aa20ObjectImpl.Builder done() {
            return super.done();
        }

        @Override
        protected void setToParent(Aa20ObjectImpl.Builder parent, Aa20Server build) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
            parent.withAa20Server(this._name, build);
        }
    }
}
