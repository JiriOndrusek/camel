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

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class Aa20OperationImpl extends AbstractAa20SpecificationExtensionImpl implements Aa20Operation {

    private String operationId;
    private String summary;
    private String description;
    private List<Aa20Tag> tags;
    private Aa20ExternalDocumentation externalDocs;
    private Aa20OperationBindings bindings;
    private List<Aa20OperationTrait> traits;
    private Aa20Message message;

    public static Builder newBuilder() {
        return new Builder();
    }

    private Aa20OperationImpl() {
        super(null);
    }

    public Aa20OperationImpl(Builder b) {
        super(b);
        this.operationId = b.operationId;
        this.summary = b.summary;
        this.description = b.description;
        this.tags = b.tags;
        this.externalDocs = b.externalDocs;
        this.bindings = b.bindings;
        this.traits = b.traits;
        this.message = b.message;
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

    public Aa20OperationBindings getBindings() {
        return bindings;
    }

    public void setBindings(Aa20OperationBindings bindings) {
        this.bindings = bindings;
    }

    @Override
    public List<Aa20OperationTrait> getTraits() {
        return traits;
    }

    public void setTraits(List<Aa20OperationTrait> traits) {
        this.traits = traits;
    }

    @Override
    public Aa20Message getMessage() {
        return message;
    }

    public void setMessage(Aa20Message message) {
        this.message = message;
    }



// --------------------------------------- builder ---------------------------------------------------------

    public static class Builder extends AbstractSpecificationExtensionsBuilder<Builder, Aa20Operation> {

        private String operationId;
        String summary;
        String description;
        List<Aa20Tag> tags;
        Aa20ExternalDocumentation externalDocs;
        Aa20OperationBindings bindings;
        List<Aa20OperationTrait> traits;
        private Aa20Message message;

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

        public Builder addTags(Consumer<Aa20TagImpl.Builder> tag) {
            if(this.tags == null) {
                this.tags = new LinkedList<>();
            }
            Aa20TagImpl.Builder builder = Aa20TagImpl.newBuilder();
            tag.accept(builder);
            this.tags.add(builder.done());
            return this;
        }

        public Builder addExternalDocs(Consumer<Aa20ExternalDocumentationImpl.Builder> externalDocs) {
            Aa20ExternalDocumentationImpl.Builder builder = Aa20ExternalDocumentationImpl.newBuilder();
            externalDocs.accept(builder);
            this.externalDocs = builder.done();
            return this;
        }

        public Builder addBindings(Consumer<Aa20OperationBindingsImpl.Builder> bindings) {
            Aa20OperationBindingsImpl.Builder builder = Aa20OperationBindingsImpl.newBuilder();
            bindings.accept(builder);
            this.bindings = builder.done();
            return this;
        }

        public Builder addTrait(Consumer<Aa20OperationTraitImpl.Builder> tag) {
            if(this.traits == null) {
                this.traits = new LinkedList<>();
            }
            Aa20OperationTraitImpl.Builder builder = Aa20OperationTraitImpl.newBuilder();
            tag.accept(builder);
            this.traits.add(builder.done());
            return this;
        }

        public Builder addMessage(Consumer<Aa20MessageImpl.Builder> message) {
            Aa20MessageImpl.Builder builder = Aa20MessageImpl.newBuilder();
            message.accept(builder);
            this.message = builder.done();
            return this;
        }

        @Override
        public Aa20Operation done() {
            return new Aa20OperationImpl(this);
        }
    }
}
