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
import org.apache.camel.asyncApi.Aa20SchemaType;
import org.apache.camel.asyncApi.Aa20SecurityRequirement;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

@JsonInclude(JsonInclude.Include.ALWAYS)
public abstract class Aa20SecurityRequirementImpl implements Aa20SecurityRequirement {

    private List<String> schemas;

    public static Aa20SecurityRequirementImpl.Builder newBuilder(Aa20SchemaType type) {
        return new Aa20SecurityRequirementImpl.Builder(type);
    }

    protected Aa20SecurityRequirementImpl() {
    }

    protected List<String> getSchemas() {
        return schemas;
    }

    public void setSchemas(List<String> schemas) {
        this.schemas = schemas;
    }

    // --------------------------------------- builder ---------------------------------------------------------

    public static class Builder extends NestedBuilder<Aa20ServerImpl.Builder, Aa20SecurityRequirement> {
        private final Aa20SchemaType type;
        private List<String> schemas;

        private Builder(Aa20SchemaType type) {
            this.type = type;
        }

        public Builder withSchema(String name) {
            if(schemas == null) {
                schemas = new LinkedList<>();
            }
            this.schemas.add(name);
            return this;
        }

        public List<String> getSchemas() {
            return schemas;
        }

        @Override
        public Aa20SecurityRequirement build() {
            Aa20SecurityRequirementImpl securityRequirement;
            //todo add other trypes
            switch(type) {
                case apiKey: securityRequirement = new Aa20SecurityRequirementApiKeyImpl();
                        break;
                case openIdConnect: securityRequirement = new Aa20SecurityRequirementOpenIdConnectImpl();
                        break;
                default: securityRequirement = new Aa20SecurityRequirementOAuth2Impl();
            }
            securityRequirement.setSchemas(this.schemas);
            return securityRequirement;
        }

        @Override
        protected void setToParent(Aa20ServerImpl.Builder parent, Aa20SecurityRequirement build) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
            parent.withAa20SecurityRequirement(build);
        }
    }
}
