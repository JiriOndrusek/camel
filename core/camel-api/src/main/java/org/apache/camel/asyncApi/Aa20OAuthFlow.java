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

import java.util.Map;

/**
 * Configuration details for a supported OAuth Flow
 */
public class Aa20OAuthFlow {

    private final String authorizationUrl;
    private final String tokenUrl;
    private String refreshUrl;
    private final Map<String,String> scopes;

    /**
     * @param authorizationUrl The authorization URL to be used for this flow. This MUST be in the form of a URL.
     * @param tokenUrl The token URL to be used for this flow. This MUST be in the form of a URL.
     * @param scopes The available scopes for the OAuth2 security scheme. A map between the scope name and a short description for it.
     */
    public Aa20OAuthFlow(String authorizationUrl, String tokenUrl, Map<String, String> scopes) {
        this.authorizationUrl = authorizationUrl;
        this.tokenUrl = tokenUrl;
        this.scopes = scopes;
    }

    public String getAuthorizationUrl() {
        return authorizationUrl;
    }

    public String getTokenUrl() {
        return tokenUrl;
    }

    public String getRefreshUrl() {
        return refreshUrl;
    }

    /**
     * @param refreshUrl The URL to be used for obtaining refresh tokens. This MUST be in the form of a URL.
     */
    public Aa20OAuthFlow setRefreshUrl(String refreshUrl) {
        this.refreshUrl = refreshUrl;
        return this;
    }

    public Map<String, String> getScopes() {
        return scopes;
    }
}
