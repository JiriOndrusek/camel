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

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
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

    public static Aa20ObjectImpl.Builder newBuilder() {
        return new Aa20ObjectImpl.Builder();
    }

    private Aa20ObjectImpl() {
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
    public Aa20Components getComponents() {
        return null;
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



    // --------------------------------------- builder ---------------------------------------------------------
    public static class Builder {

        private String asyncapi;
        private String id;
        private Aa20Info aa20Info;
        private Map<String, Aa20Server> servers;
        private Map<String, Aa20ChannelItem> channels;
        private Map<String, Aa20ChannelItemImpl.Builder> channelBuilders = new LinkedHashMap<>();

        private Builder() {
        }

        public Builder withAa20InfoImpl(Aa20InfoImpl aa20Info) {
            this.aa20Info = aa20Info;
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

        public void withAa20Info(Aa20Info aa20Info) {
            this.aa20Info = aa20Info;
        }

        public Aa20InfoImpl.Builder addInfo() {
            return  Aa20InfoImpl.newBuilder(this, o -> withAa20Info(o));
        }

        public void withAa20Server(String name, Aa20Server server) {
            if(this.servers == null) {
                this.servers = new LinkedHashMap<>();
            }
            this.servers.put(name, server);
        }

        public Aa20ServerImpl.Builder addServer(String name) {
            Aa20ServerImpl.Builder builder = Aa20ServerImpl.newBuilder(this, o -> withAa20Server(name, o));
            return builder;
        }

        public void withAa20Channel(String name, Aa20ChannelItem aa20ChannelItem) {
            if(this.channels == null) {
                this.channels = new LinkedHashMap<>();
            }
            this.channels.put(name, aa20ChannelItem);
        }

        public Aa20ChannelItemImpl.Builder addChannel(String name) {
            Aa20ChannelItemImpl.Builder builder = Aa20ChannelItemImpl.newBuilder(this, o -> withAa20Channel(name, o));
            channelBuilders.put(name, builder);
            return builder;
        }

        public Aa20Object build() {
            Aa20ObjectImpl object = new Aa20ObjectImpl();
            object.setAsyncapi(this.asyncapi);
            object.setId(this.id);
            object.setInfo(this.aa20Info);
            object.setServers(this.servers);
            object.setChannels(this.channels);
            return object;
        }
    }
}
