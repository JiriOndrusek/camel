/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed add
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance add
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * addOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.asyncapi.model;

import org.apache.camel.asyncApi.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Aa20ComponentsImpl extends AbstractAa20SpecificationExtensionImpl implements Aa20Components {

    private Map<String, Aa20Message> messages;

    private Map<String, Aa20Schema> schemas;

    private Map<String, Aa20SecurityScheme> securitySchemes;

    private Map<String, Aa20Parameter> parameters;

    private Map<String, Aa20CorrelationId> correlationIds;

    private Map<String, Aa20OperationTrait> operationTraits;

    private Map<String, Aa20MessageTrait> messageTraits;

    private Map<String, Aa20ServerBindings> serverBindings;

    private Map<String, Aa20ChannelBindings> channelBindings;

    private Map<String, Aa20OperationBindings> operationBindings;

    private Map<String, Aa20MessageBindings> messageBindings;

    public static Builder newBuilder() {
        return new Builder();
    }

    private Aa20ComponentsImpl() {
        super(null);
    }

    private Aa20ComponentsImpl(Builder b) {
        super(b);
        this.messages = b.messages;
        this.schemas = b.schemas;
        this.securitySchemes = b.securitySchemes;
        this.parameters = b.parameters;
        this.correlationIds = b.correlationIds;
        this.operationTraits = b.operationTraits;
        this.messageTraits = b.messageTraits;
        this.serverBindings = b.serverBindings;
        this.channelBindings = b.channelBindings;
        this.operationBindings = b.operationBindings;
        this. messageBindings = b.messageBindings;
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
    public Map<String, Aa20SecurityScheme> getSecuritySchemes() {
        return securitySchemes;
    }

    public void setSecuritySchemes(Map<String, Aa20SecurityScheme> securitySchemes) {
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
    public Map<String, Aa20CorrelationId> getCorrelationIds() {
        return correlationIds;
    }

    public void setCorrelationIds(Map<String, Aa20CorrelationId> correlationIds) {
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
    public Map<String, Aa20MessageBindings> getMessageBindings() {
        return messageBindings;
    }

    public void setMessageBindings(Map<String, Aa20MessageBindings> messageBindings) {
        this.messageBindings = messageBindings;
    }

// --------------------------------------- builder ---------------------------------------------------------

    public static class Builder extends AbstractSpecificationExtensionsBuilder<Builder, Aa20Components> {

        private Map<String, Aa20Message> messages;

        private Map<String, Aa20Schema> schemas;

        private Map<String, Aa20SecurityScheme> securitySchemes;

        private Map<String, Aa20Parameter> parameters;

        private Map<String, Aa20CorrelationId> correlationIds;

        private Map<String, Aa20OperationTrait> operationTraits;

        private Map<String, Aa20MessageTrait> messageTraits;

        private Map<String, Aa20ServerBindings> serverBindings;

        private Map<String, Aa20ChannelBindings> channelBindings;

        private Map<String, Aa20OperationBindings> operationBindings;

        private Map<String, Aa20MessageBindings> messageBindings;

        private Builder() {
        }

        public Builder addMessage(String name, Consumer<Aa20MessageImpl.Builder> message) {
            if(this.messages == null) {
                this.messages = new LinkedHashMap<>();
            }
            Aa20MessageImpl.Builder builder = Aa20MessageImpl.newBuilder();
            message.accept(builder);
            this.messages.put(name, builder.done());
            return this;
        }

        public Builder addSchema(String name, Consumer<Aa20SchemaImpl.Builder> schema) {
            if(this.schemas == null) {
                this.schemas = new LinkedHashMap<>();
            }
            Aa20SchemaImpl.Builder builder = Aa20SchemaImpl.newBuilder();
            schema.accept(builder);
            this.schemas.put(name, builder.done());
            return this;
        }

        public Builder addSecuritySchemeUserPassword(String name, Consumer<Aa20SecuritySchemeImpl.CommonBuilder> securityScheme) {
            if(this.securitySchemes == null) {
                this.securitySchemes = new LinkedHashMap<>();
            }
            Aa20SecuritySchemeImpl.CommonBuilder builder = Aa20SecuritySchemeImpl.newUserPassword();
            securityScheme.accept(builder);
            this.securitySchemes.put(name, builder.done());
            return this;
        }

        public Builder addSecuritySchemeApiKey(String name, Consumer<Aa20SecuritySchemeImpl.ApiKeyBuilder> securityScheme) {
            if(this.securitySchemes == null) {
                this.securitySchemes = new LinkedHashMap<>();
            }
            Aa20SecuritySchemeImpl.ApiKeyBuilder builder = Aa20SecuritySchemeImpl.newApiKey();
            securityScheme.accept(builder);
            this.securitySchemes.put(name, builder.done());
            return this;
        }

        public Builder addSecuritySchemeX509(String name, Consumer<Aa20SecuritySchemeImpl.CommonBuilder> securityScheme) {
            if(this.securitySchemes == null) {
                this.securitySchemes = new LinkedHashMap<>();
            }
            Aa20SecuritySchemeImpl.CommonBuilder builder = Aa20SecuritySchemeImpl.newX509();
            securityScheme.accept(builder);
            this.securitySchemes.put(name, builder.done());
            return this;
        }

        public Builder addSecuritySchemeSymmetricEncryption(String name, Consumer<Aa20SecuritySchemeImpl.CommonBuilder> securityScheme) {
            if(this.securitySchemes == null) {
                this.securitySchemes = new LinkedHashMap<>();
            }
            Aa20SecuritySchemeImpl.CommonBuilder builder = Aa20SecuritySchemeImpl.newSymmetricEncryption();
            securityScheme.accept(builder);
            this.securitySchemes.put(name, builder.done());
            return this;
        }

        public Builder addSecuritySchemeAsymmetricEncryption(String name, Consumer<Aa20SecuritySchemeImpl.CommonBuilder> securityScheme) {
            if(this.securitySchemes == null) {
                this.securitySchemes = new LinkedHashMap<>();
            }
            Aa20SecuritySchemeImpl.CommonBuilder builder = Aa20SecuritySchemeImpl.newAsymmetricEncryption();
            securityScheme.accept(builder);
            this.securitySchemes.put(name, builder.done());
            return this;
        }

        public Builder addSecuritySchemeHttp(String name, Consumer<Aa20SecuritySchemeImpl.HttpBuilder> securityScheme) {
            if(this.securitySchemes == null) {
                this.securitySchemes = new LinkedHashMap<>();
            }
            Aa20SecuritySchemeImpl.HttpBuilder builder = Aa20SecuritySchemeImpl.newHttp();
            securityScheme.accept(builder);
            this.securitySchemes.put(name, builder.done());
            return this;
        }

        public Builder addSecuritySchemeHttpApiKey(String name, Consumer<Aa20SecuritySchemeImpl.HttpApiKeyBuilder> securityScheme) {
            if(this.securitySchemes == null) {
                this.securitySchemes = new LinkedHashMap<>();
            }
            Aa20SecuritySchemeImpl.HttpApiKeyBuilder builder = Aa20SecuritySchemeImpl.newHttpApiKey();
            securityScheme.accept(builder);
            this.securitySchemes.put(name, builder.done());
            return this;
        }

        public Builder addSecuritySchemeOAuth2(String name, Consumer<Aa20SecuritySchemeImpl.OAuth2Builder> securityScheme) {
            if(this.securitySchemes == null) {
                this.securitySchemes = new LinkedHashMap<>();
            }
            Aa20SecuritySchemeImpl.OAuth2Builder builder = Aa20SecuritySchemeImpl.newOAuth2();
            securityScheme.accept(builder);
            this.securitySchemes.put(name, builder.done());
            return this;
        }

        public Builder addSecuritySchemeOpenIdConnect(String name, Consumer<Aa20SecuritySchemeImpl.OpenIdConnectBuilder> securityScheme) {
            if(this.securitySchemes == null) {
                this.securitySchemes = new LinkedHashMap<>();
            }
            Aa20SecuritySchemeImpl.OpenIdConnectBuilder builder = Aa20SecuritySchemeImpl.newOpenIdConnect();
            securityScheme.accept(builder);
            this.securitySchemes.put(name, builder.done());
            return this;
        }

        public Builder addParameter(String name, Consumer<Aa20ParameterImpl.Builder> parameter) {
            if(this.parameters == null) {
                this.parameters = new LinkedHashMap<>();
            }
            Aa20ParameterImpl.Builder builder = Aa20ParameterImpl.newBuilder();
            parameter.accept(builder);
            this.parameters.put(name, builder.done());
            return this;
        }

        public Builder addCorrelationId(String name, Consumer<Aa20CorrelationIdImpl.Builder> corellationId) {
            if(this.correlationIds == null) {
                this.correlationIds = new LinkedHashMap<>();
            }
            Aa20CorrelationIdImpl.Builder builder = Aa20CorrelationIdImpl.newBuilder();
            corellationId.accept(builder);
            this.correlationIds.put(name, builder.done());
            return this;
        }

        public Builder addOperationTrait(String name, Consumer<Aa20OperationTraitImpl.Builder> operationTrait) {
            if(this.operationTraits == null) {
                this.operationTraits = new LinkedHashMap<>();
            }
            Aa20OperationTraitImpl.Builder builder = Aa20OperationTraitImpl.newBuilder();
            operationTrait.accept(builder);
            this.operationTraits.put(name, builder.done());
            return this;
        }

        public Builder addMessageTrait(String name, Consumer< Aa20MessageTraitImpl.Builder> messageTrait) {
            if(this.messageTraits == null) {
                this.messageTraits = new LinkedHashMap<>();
            }
            Aa20MessageTraitImpl.Builder builder = Aa20MessageTraitImpl.newBuilder();
            messageTrait.accept(builder);
            this.messageTraits.put(name, builder.done());
            return this;
        }

        public Builder addServerBindings(String name, Consumer< Aa20ServerBindingsImpl.Builder> serverBindings) {
            if(this.serverBindings == null) {
                this.serverBindings = new LinkedHashMap<>();
            }
            Aa20ServerBindingsImpl.Builder builder = Aa20ServerBindingsImpl.newBuilder();
            serverBindings.accept(builder);
            this.serverBindings.put(name, builder.done());
            return this;
        }

        public Builder addChannelBindings(String name, Consumer< Aa20ChannelBindingsImpl.Builder> channelBindings) {
            if(this.channelBindings == null) {
                this.channelBindings = new LinkedHashMap<>();
            }
            Aa20ChannelBindingsImpl.Builder builder = Aa20ChannelBindingsImpl.newBuilder();
            channelBindings.accept(builder);
            this.channelBindings.put(name, builder.done());
            return this;
        }

        public Builder addOperationBindings(String name, Consumer< Aa20OperationBindingsImpl.Builder> operationBindings) {
            if(this.operationBindings == null) {
                this.operationBindings = new LinkedHashMap<>();
            }
            Aa20OperationBindingsImpl.Builder builder = Aa20OperationBindingsImpl.newBuilder();
            operationBindings.accept(builder);
            this.operationBindings.put(name, builder.done());
            return this;
        }

        public Builder addMessageBindings(String name, Consumer<Aa20MessageBindingsImpl.Builder> messageBindings) {
            if(this.messageBindings == null) {
                this.messageBindings = new LinkedHashMap<>();
            }
            Aa20MessageBindingsImpl.Builder builder = Aa20MessageBindingsImpl.newBuilder();
            messageBindings.accept(builder);
            this.messageBindings.put(name, builder.done());
            return this;
        }

        @Override
        public Aa20Components done() {
            return new Aa20ComponentsImpl(this);
        }
    }
}
