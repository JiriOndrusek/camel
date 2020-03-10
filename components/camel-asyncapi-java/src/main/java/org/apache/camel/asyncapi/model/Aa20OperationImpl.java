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

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Aa20OperationImpl implements Aa20Operation {



    private String operationId;
    private String summary;
    private String description;
    private List<Aa20Tag> tags = new LinkedList<>();
    private Aa20ExternalDocumentation externalDocs;
    private Aa20MessageBinding bindings = new Aa20MessageBinding();
    //ref
    private List<Aa20OperationTrait> traits;
    //todo or oneOf
    private Aa20Message message;

    public static Aa20OperationImpl.Builder newBuilder() {
        return new Aa20OperationImpl.Builder();
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

    @Override
    public Aa20MessageBinding getBindings() {
        return bindings;
    }

    public void setBindings(Aa20MessageBinding bindings) {
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

    public static class Builder extends NestedBuilder<Aa20ChannelItemImpl.Builder, Aa20Operation> {

        private String operationId;
        String summary;
        String description;
        List<Aa20Tag> tags = new LinkedList<>();
        Aa20ExternalDocumentation externalDocs;
        Aa20MessageBinding bindings = new Aa20MessageBinding();
        //ref
        List<Aa20OperationTrait> traits;
        //todo or oneOf
        private Aa20Message message;

        private Builder() {
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

//        public Builder setTags(List<Aa20Tag> tags) {
//            this.tags = tags;
//        }
//
//        public void setExternalDocs(Aa20ExternalDocumentation externalDocs) {
//            this.externalDocs = externalDocs;
//        }
//
//        public void setBindings(Aa20MessageBinding bindings) {
//            this.bindings = bindings;
//        }
//
//        public void setTraits(List<Aa20OperationTrait> traits) {
//            this.traits = traits;
//        }

        public Builder withAa20MessageImpl(Aa20MessageImpl message) {
            this.message = message;
            return this;
        }

        public Aa20MessageImpl.Builder addMessage() {
            return Aa20MessageImpl.newBuilder().withParentBuilder(this);
        }

        @Override
        public Aa20Operation build() {
            Aa20OperationImpl operation = new Aa20OperationImpl();
            operation.setOperationId(this.operationId);
            operation.setSummary(this.summary);
            operation.setDescription(this.description);
            operation.setMessage(this.message);
            return operation;
        }
    }
}
