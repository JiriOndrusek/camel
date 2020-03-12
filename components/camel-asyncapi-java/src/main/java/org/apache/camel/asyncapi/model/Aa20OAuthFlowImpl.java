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

import org.apache.camel.asyncApi.Aa20Contact;
import org.apache.camel.asyncApi.Aa20OAuthFlow;

import java.util.HashMap;
import java.util.Map;

public class Aa20OAuthFlowImpl extends AbstractAa20SpecificationExtensionImpl implements Aa20OAuthFlow {

    String authorizationUrl;
    String tokenUrl;
    String refreshUrl;
    Map<String, String> scopes;

    public static Builder newBuilder() {
        return new Builder();
    }

    private Aa20OAuthFlowImpl() {
        super(null);
    }

    private Aa20OAuthFlowImpl(Builder b) {
        super(b);
        this.authorizationUrl = b.authorizationUrl;
        this.tokenUrl = b.tokenUrl;
        this.refreshUrl = b.refreshUrl;
        this.scopes = b.scopes;
    }

    @Override
    public String getAuthorizationUrl() {
        return authorizationUrl;
    }

    public void setAuthorizationUrl(String authorizationUrl) {
        this.authorizationUrl = authorizationUrl;
    }

    @Override
    public String getTokenUrl() {
        return tokenUrl;
    }

    public void setTokenUrl(String tokenUrl) {
        this.tokenUrl = tokenUrl;
    }

    @Override
    public String getRefreshUrl() {
        return refreshUrl;
    }

    public void setRefreshUrl(String refreshUrl) {
        this.refreshUrl = refreshUrl;
    }

    @Override
    public Map<String, String> getScopes() {
        return scopes;
    }

    public void setScopes(Map<String, String> scopes) {
        this.scopes = scopes;
    }


// --------------------------------------- builder ---------------------------------------------------------

    public static class Builder extends AbstractSpecificationExtensionsBuilder<Builder, Aa20OAuthFlow> {
        String authorizationUrl;
        String tokenUrl;
        String refreshUrl;
        Map<String, String> scopes;

        private Builder() {
        }

        public Builder withAuthorizationUrl(String authorizationUrl) {
            this.authorizationUrl = authorizationUrl;
            return this;
        }

        public Builder withTokenUrl(String tokenUrl) {
            this.tokenUrl = tokenUrl;
            return this;
        }

        public Builder withRefreshUrl(String refreshUrl) {
            this.refreshUrl = refreshUrl;
            return this;
        }

        public Builder withScope(String name, String description) {
            if(this.scopes == null) {
                scopes = new HashMap<>();
            }
            this.scopes.put(name, description);
            return this;
        }

        @Override
        public Aa20OAuthFlow done() {
            return new Aa20OAuthFlowImpl(this);
        }
    }
}
