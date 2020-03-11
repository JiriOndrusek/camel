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

import org.apache.camel.asyncApi.Aa20ServerVariable;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class Aa20ServerVariableImpl implements Aa20ServerVariable {

    private List<String> enums;
    private String defaultValue;
    private String description;
    private List<String> examples;

    public static Builder newBuilder() {
        return new Builder();
    }

    private Aa20ServerVariableImpl() {
    }

    public Aa20ServerVariableImpl(Builder b) {
        this.defaultValue = b.defaultValue;
        this.description = b.description;
        this.enums = b.enums;
        this.examples = b.examples;
    }

    public List<String> getEnum() {
        return enums;
    }

    public void setEnum(List<String> enums) {
        this.enums = enums;
    }

    public String getDefault() {
        return defaultValue;
    }

    public void setDefault(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public List<String> getExamples() {
        return examples;
    }

    public void setExamples(List<String> examples) {
        this.examples = examples;
    }

// --------------------------------------- builder ---------------------------------------------------------

    public static class Builder extends AbstractBuilder<Aa20ServerVariable> {

        private List<String> enums = new LinkedList<>();
        private String defaultValue;
        private String description;
        private List<String> examples = new LinkedList<>();

        public Builder() {
        }

        public Builder withEnum(String name) {
            this.enums.add(name);
            return this;
        }

        public Builder withDefault(String defaultValue) {
            this.defaultValue = defaultValue;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withExample(String example) {
            this.examples.add(example);
            return this;
        }

        @Override
        public Aa20ServerVariable done() {
            return new Aa20ServerVariableImpl(this);
        }
    }
}
