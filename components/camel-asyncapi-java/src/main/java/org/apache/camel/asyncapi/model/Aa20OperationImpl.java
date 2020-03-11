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

public class Aa20OperationImpl implements Aa20Operation {



    private String operationId;
    private String summary;
    private String description;
    private List<Aa20Tag> tags = new LinkedList<>();
    private Aa20ExternalDocumentation externalDocs;
    private Aa20MessageBinding binding;
    //ref
    private List<Aa20OperationTrait> traits;
    //todo or oneOf
    private Aa20Message message;

    public static OperationBuilder newBuilder(Aa20ChannelItemImpl.Builder parent, Consumer<Aa20Operation> consumer) {
        return new OperationBuilder(parent, consumer);
    }

    private Aa20OperationImpl() {
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

    public Aa20MessageBinding getBinding() {
        return binding;
    }

    public void setBinding(Aa20MessageBinding binding) {
        this.binding = binding;
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

    public static class OperationBuilder extends NestedBuilder<Aa20ChannelItemImpl.Builder, Aa20Operation> {

        private String operationId;
        String summary;
        String description;
        List<Aa20Tag> tags = new LinkedList<>();
        Aa20ExternalDocumentation externalDocs;
        Aa20MessageBinding binding;
        List<Aa20OperationTrait> traits;
        private Aa20Message message;

        private OperationBuilder(Aa20ChannelItemImpl.Builder parent, Consumer<Aa20Operation> consumer) {
            super(parent, consumer);
        }

        public OperationBuilder withOperationId(String operationId) {
            this.operationId = operationId;
            return this;
        }

        public OperationBuilder withSummary(String summary) {
            this.summary = summary;
            return this;
        }

        public OperationBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

//        public Builder setTags(List<Aa20Tag> tags) {
//            this.tags = tags;
//        }
//
//        public void setExternalDocs(Aa20ExternalDocumentation externalDocs) {
//            this.externalDocs = externalDocs;
//        }
//


        public OperationBuilder withBinding(Aa20MessageBinding binding) {
            this.binding = binding;
            return this;
        }

        public Aa20MessageBindingImpl.Builder addBindings() {
            return Aa20MessageBindingImpl.newBuilder(this, o -> withBinding(o));
        }
//
//        public void setTraits(List<Aa20OperationTrait> traits) {
//            this.traits = traits;
//        }

        public OperationBuilder withAa20MessageImpl(Aa20Message message) {
            this.message = message;
            return this;
        }

        public Aa20MessageImpl.FromOperationBuilder addMessage() {
            return Aa20MessageImpl.newFromOperationBuilder(this, o -> withAa20MessageImpl(o));
        }

        @Override
        public Aa20Operation build() {
            Aa20OperationImpl operation = new Aa20OperationImpl();
            operation.setOperationId(this.operationId);
            operation.setSummary(this.summary);
            operation.setDescription(this.description);
            operation.setMessage(this.message);
            operation.setBinding(this.binding);
            return operation;
        }
    }
}
