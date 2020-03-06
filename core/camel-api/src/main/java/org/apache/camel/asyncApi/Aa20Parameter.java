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

public class Aa20Parameter implements Aa20OrReferenceType<Aa20Parameter> {

    //todo CommonMark syntax can be used for rich text representation.
    String description;
    //todo schema
    String location;

    public String getDescription() {
        return description;
    }

    /**
     * @param description A verbose explanation of the parameter. CommonMark syntax can be used for rich text representation.
     */
    public Aa20Parameter setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getLocation() {
        return location;
    }

    /**
     * @param location A runtime expression that specifies the location of the parameter value. Even when a definition for the target field exists, it MUST NOT be used to validate this parameter but, instead, the schema property MUST be used.
     */
    public Aa20Parameter setLocation(String location) {
        this.location = location;
        return this;
    }
}
