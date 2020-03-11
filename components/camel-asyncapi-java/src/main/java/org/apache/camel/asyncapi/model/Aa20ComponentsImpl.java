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
import java.util.Map;
import java.util.function.Consumer;

public class Aa20ComponentsImpl implements Aa20Components {

    private Map<String, Aa20Message> messages;

    private Map<String, Aa20Schema> schemas;

    private Map<String, Aa20SecuritySchema> securitySchemes;

    private Map<String, Aa20Parameter> parameters;

    private Map<String, Aa20CorellationId> correlationIds;

    private Map<String, Aa20OperationTrait> operationTraits;

    private Map<String, Aa20MessageTrait> messageTraits;

    private Map<String, Aa20ServerBindings> serverBindings;

    private Map<String, Aa20ChannelBindings> channelBindings;

    private Map<String, Aa20OperationBindings> operationBindings;

    private Map<String, Aa20MessageBinding> messageBindings;

    public static Builder newBuilder() {
        return new Builder();
    }

    private Aa20ComponentsImpl() {
    }

    private Aa20ComponentsImpl(Builder builder) {
        this.messages = builder.messages;
    }

    @Override
    public Map<String, Aa20Message> getMessages() {
        return messages;
    }

    public void setMessages(Map<String, Aa20Message> messages) {
        this.messages = messages;
    }

    @Override
    public Map<String, Aa20Schema> getSchemas() {
        return schemas;
    }

    public void setSchemas(Map<String, Aa20Schema> schemas) {
        this.schemas = schemas;
    }

    @Override
    public Map<String, Aa20SecuritySchema> getSecuritySchemes() {
        return securitySchemes;
    }

    public void setSecuritySchemes(Map<String, Aa20SecuritySchema> securitySchemes) {
        this.securitySchemes = securitySchemes;
    }

    @Override
    public Map<String, Aa20Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Aa20Parameter> parameters) {
        this.parameters = parameters;
    }

    @Override
    public Map<String, Aa20CorellationId> getCorrelationIds() {
        return correlationIds;
    }

    public void setCorrelationIds(Map<String, Aa20CorellationId> correlationIds) {
        this.correlationIds = correlationIds;
    }

    @Override
    public Map<String, Aa20OperationTrait> getOperationTraits() {
        return operationTraits;
    }

    public void setOperationTraits(Map<String, Aa20OperationTrait> operationTraits) {
        this.operationTraits = operationTraits;
    }

    @Override
    public Map<String, Aa20MessageTrait> getMessageTraits() {
        return messageTraits;
    }

    public void setMessageTraits(Map<String, Aa20MessageTrait> messageTraits) {
        this.messageTraits = messageTraits;
    }

    @Override
    public Map<String, Aa20ServerBindings> getServerBindings() {
        return serverBindings;
    }

    public void setServerBindings(Map<String, Aa20ServerBindings> serverBindings) {
        this.serverBindings = serverBindings;
    }

    @Override
    public Map<String, Aa20ChannelBindings> getChannelBindings() {
        return channelBindings;
    }

    public void setChannelBindings(Map<String, Aa20ChannelBindings> channelBindings) {
        this.channelBindings = channelBindings;
    }

    @Override
    public Map<String, Aa20OperationBindings> getOperationBindings() {
        return operationBindings;
    }

    public void setOperationBindings(Map<String, Aa20OperationBindings> operationBindings) {
        this.operationBindings = operationBindings;
    }

    @Override
    public Map<String, Aa20MessageBinding> getMessageBindings() {
        return messageBindings;
    }

    public void setMessageBindings(Map<String, Aa20MessageBinding> messageBindings) {
        this.messageBindings = messageBindings;
    }

// --------------------------------------- builder ---------------------------------------------------------

    public static class Builder extends AbstractBuilder<Aa20Components> {

        private Map<String, Aa20Message> messages;

        private Builder() {
        }

        public Builder withMessage(String name, Aa20Message message) {
            if(this.messages == null) {
                this.messages = new LinkedHashMap<>();
            }
            this.messages.put(name, message);
            return this;
        }

        @Override
        public Aa20Components done() {
            return new Aa20ComponentsImpl(this);
        }
    }
}
