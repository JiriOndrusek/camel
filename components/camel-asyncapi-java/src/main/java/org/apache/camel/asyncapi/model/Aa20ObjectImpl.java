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
import java.util.List;
import java.util.Map;

public class Aa20ObjectImpl implements Aa20Object {

    private String asyncapi;
    private String id;
    private Aa20Info info;
    private Map<String, Aa20Server> servers;
    private Map<String, Aa20ChannelItem> channels;
    private Aa20Components components;

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


    @Override
    public List<Aa20Tag> getTags() {
        return null;
    }

    @Override
    public Aa20ExternalDocumentation getExternalDocs() {
        return null;
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


    // --------------------------------------- builder ---------------------------------------------------------
    public static class Builder extends AbstractBuilder<Aa20Object> {

        String asyncapi;
        String id;
        Aa20Info info;
        Map<String, Aa20Server> servers;
        Map<String, Aa20ChannelItem> channels;
        Map<String, Aa20ChannelItemImpl.Builder> channelBuilders = new LinkedHashMap<>();
        Aa20Components components;

        private Builder() {
        }

        public Builder withInfo(Aa20Info info) {
            this.info = info;
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

        public Builder withServer(String name, Aa20Server server) {
            if(this.servers == null) {
                this.servers = new LinkedHashMap<>();
            }
            this.servers.put(name, server);
            return this;
        }


        public Builder withChannel(String name, Aa20ChannelItem aa20ChannelItem) {
            if(this.channels == null) {
                this.channels = new LinkedHashMap<>();
            }
            this.channels.put(name, aa20ChannelItem);
            return this;
        }

        public Builder withComponents(Aa20Components components) {
            this.components = components;
            return this;
        }

        @Override
        public Aa20Object done() {
            return new Aa20ObjectImpl(this);
        }
    }
}
