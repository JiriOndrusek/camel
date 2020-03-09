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
    List<Aa20SecurityRequirement> security = new LinkedList();
    Aa20ServerBindings bindings;

    public static Aa20ServerImpl.Builder newBuilder(String name) {
        return new Aa20ServerImpl.Builder(name);
    }

    private Aa20ServerImpl() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

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

    public Map<String, Aa20ServerVariable> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Aa20ServerVariable> variables) {
        this.variables = variables;
    }

    public List<Aa20SecurityRequirement> getSecurity() {
        return security;
    }

    public void setSecurity(List<Aa20SecurityRequirement> security) {
        this.security = security;
    }

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
        private Map<String, Aa20ServerVariable> variables;
        private List<Aa20SecurityRequirement> aa20SecurityRequirements;
//        List
private Aa20ServerBindings bindings;
//        private Map<String, todo> licenseBuilder = Aa20LicenseImpl.newBuilder().withParentBuilder(this);

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

        public Builder setVariables(Map<String, Aa20ServerVariable> variables) {
            this.variables = variables;
            return this;
        }


        public Builder withAa20SecurityRequirement(Aa20SecurityRequirement aa20SecurityRequirement) {
            if(aa20SecurityRequirements == null) {
                aa20SecurityRequirements = new LinkedList<>();
            }
            this.aa20SecurityRequirements.add(aa20SecurityRequirement);
            return this;
        }

        public Builder setBindings(Aa20ServerBindings bindings) {
            this.bindings = bindings;
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
        public Aa20SecurityRequirementImpl.Builder addSecurityRequirement(Aa20SchemaType type) {
            return Aa20SecurityRequirementImpl.newBuilder(type).withParentBuilder(this);
        }

        @Override
        public Aa20Server build() {
            Aa20ServerImpl server = new Aa20ServerImpl();
            server.setUrl(this.url);
            server.setProtocol(this.protocol);
            server.setProtocolVersion(this.protocolVersion);
            server.setDescription(this.description);
            server.setSecurity(new LinkedList<>(this.aa20SecurityRequirements));

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
