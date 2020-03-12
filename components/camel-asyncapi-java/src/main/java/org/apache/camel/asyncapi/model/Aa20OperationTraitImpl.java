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

import java.util.List;

public class Aa20OperationTraitImpl extends AbstractAa20SpecificationExtensionImpl implements Aa20OperationTrait {

    private String operationId;
    private String summary;
    private String description;
    private List<Aa20Tag> tags;
    private Aa20ExternalDocumentation externalDocs;
    private Aa20OperationBindings bindings;
    private String $ref;

    public static Builder newBuilder() {
        return new Builder();
    }

    private Aa20OperationTraitImpl() {
        super(null);
    }

    public Aa20OperationTraitImpl(Builder b) {
        super(b);
        this.operationId = b.operationId;
        this.summary = b.summary;
        this.description = b.description;
        this.tags = b.tags;
        this.externalDocs = b.externalDocs;
        this.bindings = b.bindings;
        this.$ref = b.$ref;
    }

    @Override
    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
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
    public Aa20OperationBindings getBindings() {
        return bindings;
    }

    public void setBindings(Aa20OperationBindings bindings) {
        this.bindings = bindings;
    }

    @Override
    public String get$ref() {
        return $ref;
    }

    public void set$ref(String $ref) {
        this.$ref = $ref;
    }

// --------------------------------------- builder ---------------------------------------------------------

    public static class Builder extends AbstractSpecificationExtensionsBuilder<Builder, Aa20OperationTrait> {

        private String operationId;
        String summary;
        String description;
        List<Aa20Tag> tags;
        Aa20ExternalDocumentation externalDocs;
        Aa20OperationBindings bindings;
        private String $ref;

        public Builder() {
        }

        public Builder withOperationId(String operationId) {
            this.operationId = operationId;
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

        public Builder withTags(List<Aa20Tag> tags) {
            this.tags = tags;
            return this;
        }

        public Builder withExternalDocs(Aa20ExternalDocumentation externalDocs) {
            this.externalDocs = externalDocs;
            return this;
        }

        public Builder withBindings(Aa20OperationBindings bindings) {
            this.bindings = bindings;
            return this;
        }

        public Builder with$ref(String $ref) {
            this.$ref = $ref;
            return this;
        }


        @Override
        public Aa20OperationTrait done() {
            return new Aa20OperationTraitImpl(this);
        }
    }
}
