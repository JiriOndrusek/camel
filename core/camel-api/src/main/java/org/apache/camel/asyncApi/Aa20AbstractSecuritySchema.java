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

public abstract class Aa20AbstractSecuritySchema {

    public enum Type {
        userPassword,
        apiKey,
        X509,
        symmetricEncryption,
        asymmetricEncryption,
        httpApiKey,
        http,
        oauth2,
        openIdConnect;
    }

    private final Type type;
    private String description;

    /**
     * @param type The type of the security scheme. Valid values are "userPassword", "apiKey", "X509", "symmetricEncryption", "asymmetricEncryption", "httpApiKey", "http", oauth2, and openIdConnect.
     */
    public Aa20AbstractSecuritySchema(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    /**
     * @param description A short description for security scheme. CommonMark syntax MAY be used for rich text representation.
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
