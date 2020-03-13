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

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * This is the root document object for the API specification. It combines
 * resource listing and API declaration together into one document.
 */
public class Aa20ObjectImpl implements Aa20Object {

    private String asyncapi;
    private String id;
    private Aa20Info info;
    private Map<String, Aa20Server> servers;
    private Map<String, Aa20ChannelItem> channels;
    private Aa20Components components;
    private List<Aa20Tag> tags;
    private Aa20ExternalDocumentation externalDocs;

    public static Aa20ObjectImpl.Builder newBuilder() {
        return new Aa20ObjectImpl.Builder();
    }

    private Aa20ObjectImpl() {
    }

    private Aa20ObjectImpl(Builder builder) {
        this.asyncapi = builder.asyncapi;
        this.id = builder.id;
        this.info = builder.info;
        this.servers = builder.servers;
        this.channels = builder.channels;
        this.components = builder.components;
        this.tags = builder.tags;
        this.externalDocs = builder.externalDocs;
    }

    public Aa20ObjectImpl(String asyncapi) {
        this.asyncapi = asyncapi;
    }

    public String getAsyncapi() {
        return asyncapi;
    }

    public void setAsyncapi(String asyncapi) {
        this.asyncapi = asyncapi;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Aa20Info getInfo() {
        return info;
    }

    @Override
    public Map<String, Aa20Server> getServers() {
        return servers;
    }

    public void setServers(Map<String, Aa20Server> servers) {
        this.servers = servers;
    }

    @Override
    public Map<String, Aa20ChannelItem> getChannels() {
        return channels;
    }

    public void setChannels(Map<String, Aa20ChannelItem> channels) {
        this.channels = channels;
    }

    public void setInfo(Aa20Info info) {
        this.info = info;
    }

    @Override
    public Aa20Components getComponents() {
        return components;
    }

    public void setComponents(Aa20Components components) {
        this.components = components;
    }

    @Override
    public List<Aa20Tag> getTags() {
        return tags;
    }

    public void setTags(List<Aa20Tag> tags) {
        this.tags = tags;
    }

    @Override
    public Aa20ExternalDocumentation getExternalDocs() {
        return externalDocs;
    }

    public void setExternalDocs(Aa20ExternalDocumentation externalDocs) {
        this.externalDocs = externalDocs;
    }

    // --------------------------------------- builder ---------------------------------------------------------
    public static class Builder extends AbstractBuilder<Aa20Object> {

        private String asyncapi;
        private String id;
        private Aa20Info info;
        private Map<String, Aa20Server> servers;
        private Map<String, Aa20ChannelItem> channels;
        private Aa20Components components;
        private List<Aa20Tag> tags;
        private Aa20ExternalDocumentation externalDocs;

        private Builder() {
        }

        public Builder addInfo(Consumer<Aa20InfoImpl.Builder> infoBuilder) {
            Aa20InfoImpl.Builder builder = Aa20InfoImpl.newBuilder();
            infoBuilder.accept(builder);
            this.info = builder.done();
            return this;
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withAsyncApi(String asyncApi) {
            this.asyncapi = asyncApi;
            return this;
        }

        public Builder addhServer(String name, Consumer<Aa20ServerImpl.Builder> server) {
            if(this.servers == null) {
                this.servers = new LinkedHashMap<>();
            }
            Aa20ServerImpl.Builder builder = Aa20ServerImpl.newBuilder();
            server.accept(builder);
            this.servers.put(name, builder.done());
            return this;
        }


        public Builder addChannel(String name, Consumer<Aa20ChannelItemImpl.Builder> channel) {
            if(this.channels == null) {
                this.channels = new LinkedHashMap<>();
            }
            Aa20ChannelItemImpl.Builder builder = Aa20ChannelItemImpl.newBuilder();
            channel.accept(builder);
            this.channels.put(name, builder.done());
            return this;
        }

        public Builder addComponents(Consumer<Aa20ComponentsImpl.Builder> components) {
            Aa20ComponentsImpl.Builder builder = Aa20ComponentsImpl.newBuilder();
            components.accept(builder);
            this.components = builder.done();
            return this;
        }

        public Builder addTag(Consumer<Aa20TagImpl.Builder> tag) {
            if(tags == null) {
                tags = new LinkedList<>();
            }
            Aa20TagImpl.Builder builder = Aa20TagImpl.newBuilder();
            tag.accept(builder);
            this.tags.add(builder.done());
            return this;
        }

        public Builder addExternalDocs(Consumer<Aa20ExternalDocumentationImpl.Builder> externalDocs) {
            Aa20ExternalDocumentationImpl.Builder builder = Aa20ExternalDocumentationImpl.newBuilder();
            externalDocs.accept(builder);
            this.externalDocs = builder.done();
            return this;
        }

        @Override
        public Aa20Object done() {
            return new Aa20ObjectImpl(this);
        }
    }
}
