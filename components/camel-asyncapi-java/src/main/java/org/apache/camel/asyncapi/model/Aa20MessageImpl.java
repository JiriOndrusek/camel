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

import org.apache.camel.asyncApi.*;

import java.util.*;
import java.util.function.Consumer;

public class Aa20MessageImpl extends AbstractAa20SpecificationExtensionImpl implements Aa20Message {

    private Aa20Schema headers;
    private Map<String, Object> payload = new LinkedHashMap();
    private Aa20CorellationId correlationId;
    private String schemaFormat;
    private String contentType;
    private String name;
    private String title;
    private String summary;
    private String description;
    List<Aa20Tag> tags;
    Aa20ExternalDocumentation externalDocs;
    Aa20MessageBindings bindings;
    private List<String> examples;
    private List<Aa20MessageTrait> traits;
    private Set<Aa20Message> oneOf;
    private String $ref;

    public static Builder newBuilder() {
        return new Builder();
    }

    private Aa20MessageImpl() {
        super(null);
    }

    public Aa20MessageImpl(Builder b) {
        super(b);
        this.headers = b.headers;
        this.payload = b.payload;
        this.correlationId = b.correlationId;
        this.schemaFormat = b.schemaFormat;
        this.contentType = b.contentType;
        this.name = b.name;
        this.title = b.title;
        this.examples = b.examples;
        this.summary = b.summary;
        this.description = b.description;
        this.tags = b.tags;
        this.externalDocs = b.externalDocs;
        this.bindings = b.bindings;
        this.traits = b.traits;
        this.oneOf = b.oneOf;
        this.$ref = b.$ref;
    }

    @Override
    public Aa20Schema getHeaders() {
        return headers;
    }

    public void setHeaders(Aa20Schema headers) {
        this.headers = headers;
    }

    @Override
    public Aa20CorellationId getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(Aa20CorellationId correlationId) {
        this.correlationId = correlationId;
    }

    @Override
    public String getSchemaFormat() {
        return schemaFormat;
    }

    public void setSchemaFormat(String schemaFormat) {
        this.schemaFormat = schemaFormat;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public List<String> getExamples() {
        return examples;
    }

    public void setExamples(List<String> examples) {
        this.examples = examples;
    }

    @Override
    public Map<String, Object> getPayload() {
        return payload;
    }

    public void setPayload(Map<String, Object> payload) {
        this.payload = payload;
    }

    @Override
    public List<Aa20MessageTrait> getTraits() {
        return traits;
    }

    public void setTraits(List<Aa20MessageTrait> traits) {
        this.traits = traits;
    }

    @Override
    public Set<Aa20Message> getOneOf() {
        return oneOf;
    }

    public void setOneOf(Set<Aa20Message> oneOf) {
        this.oneOf = oneOf;
    }

    @Override
    public String get$ref() {
        return $ref;
    }

    public void set$ref(String $ref) {
        this.$ref = $ref;
    }

    @Override
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public List<Aa20Tag> getTags() {
        return tags;
    }

    public void setTags(List<Aa20Tag> tags) {
        this.tags = tags;
    }

    @Override
    public Aa20ExternalDocumentation getExternalDocs() {
        return externalDocs;
    }

    public void setExternalDocs(Aa20ExternalDocumentation externalDocs) {
        this.externalDocs = externalDocs;
    }

    @Override
    public Aa20MessageBindings getBindings() {
        return bindings;
    }

    public void setBindings(Aa20MessageBindings bindings) {
        this.bindings = bindings;
    }

    // --------------------------------------- builder ---------------------------------------------------------

    public static class Builder extends AbstractSpecificationExtensionsBuilder<Builder, Aa20Message> {

        public Builder() {
        }

        private Aa20Schema headers;
        private Map<String, Object> payload = new LinkedHashMap();
        private Aa20CorellationId correlationId;
        private String schemaFormat;
        private String contentType;
        private String name;
        private String title;
        private String summary;
        private String description;
        List<Aa20Tag> tags;
        Aa20ExternalDocumentation externalDocs;
        Aa20MessageBindings bindings;
        private List<String> examples;
        private List<Aa20MessageTrait> traits;
        private Set<Aa20Message> oneOf;
        private String $ref;

        public Builder withHeaders(Aa20Schema headers) {
            this.headers = headers;
            return this;
        }

        public Builder withCorrelationId(Aa20CorellationId correlationId) {
            this.correlationId = correlationId;
            return this;
        }

        public Builder withSchemaFormat(String schemaFormat) {
            this.schemaFormat = schemaFormat;
            return this;
        }

        public Builder withContentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withExamples(List<String> examples) {
            this.examples = examples;
            return this;
        }

        public Builder withPayload(Map<String, Object> payload) {
            if(this.payload == null) {
                this.payload = new HashMap<>();
            }
            this.payload = payload;
            return this;
        }

        public Builder withTrait(Aa20MessageTrait trait) {
            if(this.traits == null) {
                this.traits = new LinkedList<>();
            }
            this.traits.add(trait);
            return this;
        }

        public Builder withOneOf(Aa20Message message) {
            if(this.oneOf == null) {
                this.oneOf = new HashSet<>();
            }
            this.oneOf.add(message);
            return this;
        }


        public Builder with$ref(String $ref) {
            this.$ref = $ref;
            return this;
        }

        public Builder withSummary(String summary) {
            this.summary = summary;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withTags(Aa20Tag tag) {
            if(this.tags == null) {
                this.tags = new LinkedList<>();
            }
            this.tags.add(tag);
            return this;
        }

        public Builder withExternalDocs(Aa20ExternalDocumentation externalDocs) {
            this.externalDocs = externalDocs;
            return this;
        }

        public Builder withBindings(Aa20MessageBindings bindings) {
            this.bindings = bindings;
            return this;
        }


        @Override
        public Aa20Message done() {
            return new Aa20MessageImpl(this);
        }
    }
}
