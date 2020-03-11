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

import org.apache.camel.asyncApi.Aa20MessageBinding;

import java.util.function.Consumer;

public class Aa20MessageBindingImpl implements Aa20MessageBinding {

    String $ref;

    public static Builder newBuilder(Aa20OperationImpl.OperationBuilder parent, Consumer<Aa20MessageBinding> consumer) {
        return new Builder(parent, consumer);
    }

    private Aa20MessageBindingImpl() {
    }

    @Override
    public String get$ref() {
        return $ref;
    }

    public void set$ref(String $ref) {
        this.$ref = $ref;
    }

    // --------------------------------------- builder ---------------------------------------------------------

    public static class Builder extends NestedBuilder<Aa20OperationImpl.OperationBuilder, Aa20MessageBinding> {
        String $ref;

        public Builder(Aa20OperationImpl.OperationBuilder parent, Consumer<Aa20MessageBinding> consumer) {
            super(parent, consumer);
        }

        public Builder with$ref(String $ref) {
            this.$ref = $ref;
            return this;
        }

        @Override
        public Aa20MessageBinding build() {
            Aa20MessageBindingImpl messageBinding = new Aa20MessageBindingImpl();
            messageBinding.set$ref(this.$ref);
            return messageBinding;
        }
    }
}
