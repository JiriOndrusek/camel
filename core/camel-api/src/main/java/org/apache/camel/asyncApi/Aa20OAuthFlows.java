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
 * Allows configuration of the supported OAuth Flows.
 *
 * todo This object MAY be extended with Specification Extensions.
 */
public class Aa20OAuthFlows {

    private Aa20OAuthFlow implicit;
    private Aa20OAuthFlow password;
    private Aa20OAuthFlow clientCredentials;
    private Aa20OAuthFlow authorizationCode;

    public Aa20OAuthFlow getImplicit() {
        return implicit;
    }

    /**
     * @param implicit Configuration for the OAuth Implicit flow
     */
    public Aa20OAuthFlows setImplicit(Aa20OAuthFlow implicit) {
        this.implicit = implicit;
        return this;
    }

    public Aa20OAuthFlow createImplicit() {
        this.implicit = new Aa20OAuthFlow();
        return this.implicit;
    }

    public Aa20OAuthFlow getPassword() {
        return password;
    }

    /**
     * @param password Configuration for the OAuth Resource Owner Protected Credentials flow
     */
    public Aa20OAuthFlows setPassword(Aa20OAuthFlow password) {
        this.password = password;
        return this;
    }

    public Aa20OAuthFlow createPassword() {
        this.password = new Aa20OAuthFlow();
        return this.password;
    }

    public Aa20OAuthFlow getClientCredentials() {
        return clientCredentials;
    }

    /**
     * @param clientCredentials Configuration for the OAuth Client Credentials flow.
     */
    public Aa20OAuthFlows setClientCredentials(Aa20OAuthFlow clientCredentials) {
        this.clientCredentials = clientCredentials;
        return this;
    }

    public Aa20OAuthFlow createClientCredentials() {
        this.clientCredentials = new Aa20OAuthFlow();
        return this.clientCredentials;
    }

    public Aa20OAuthFlow getAuthorizationCode() {
        return authorizationCode;
    }

    /**
     * @param authorizationCode Configuration for the OAuth Authorization Code flow.
     */
    public Aa20OAuthFlows setAuthorizationCode(Aa20OAuthFlow authorizationCode) {
        this.authorizationCode = authorizationCode;
        return this;
    }

    public Aa20OAuthFlow createAuthorizationCode() {
        this.authorizationCode = new Aa20OAuthFlow();
        return this.authorizationCode;
    }

}
