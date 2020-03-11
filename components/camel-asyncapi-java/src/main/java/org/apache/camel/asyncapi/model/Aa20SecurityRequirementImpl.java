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

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.camel.asyncApi.Aa20SecurityRequirement;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

@JsonInclude(JsonInclude.Include.ALWAYS)
public class Aa20SecurityRequirementImpl implements Aa20SecurityRequirement {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> userPassword;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> apiKey;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> x509;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> symmetricEncryption;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> asymmetricEncryption;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> httpApiKey;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> http;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> oauth2;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> openIdConnect;


    public static Builder newBuilder() {
        return new Builder();
    }

    private Aa20SecurityRequirementImpl() {
    }

    private Aa20SecurityRequirementImpl(Builder b) {
        this.apiKey = b.apiKey;
        this.userPassword = b.userPassword;
        this.x509 = b.x509;
        this.asymmetricEncryption = b.asymmetricEncryption;
        this.symmetricEncryption = b.symmetricEncryption;
        this.httpApiKey = b.httpApiKey;
        this.http = b.http;
        this.oauth2 = b.oauth2;
        this.openIdConnect = b.openIdConnect;
    }

    @Override
    public List<String> getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(List<String> userPassword) {
        this.userPassword = userPassword;
    }

    @Override
    public List<String> getApiKey() {
        return apiKey;
    }

    public void setApiKey(List<String> apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public List<String> getX509() {
        return x509;
    }

    public void setX509(List<String> x509) {
        this.x509 = x509;
    }

    @Override
    public List<String> getSymmetricEncryption() {
        return symmetricEncryption;
    }

    public void setSymmetricEncryption(List<String> symmetricEncryption) {
        this.symmetricEncryption = symmetricEncryption;
    }

    @Override
    public List<String> getAsymmetricEncryption() {
        return asymmetricEncryption;
    }

    public void setAsymmetricEncryption(List<String> asymmetricEncryption) {
        this.asymmetricEncryption = asymmetricEncryption;
    }

    @Override
    public List<String> getHttpApiKey() {
        return httpApiKey;
    }

    public void setHttpApiKey(List<String> httpApiKey) {
        this.httpApiKey = httpApiKey;
    }

    @Override
    public List<String> getHttp() {
        return http;
    }

    public void setHttp(List<String> http) {
        this.http = http;
    }

    @Override
    public List<String> getOauth2() {
        return oauth2;
    }

    public void setOauth2(List<String> oauth2) {
        this.oauth2 = oauth2;
    }

    @Override
    public List<String> getOpenIdConnect() {
        return openIdConnect;
    }

    public void setOpenIdConnect(List<String> openIdConnect) {
        this.openIdConnect = openIdConnect;
    }

// --------------------------------------- builder ---------------------------------------------------------

    public static class Builder extends AbstractBuilder<Aa20SecurityRequirement> {
        private List<String> userPassword;
        private List<String> apiKey;
        private List<String> x509;
        private List<String> symmetricEncryption;
        private List<String> asymmetricEncryption;
        private List<String> httpApiKey;
        private List<String> http;
        private List<String> oauth2;
        private List<String> openIdConnect;



        private Builder() {

        }

        public Builder withUserPassword() {
            if(userPassword == null){
                userPassword = new LinkedList<>();
            }
            return this;
        }

        public Builder withUserPassword(String schema) {
            withUserPassword();
            this.userPassword.add(schema);
            return this;
        }

        public Builder withUserPassword(List<String> schemas) {
            this.userPassword = schemas;
            return this;
        }

        public Builder withApiKey() {
            if(apiKey == null){
                apiKey = new LinkedList<>();
            }
            return this;
        }

        public Builder withApiKey(String schema) {
            withApiKey();
            this.apiKey.add(schema);
            return this;
        }

        public Builder withApiKey(List<String> schemas) {
            this.apiKey = schemas;
            return this;
        }


        public Builder withX509() {
            if(x509 == null){
                x509 = new LinkedList<>();
            }
            return this;
        }

        public Builder withX509(String schema) {
            withX509();
            this.x509.add(schema);
            return this;
        }


        public Builder withX509(List<String> schemas) {
            this.x509 = schemas;
            return this;
        }


        public Builder withAsymmetricEncryption() {
            if(asymmetricEncryption == null){
                asymmetricEncryption = new LinkedList<>();
            }
            return this;
        }

        public Builder withAsymmetricEncryption(String schema) {
            withAsymmetricEncryption();
            this.asymmetricEncryption.add(schema);
            return this;
        }

        public Builder withAsymmetricEncryption(List<String> schemas) {
            this.asymmetricEncryption = schemas;
            return this;
        }


        public Builder withSymmetricEncryption() {
            if(symmetricEncryption == null){
                symmetricEncryption = new LinkedList<>();
            }
            return this;
        }

        public Builder withSymmetricEncryption(String schema) {
            withSymmetricEncryption();
            this.symmetricEncryption.add(schema);
            return this;
        }

        public Builder withSymmetricEncryption(List<String> schemas) {
            this.symmetricEncryption = schemas;
            return this;
        }


        public Builder withHttpApiKey() {
            if(httpApiKey == null){
                httpApiKey = new LinkedList<>();
            }
            return this;
        }

        public Builder withHttpApiKey(String schema) {
            withHttpApiKey();
            this.httpApiKey.add(schema);
            return this;
        }

        public Builder withHttpApiKey(List<String> schemas) {
            this.httpApiKey = schemas;
            return this;
        }

        public Builder withHttp() {
            if(http == null){
                http = new LinkedList<>();
            }
            return this;
        }

        public Builder withHttp(String schema) {
            withHttp();
            this.http.add(schema);
            return this;
        }

        public Builder withHttp(List<String> schemas) {
            this.http = schemas;
            return this;
        }

        public Builder withOAuth2() {
            if(oauth2 == null){
                oauth2 = new LinkedList<>();
            }
            return this;
        }

        public Builder withOAuth2(String schema) {
            withOAuth2();
            this.oauth2.add(schema);
            return this;
        }

        public Builder withOAuth2(List<String> schemas) {
            this.oauth2 = schemas;
            return this;
        }

        public Builder withOpenIdConnect() {
            if(openIdConnect == null){
                openIdConnect = new LinkedList<>();
            }
            return this;
        }

        public Builder withOpenIdConnect(String schema) {
            withOpenIdConnect();
            this.openIdConnect.add(schema);
            return this;
        }

        public Builder withOpenIdConnect(List<String> schemas) {
            this.openIdConnect = schemas;
            return this;
        }


        @Override
        public Aa20SecurityRequirement done() {
            return new Aa20SecurityRequirementImpl(this);
        }
    }
}
