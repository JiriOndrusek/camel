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

    public static Builder newBuilder() {
        return new Builder();
    }

    private Aa20MessageBindingImpl() {
    }

    private Aa20MessageBindingImpl(Builder b) {
        this.$ref = b.$ref;
    }

    @Override
    public String get$ref() {
        return $ref;
    }

    public void set$ref(String $ref) {
        this.$ref = $ref;
    }

    // --------------------------------------- builder ---------------------------------------------------------

    public static class Builder extends AbstractBuilder<Aa20MessageBinding> {
        String $ref;

        public Builder() {
        }

        public Builder with$ref(String $ref) {
            this.$ref = $ref;
            return this;
        }

        @Override
        public Aa20MessageBinding done() {
            return new Aa20MessageBindingImpl(this);
        }
    }
}
