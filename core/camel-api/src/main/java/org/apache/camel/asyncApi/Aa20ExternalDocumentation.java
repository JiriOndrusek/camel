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
 * Allows referencing an external resource for extended documentation.
 */
public class Aa20ExternalDocumentation {


    String description;
    final String url;

    /**
     * @param url Required. The URL for the target documentation. Value MUST be in the format of a URL.
     */
    public Aa20ExternalDocumentation(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    /**
     * @param description A short description of the target documentation. CommonMark syntax can be used for rich text representation.
     */
    public Aa20ExternalDocumentation setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getUrl() {
        return url;
    }
}
