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

public class Aa20ChannelItemImpl implements Aa20ChannelItem {

    private String $ref;
    private String description;
    private Aa20Operation subscribe;
    private Aa20Operation publish;
    private Map<String, Aa20OrReferenceType<Aa20Parameter>> parameters = new LinkedHashMap<>();
    private Aa20ChannelBindings bindings = new Aa20ChannelBindings();

    public static Aa20ChannelItemImpl.Builder newBuilder(Aa20ObjectImpl.Builder parent, Consumer<Aa20ChannelItem> consumer) {
        return new Aa20ChannelItemImpl.Builder(parent, consumer);
    }

    private Aa20ChannelItemImpl() {
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
    public Map<String, Aa20OrReferenceType<Aa20Parameter>> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Aa20OrReferenceType<Aa20Parameter>> parameters) {
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

    public static class Builder<T> extends NestedBuilder<T,  Aa20ChannelItem> {

        private String $ref;
        private String description;
        private Aa20Operation subscribe;
        private Aa20Operation publish;
//        private Map<String, Aa20OrReferenceType<Aa20Parameter>> parameters = new LinkedHashMap<>();
//        private Aa20ChannelBindings bindings = new Aa20ChannelBindings();

        private Builder(T parent, Consumer<Aa20ChannelItem> consumer) {
            super(parent, consumer);
        }

        public Builder with$ref(String $ref) {
            this.$ref = $ref;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withSubscribe(Aa20Operation subscribe) {
            this.subscribe = subscribe;
            return this;
        }

        public Builder withPublish(Aa20Operation publish) {
            this.publish = publish;
            return this;
        }

        @Override
        public Aa20ChannelItem build() {
            Aa20ChannelItemImpl channel = new Aa20ChannelItemImpl();
            channel.set$ref(this.$ref);
            channel.setDescription(this.description);
            channel.setSubscribe(this.subscribe);
            channel.setPublish(this.publish);
            return channel;
        }

        public Aa20OperationImpl.OperationBuilder addSubscribe() {
            return Aa20OperationImpl.newBuilder(this, o -> withSubscribe(o));
        }

        public Aa20OperationImpl.OperationBuilder addPublish() {
            return Aa20OperationImpl.newBuilder(this, o -> withPublish(o));
        }
    }
}
