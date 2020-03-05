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
 * This is the root document object for the API specification. It combines
 * resource listing and API declaration together into one document.
 */
public class Aa20Object {

    //todo AsyncAPI Version String
    String asyncapi;
    //todo Identifier
    String id;
    Aa20Info info;
    //todo ^[A-Za-z0-9_\-]+$
    Map<String, Aa20Server> servers = new LinkedHashMap();
    Map<String, Aa20ChannelItem> channels = new LinkedHashMap();
    //todo components
    List<Aa20Tag> tags = new LinkedList<>();
    Aa20ExternalDocumentation externalDocs;

    public Aa20Object(String asyncapi) {
        this.asyncapi = asyncapi;
    }

    public String getAsyncapi() {
        return asyncapi;
    }

    /**
     * Required. Specifies the AsyncAPI Specification version being used. It can
     * be used by tooling Specifications and clients to interpret the version.
     * The structure shall be major.minor.patch, where patch versions must be
     * compatible with the existing major.minor tooling. Typically patch versions
     * will be introduced to address errors in the documentation, and tooling should
     * typically be compatible with the corresponding major.minor (1.0.*).
     * Patch versions will correspond to patches of this document.
     */
    public Aa20Object setAsyncapi(String asyncapi) {
        this.asyncapi = asyncapi;
        return this;
    }

    public String getId() {
        return id;
    }

    /**
     * Identifier of the application the AsyncAPI document is defining.
     */
    public Aa20Object setId(String id) {
        this.id = id;
        return this;
    }

    public Aa20Info getInfo() {
        return info;
    }

    /**
     * Required. Provides metadata about the API. The metadata can be used by the clients if needed.
     */
    public Aa20Info createInfo(String title, String version) {
        this.info = new Aa20Info(title, version);
        return this.info;
    }

    public Map<String, Aa20Server> getServers() {
        return servers;
    }

    /**
     * Provides connection details of servers.
     */
    public Aa20Object setServers(Map<String, Aa20Server> servers) {
        this.servers = servers;
        return this;
    }

    public Aa20Server createServer(String name, String url, String protocol) {
        Aa20Server server = new Aa20Server(url, protocol);
        this.servers.put(name, server);
        return server;
    }

    public Map<String, Aa20ChannelItem> getChannels() {
        return channels;
    }

    /**
     * A relative path to an individual channel. The field name MUST be in the form of a RFC 6570 URI template.
     * Query parameters and fragments SHALL NOT be used, instead use bindings to define them.
     */
    public Aa20Object setChannels(Map<String, Aa20ChannelItem> channels) {
        this.channels = channels;
        return this;
    }

    public Aa20ChannelItem createChannels(String name) {
        Aa20ChannelItem channelItem = new Aa20ChannelItem();
        this.channels.put(name, channelItem);
        return channelItem;
    }

    /**
     * A list of tags for API documentation control. Tags can be used for logical grouping of messages.
     */
    public Aa20Object setTags(List<Aa20Tag> tags) {
        this.tags = tags;
        return this;
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
    public Aa20Object setExternalDocs(Aa20ExternalDocumentation externalDocs) {
        this.externalDocs = externalDocs;
        return this;
    }

    public Aa20ExternalDocumentation createExternalDocs(String url) {
        Aa20ExternalDocumentation externalDocs = new Aa20ExternalDocumentation(url);
        this.externalDocs = externalDocs;
        return externalDocs;
    }
}
