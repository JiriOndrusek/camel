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

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Describes a message received on a given channel and operation.
 */
public abstract class AbstractMessage<T extends AbstractMessage> extends AbstractObject<T> {

    //todo headers	Schema Object | Reference Object
    Map<String, Object> payload = new LinkedHashMap();
    Aa20OrReferenceType<Aa20CorellationId> correlationId;
    String schemaFormat;
    String contentType;
    String name;
    String title;
    List<String> examples= new LinkedList<>();
    List<Aa20MessageTrait> traits = new LinkedList();

    public Object getPayload() {
        return payload;
    }

    public T addPayload(String name, Object payload) {
        this.payload.put(name, payload);
        return (T)this;
    }

    public Aa20OrReferenceType<Aa20CorellationId> getCorrelationId() {
        return correlationId;
    }

    public Aa20CorellationId createCorrelationId(String location) {
        Aa20CorellationId corellationId = new Aa20CorellationId(location);
        this.correlationId = corellationId;
        return corellationId;
    }

    public Aa20Reference createReference(String $ref) {
        Aa20Reference reference = new Aa20Reference($ref);
        this.correlationId = reference;
        return reference;
    }

    public String getSchemaFormat() {
        return schemaFormat;
    }

    public T setSchemaFormat(String schemaFormat) {
        this.schemaFormat = schemaFormat;
        return (T)this;
    }

    public String getContentType() {
        return contentType;
    }

    public T setContentType(String contentType) {
        this.contentType = contentType;
        return (T)this;
    }

    public String getName() {
        return name;
    }

    public T setName(String name) {
        this.name = name;
        return (T)this;
    }

    public String getTitle() {
        return title;
    }

    public T setTitle(String title) {
        this.title = title;
        return (T)this;
    }

    public List<String> getExamples() {
        return examples;
    }

    public T createExample(String example) {
        this.examples.add(example);
        return (T)this;
    }

    public List<Aa20MessageTrait> getTraits() {
        return traits;
    }

    public Aa20MessageTrait createTrait() {
        Aa20MessageTrait trait = new Aa20MessageTrait();
        this.traits.add(trait);
        return trait;
    }
}
