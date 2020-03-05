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

/**
 * An object that specifies an identifier at design time that can used for message tracing and correlation.
 *
 * For specifying and computing the location of a Correlation ID, a runtime expression is used.
 */
public class Aa20CorellationId extends AbstractCorrelationIdOrReference {

    String description;
    final String location;

    /**
     *
     * @param location REQUIRED. A runtime expression that specifies the location of the correlation ID.
     */
    public Aa20CorellationId(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    /**
     * @param description An optional description of the identifier. CommonMark syntax can be used for rich text representation.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }
}
