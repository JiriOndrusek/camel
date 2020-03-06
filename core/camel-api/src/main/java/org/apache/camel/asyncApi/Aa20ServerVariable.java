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
package org.apache.camel.asyncApi;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * An object representing a Server Variable for server URL template substitution.
 * todo This object MAY be extended with Specification Extensions.
 */
public class Aa20ServerVariable {

    private Set<String> enums = new LinkedHashSet();
    String defaultValue;
    String description;
    List<String> examples = new LinkedList<>();

    public Set<String> getEnums() {
        return enums;
    }

    public void setEnums(Set<String> enums) {
        this.enums = enums;
    }

    public Aa20ServerVariable createEnum(String enumName) {
        this.enums.add(enumName);
        return this;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * The default value to use for substitution, and to send, if an alternate value is not supplied.
     */
    public Aa20ServerVariable setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    public String getDescription() {
        return description;
    }

    /**
     * An optional description for the server variable. CommonMark syntax MAY be used for rich text representation.
     */
    public Aa20ServerVariable setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<String> getExamples() {
        return examples;
    }

    /**
     * An array of examples of the server variable.
     */
    public Aa20ServerVariable setExamples(List<String> examples) {
        this.examples = examples;
        return this;
    }

    public Aa20ServerVariable addExample(String example) {
        examples.add(example);
        return this;
    }
}
