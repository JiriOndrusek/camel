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

public class Aa20SecuritySchemaOpenIdConnect extends Aa20AbstractSecuritySchema implements Aa20OrReferenceType<Aa20SecuritySchemaOpenIdConnect> {

    /**
     * An object containing configuration information for the flow types supported.
     */
    private final String openIdConnect;

    /**
     * @param openIdConnect  OpenId Connect URL to discover OAuth2 configuration values. This MUST be in the form of a URL.
     */
    public Aa20SecuritySchemaOpenIdConnect(String openIdConnect) {
        super(Type.openIdConnect);
        this.openIdConnect = openIdConnect;
    }

    public String getOpenIdConnect() {
        return openIdConnect;
    }
}
