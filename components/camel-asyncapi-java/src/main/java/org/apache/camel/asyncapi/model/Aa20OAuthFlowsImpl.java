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

import org.apache.camel.asyncApi.Aa20OAuthFlow;
import org.apache.camel.asyncApi.Aa20OAuthFlows;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Aa20OAuthFlowsImpl extends AbstractAa20SpecificationExtensionImpl implements Aa20OAuthFlows {

    private Aa20OAuthFlow implicit;
    private Aa20OAuthFlow password;
    private Aa20OAuthFlow clientCredentials;
    private Aa20OAuthFlow authorizationCode;

    public static Builder newBuilder() {
        return new Builder();
    }

    private Aa20OAuthFlowsImpl() {
        super(null);
    }

    private Aa20OAuthFlowsImpl(Builder b) {
        super(b);
        this.implicit = b.implicit;
        this.password = b.password;
        this.clientCredentials = b.clientCredentials;
        this.authorizationCode = b.authorizationCode;
    }

    @Override
    public Aa20OAuthFlow getImplicit() {
        return implicit;
    }

    public void setImplicit(Aa20OAuthFlow implicit) {
        this.implicit = implicit;
    }

    @Override
    public Aa20OAuthFlow getPassword() {
        return password;
    }

    public void setPassword(Aa20OAuthFlow password) {
        this.password = password;
    }

    @Override
    public Aa20OAuthFlow getClientCredentials() {
        return clientCredentials;
    }

    public void setClientCredentials(Aa20OAuthFlow clientCredentials) {
        this.clientCredentials = clientCredentials;
    }

    @Override
    public Aa20OAuthFlow getAuthorizationCode() {
        return authorizationCode;
    }

    public void setAuthorizationCode(Aa20OAuthFlow authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

// --------------------------------------- builder ---------------------------------------------------------

    public static class Builder extends AbstractSpecificationExtensionsBuilder<Builder, Aa20OAuthFlows> {
        private Aa20OAuthFlow implicit;
        private Aa20OAuthFlow password;
        private Aa20OAuthFlow clientCredentials;
        private Aa20OAuthFlow authorizationCode;


        private Builder() {
        }

        public Builder withImplicit(Consumer<Aa20OAuthFlowImpl.Builder> implicit) {
            Aa20OAuthFlowImpl.Builder builder = Aa20OAuthFlowImpl.newBuilder();
            implicit.accept(builder);
            this.implicit = builder.done();
            return this;
        }

        public Builder withPassword(Consumer<Aa20OAuthFlowImpl.Builder> password) {
            Aa20OAuthFlowImpl.Builder builder = Aa20OAuthFlowImpl.newBuilder();
            password.accept(builder);
            this.password = builder.done();
            return this;
        }

        public Builder withClientCredentials(Consumer<Aa20OAuthFlowImpl.Builder> clientCredentials) {
            Aa20OAuthFlowImpl.Builder builder = Aa20OAuthFlowImpl.newBuilder();
            clientCredentials.accept(builder);
            this.clientCredentials = builder.done();
            return this;
        }

        public Builder withAuthorizationCode(Consumer<Aa20OAuthFlowImpl.Builder> authorizationCode) {
            Aa20OAuthFlowImpl.Builder builder = Aa20OAuthFlowImpl.newBuilder();
            authorizationCode.accept(builder);
            this.authorizationCode = builder.done();
            return this;
        }

        @Override
        public Aa20OAuthFlows done() {
            return new Aa20OAuthFlowsImpl(this);
        }
    }
}
