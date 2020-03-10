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

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.camel.asyncApi.Aa20SchemaType;
import org.apache.camel.asyncApi.Aa20SecurityRequirement;
import org.apache.camel.asyncApi.Aa20ServerVariable;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Aa20ServerVariableImpl implements Aa20ServerVariable {

    private List<String> enums = new LinkedList<>();
    private String defaultValue;
    private String description;
    private List<String> examples = new LinkedList<>();

//    public static Aa20ServerVariableImpl.Builder newBuilder() {
//        return new Aa20ServerVariableImpl.Builder();
//    }

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

//    public static class Builder extends NestedBuilder<Aa20ServerImpl.Builder, Aa20ServerVariable> {
//
//        private List<String> enums = new LinkedList<>();
//        private String defaultValue;
//        private String description;
//        private List<String> examples = new LinkedList<>();
//
//        public Builder withEnum(String name) {
//            this.enums.add(name);
//            return this;
//        }
//
//        public Builder withDefault(String defaultValue) {
//            this.defaultValue = defaultValue;
//            return this;
//        }
//
//        public Builder withDescription(String description) {
//            this.description = description;
//            return this;
//        }
//
//        public Builder withExample(String example) {
//            this.examples.add(example);
//            return this;
//        }
//
//
//        @Override
//        public Aa20ServerVariable build() {
//            Aa20ServerVariableImpl variable = new Aa20ServerVariableImpl();
//            variable.setDefault(this.defaultValue);
//            variable.setDescription(this.description);
//            variable.setEnum(this.enums);
//            variable.setExamples(this.examples);
//            return variable;
//        }
//    }
}
