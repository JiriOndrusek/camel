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

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Aa20ChannelItemImpl extends AbstractAa20SpecificationExtensionImpl implements Aa20ChannelItem {

    private String $ref;
    private String description;
    private Aa20Operation subscribe;
    private Aa20Operation publish;
    private Map<String, Aa20Parameter> parameters;
    private Aa20ChannelBindings bindings;

    public static Builder newBuilder() {
        return new Builder();
    }

    private Aa20ChannelItemImpl() {
        super(null);
    }

    private Aa20ChannelItemImpl(Builder b) {
        super(b);
        this.description = b.description;
        this.$ref = b.$ref;
        this.subscribe = b.subscribe;
        this.publish = b.publish;
        this.parameters = b.parameters;
        this.bindings = b.bindings;
    }

    @Override
    public String get$ref() {
        return $ref;
    }

    public void set$ref(String $ref) {
        this.$ref = $ref;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Aa20Operation getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Aa20Operation subscribe) {
        this.subscribe = subscribe;
    }

    @Override
    public Aa20Operation getPublish() {
        return publish;
    }

    public void setPublish(Aa20Operation publish) {
        this.publish = publish;
    }

    @Override
    public Map<String, Aa20Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Aa20Parameter> parameters) {
        this.parameters = parameters;
    }

    @Override
    public Aa20ChannelBindings getBindings() {
        return bindings;
    }

    public void setBindings(Aa20ChannelBindings bindings) {
        this.bindings = bindings;
    }

    // --------------------------------------- builder ---------------------------------------------------------

    public static class Builder extends AbstractSpecificationExtensionsBuilder<Builder, Aa20ChannelItem> {

        private String $ref;
        private String description;
        private Aa20Operation subscribe;
        private Aa20Operation publish;
        private Map<String, Aa20Parameter> parameters;
        private Aa20ChannelBindings bindings;

        private Builder() {

        }

        public Builder with$ref(String $ref) {
            this.$ref = $ref;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder addSubscribe(Consumer<Aa20OperationImpl.Builder> subscribe) {
            Aa20OperationImpl.Builder builder = Aa20OperationImpl.newBuilder();
            subscribe.accept(builder);
            this.subscribe = builder.done();
            return this;
        }

        public Builder addPublish(Consumer<Aa20OperationImpl.Builder> publish) {
            Aa20OperationImpl.Builder builder = Aa20OperationImpl.newBuilder();
            publish.accept(builder);
            this.publish = builder.done();
            return this;
        }

        public Builder addParameter(String name, Consumer<Aa20ParameterImpl.Builder> parameter) {
            if(this.parameters == null) {
                this.parameters = new LinkedHashMap<>();
            }
            Aa20ParameterImpl.Builder builder = Aa20ParameterImpl.newBuilder();
            parameter.accept(builder);
            this.parameters.put(name, builder.done());
            return this;
        }

        public Builder addBindings(Consumer<Aa20ChannelBindingsImpl.Builder> bindings) {
            Aa20ChannelBindingsImpl.Builder builder = Aa20ChannelBindingsImpl.newBuilder();
            bindings.accept(builder);
            this.bindings = builder.done();
            return this;
        }

        @Override
        public Aa20ChannelItem done() {
            return new Aa20ChannelItemImpl(this);
        }
    }
}
