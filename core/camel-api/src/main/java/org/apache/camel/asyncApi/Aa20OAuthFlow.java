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

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Configuration details for a supported OAuth Flow
 */
public class Aa20OAuthFlow {

    private String authorizationUrl;
    private String tokenUrl;
    private String refreshUrl;
    private Map<String,String> scopes = new LinkedHashMap<>();


    public String getAuthorizationUrl() {
        return authorizationUrl;
    }

    public String getTokenUrl() {
        return tokenUrl;
    }

    public String getRefreshUrl() {
        return refreshUrl;
    }

    public Aa20OAuthFlow setAuthorizationUrl(String authorizationUrl) {
        this.authorizationUrl = authorizationUrl;
        return this;
    }

    public Aa20OAuthFlow setTokenUrl(String tokenUrl) {
        this.tokenUrl = tokenUrl;
        return this;
    }

    public void setScopes(Map<String, String> scopes) {
        this.scopes = scopes;
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

    public Aa20OAuthFlow createScope(String name, String description) {
        scopes.put(name, description);
        return this;
    }

}
