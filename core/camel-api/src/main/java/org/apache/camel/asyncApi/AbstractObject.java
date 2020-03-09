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
 */package org.apache.camel.asyncApi;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractObject<T extends AbstractObject> {

    String summary;
    //todo CommonMark syntax can be used for rich text representation.
    String description;
    List<Aa20Tag> tags = new LinkedList<>();
    Aa20ExternalDocumentation externalDocs;
    Aa20MessageBinding bindings = new Aa20MessageBinding();

    public String getSummary() {
        return summary;
    }

    /**
     * A short summary of what the operation is about.
     */
    public T setSummary(String summary) {
        this.summary = summary;
        return (T)this;
    }

    public String getDescription() {
        return description;
    }

    /**
     * A verbose explanation of the operation. CommonMark syntax can be used for rich text representation.
     */
    public T setDescription(String description) {
        this.description = description;
        return (T)this;
    }

    public List<Aa20Tag> getTags() {
        return tags;
    }

    /**
     * A list of tags for API documentation control. Tags can be used for logical grouping of messages.
     */
    public T setTags(List<Aa20Tag> tags) {
        this.tags = tags;
        return (T)this;
    }

    public Aa20Tag createTag(String name) {
        Aa20Tag tag = new Aa20Tag(name);
        this.tags.add(tag);
        return tag;
    }

    public Aa20ExternalDocumentation getExternalDocs() {
        return externalDocs;
    }

    /**
     * @param externalDocs Additional external documentation for this tag.
     */
    public T setExternalDocs(Aa20ExternalDocumentation externalDocs) {
        this.externalDocs = externalDocs;
        return (T)this;
    }

    public Aa20ExternalDocumentation createExternalDocs(String url) {
        Aa20ExternalDocumentation externalDocs = new Aa20ExternalDocumentation(url);
        this.externalDocs = externalDocs;
        return externalDocs;
    }

    public Aa20MessageBinding getBindings() {
        return bindings;
    }

    public Aa20MessageBinding createBindings(Aa20MessageBinding.Field name) {
        Aa20MessageBinding bindings = new Aa20MessageBinding();
        this.bindings = bindings;
        //todo put into bindigs
        return bindings;
    }
}
