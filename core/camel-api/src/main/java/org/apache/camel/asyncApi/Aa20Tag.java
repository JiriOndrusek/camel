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

public class Aa20Tag {

    final String name;
    //TODO CommonMark syntax can be used for rich text representation.
    String description;
    Aa20ExternalDocumentation externalDocs;

    /**
     * @param name Required. The name of the tag.
     */
    public Aa20Tag(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    /**
     * A short description for the tag. CommonMark syntax can be used for rich text representation.
     */
    public Aa20Tag setDescription(String description) {
        this.description = description;
        return this;
    }

    public Aa20ExternalDocumentation getExternalDocs() {
        return externalDocs;
    }

    /**
     * @param externalDocs Additional external documentation for this tag.
     */
    public Aa20Tag setExternalDocs(Aa20ExternalDocumentation externalDocs) {
        this.externalDocs = externalDocs;
        return this;
    }

    public Aa20ExternalDocumentation createExternalDocs(String url) {
        Aa20ExternalDocumentation externalDocs = new Aa20ExternalDocumentation(url);
        this.externalDocs = externalDocs;
        return externalDocs;
    }


}
