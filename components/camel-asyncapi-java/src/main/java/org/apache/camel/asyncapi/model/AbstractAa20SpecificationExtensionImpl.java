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

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class AbstractAa20SpecificationExtensionImpl {

    private Map<String, Object> specificationExtensions = new LinkedHashMap<>();

    AbstractAa20SpecificationExtensionImpl(AbstractSpecificationExtensionsBuilder b) {
        if(b != null) {
            specificationExtensions.putAll(b.specificationExtensions);
        }
    }

    @JsonAnyGetter
    public Map<String, Object> getSpecificationExtensions() {
        return specificationExtensions;
    }

    @JsonAnySetter
    public void addSpecificationExtensions(String propertyKey, Object value) {
        this.specificationExtensions.put(propertyKey, value);
    }

    public Object getSpecificationExtension(String name) {
        return specificationExtensions.get(name);
    }
    @JsonIgnore
    public Set<String> getSpecificationExtensionKeys() {
        return specificationExtensions.keySet();
    }

    protected static abstract class AbstractSpecificationExtensionsBuilder<B, T> extends AbstractBuilder {

        protected Map<String, Object> specificationExtensions = new LinkedHashMap<>();

        public abstract T done();

        public B withSpecificationExtension(String name, Object value) {
            if(!name.startsWith("x-")) {
                throw new IllegalArgumentException("todo");
            }
            specificationExtensions.put(name, value);

            return (B)this;
        }
    }
}
