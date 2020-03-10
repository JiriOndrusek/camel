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

import org.apache.camel.asyncApi.*;

import java.util.*;
import java.util.function.Consumer;

public class Aa20MessageImpl implements Aa20Message {

    private Aa20OrReferenceType<Aa20Schema> headers;
    private Aa20OrReferenceType<Aa20CorellationId> correlationId;
    private String schemaFormat;
    private String contentType;
    private String name;
    private String title;
    private List<String> examples= new LinkedList<>();
    private Map<String, Object> payload = new LinkedHashMap();
    private List<Aa20OrReferenceType<Aa20MessageTrait>> traits = new LinkedList();
    private Set<Aa20Message> oneOf;
    private String $ref;

    public static Aa20MessageImpl.FromOperationBuilder newFromOperationBuilder(Aa20OperationImpl.OperationBuilder parent, Consumer<Aa20Message> consumer) {
        return new Aa20MessageImpl.FromOperationBuilder(parent, consumer);
    }

    public static Aa20MessageImpl.FromMessageBuilder newFromMessageBuilder(Aa20MessageImpl.FromOperationBuilder parent, Consumer<Aa20Message> consumer) {
        return new Aa20MessageImpl.FromMessageBuilder(parent, consumer);
    }

    private Aa20MessageImpl() {
    }

    @Override
    public Aa20OrReferenceType<Aa20Schema> getHeaders() {
        return headers;
    }

    public void setHeaders(Aa20OrReferenceType<Aa20Schema> headers) {
        this.headers = headers;
    }

    @Override
    public Aa20OrReferenceType<Aa20CorellationId> getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(Aa20OrReferenceType<Aa20CorellationId> correlationId) {
        this.correlationId = correlationId;
    }

    @Override
    public String getSchemaFormat() {
        return schemaFormat;
    }

    public void setSchemaFormat(String schemaFormat) {
        this.schemaFormat = schemaFormat;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public List<String> getExamples() {
        return examples;
    }

    public void setExamples(List<String> examples) {
        this.examples = examples;
    }

    @Override
    public Map<String, Object> getPayload() {
        return payload;
    }

    public void setPayload(Map<String, Object> payload) {
        this.payload = payload;
    }

    @Override
    public List<Aa20OrReferenceType<Aa20MessageTrait>> getTraits() {
        return traits;
    }

    public void setTraits(List<Aa20OrReferenceType<Aa20MessageTrait>> traits) {
        this.traits = traits;
    }

    @Override
    public Set<Aa20Message> getOneOf() {
        return oneOf;
    }

    public void setOneOf(Set<Aa20Message> oneOf) {
        this.oneOf = oneOf;
    }

    @Override
    public String get$ref() {
        return $ref;
    }

    public void set$ref(String $ref) {
        this.$ref = $ref;
    }

    // --------------------------------------- builder ---------------------------------------------------------

    public static class FromOperationBuilder extends NestedBuilder<Aa20OperationImpl.OperationBuilder, Aa20Message> {
        private FromOperationBuilder(Aa20OperationImpl.OperationBuilder parent, Consumer<Aa20Message> consumer) {
            super(parent, consumer);
        }

//        private Aa20OrReferenceType<Aa20Schema> headers;
//        private Aa20OrReferenceType<Aa20CorellationId> correlationId;
        private String schemaFormat;
        private String contentType;
        private String name;
        private String title;
        private List<String> examples= new LinkedList<>();
        private Map<String, Object> payload = new LinkedHashMap();
//        private List<Aa20OrReferenceType<Aa20MessageTrait>> traits = new LinkedList();
        private Set<Aa20Message> oneOf;
        private String $ref;


//        public NestedBuilder<T, Aa20Message> withSchemaFormat(String schemaFormat) {
//            this.schemaFormat = schemaFormat;
//            return this;
//        }
//
//        public NestedBuilder<T, Aa20Message> withContentType(String contentType) {
//            this.contentType = contentType;
//            return this;
//        }
//
//        public NestedBuilder<T, Aa20Message> withName(String name) {
//            this.name = name;
//            return this;
//        }
//
//        public NestedBuilder<T, Aa20Message> withTitle(String title) {
//            this.title = title;
//            return this;
//        }
//
//        public NestedBuilder<T, Aa20Message> withExamples(List<String> examples) {
//            this.examples = examples;
//            return this;
//        }
//
//        public NestedBuilder<T, Aa20Message> withPayload(Map<String, Object> payload) {
//            this.payload = payload;
//            return this;
//        }
//
        public FromOperationBuilder  withOneOf(Aa20Message message) {
            if(this.oneOf == null) {
                oneOf = new LinkedHashSet<>();
            }
            this.oneOf.add(message);
            return this;
        }

        public FromMessageBuilder addOneOfMessage() {
            return Aa20MessageImpl.newFromMessageBuilder(this, o -> withOneOf(o));
        }

        public FromOperationBuilder with$ref(String $ref) {
            this.$ref = $ref;
            return this;
        }

        @Override
        public Aa20Message build() {
            Aa20MessageImpl message = new Aa20MessageImpl();
            message.setSchemaFormat(this.title);
            message.setContentType(this.contentType);
            message.setName(this.name);
            message.setTitle(this.title);
            message.setExamples(this.examples);
            message.setPayload(this.payload);
            message.setOneOf(this.oneOf);
            message.set$ref(this.$ref);
            return message;
        }
    }

    public static class FromMessageBuilder extends NestedBuilder<Aa20MessageImpl.FromOperationBuilder, Aa20Message> {
        private FromMessageBuilder(Aa20MessageImpl.FromOperationBuilder parent, Consumer<Aa20Message> consumer) {
            super(parent, consumer);
        }

        //        private Aa20OrReferenceType<Aa20Schema> headers;
//        private Aa20OrReferenceType<Aa20CorellationId> correlationId;
        private String schemaFormat;
        private String contentType;
        private String name;
        private String title;
        private List<String> examples= new LinkedList<>();
        private Map<String, Object> payload = new LinkedHashMap();
        //        private List<Aa20OrReferenceType<Aa20MessageTrait>> traits = new LinkedList();
        private Set<Aa20Message> oneOf;
        private String $ref;


//        public NestedBuilder<T, Aa20Message> withSchemaFormat(String schemaFormat) {
//            this.schemaFormat = schemaFormat;
//            return this;
//        }
//
//        public NestedBuilder<T, Aa20Message> withContentType(String contentType) {
//            this.contentType = contentType;
//            return this;
//        }
//
//        public NestedBuilder<T, Aa20Message> withName(String name) {
//            this.name = name;
//            return this;
//        }
//
//        public NestedBuilder<T, Aa20Message> withTitle(String title) {
//            this.title = title;
//            return this;
//        }
//
//        public NestedBuilder<T, Aa20Message> withExamples(List<String> examples) {
//            this.examples = examples;
//            return this;
//        }
//
//        public NestedBuilder<T, Aa20Message> withPayload(Map<String, Object> payload) {
//            this.payload = payload;
//            return this;
//        }
//
//        public FromMessageBuilder  withOneOf(Aa20Message message) {
//            if(this.oneOf == null) {
//                oneOf = new LinkedHashSet<>();
//            }
//            this.oneOf.add(message);
//            return this;
//        }
//
//        public FromMessageBuilder addOneOfMessage() {
//            return Aa20MessageImpl.newFromMessageBuilder(this, o -> withOneOf(o));
//        }

        public FromMessageBuilder with$ref(String $ref) {
            this.$ref = $ref;
            return this;
        }

        @Override
        public Aa20Message build() {
            Aa20MessageImpl message = new Aa20MessageImpl();
            message.setSchemaFormat(this.title);
            message.setContentType(this.contentType);
            message.setName(this.name);
            message.setTitle(this.title);
            message.setExamples(this.examples);
            message.setPayload(this.payload);
            message.setOneOf(this.oneOf);
            message.set$ref(this.$ref);
            return message;
        }
    }
}
