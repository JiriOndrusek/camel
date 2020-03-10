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
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Aa20Components {

    private Map<String, Aa20OrReferenceType<Aa20Schema>> schemas = new LinkedHashMap();
    private Map<String, Aa20OrReferenceType<Aa20Message>> messages = new LinkedHashMap();
    private Map<String, Aa20OrReferenceType<? extends Aa20AbstractSecuritySchema>> securitySchemes = new LinkedHashMap();
    private Map<String, Aa20OrReferenceType<Aa20Parameter>> parameters = new LinkedHashMap();
    private Map<String, Aa20OrReferenceType<Aa20CorellationId>> correlationIds = new LinkedHashMap();
    private Map<String, Aa20OrReferenceType<Aa20OperationTrait>> operationTraits = new LinkedHashMap();
    private Map<String, Aa20OrReferenceType<Aa20MessageTrait>> messageTraits = new LinkedHashMap();
    private Map<String, Aa20OrReferenceType<Aa20ServerBindings>> serverBindings = new LinkedHashMap();
    private Map<String, Aa20OrReferenceType<Aa20ChannelBindings>> channelBindings = new LinkedHashMap();
    private Map<String, Aa20OrReferenceType<Aa20OperationBindings>> operationBindings = new LinkedHashMap<>();
    private Map<String, Aa20OrReferenceType<Aa20MessageBinding>> messageBindings = new LinkedHashMap();

    public Map<String, Aa20OrReferenceType<Aa20Message>> getMessages() {
        return messages;
    }

    /**
     * @param messages An object to hold reusable Message Objects.
     */
    public void setMessages(Map<String, Aa20OrReferenceType<Aa20Message>> messages) {
        this.messages = messages;
    }

    public Map<String, Aa20OrReferenceType<Aa20Schema>> getSchemas() {
        return schemas;
    }

    /**
     * @param schemas An object to hold reusable Message Objects.
     */
    public void setSchemas(Map<String, Aa20OrReferenceType<Aa20Schema>> schemas) {
        this.schemas = schemas;
    }

    public Map<String, Aa20OrReferenceType<? extends Aa20AbstractSecuritySchema>> getSecuritySchemes() {
        return securitySchemes;
    }

    /**
     * @param securitySchemes An object to hold reusable Security Scheme Objects.
     */
    public void setSecuritySchemes(Map<String, Aa20OrReferenceType<? extends Aa20AbstractSecuritySchema>> securitySchemes) {
        this.securitySchemes = securitySchemes;
    }

    public Map<String, Aa20OrReferenceType<Aa20Parameter>> getParameters() {
        return parameters;
    }

    /**
     * @param parameters An object to hold reusable Parameter Objects.
     */
    public Aa20Components setParameters(Map<String, Aa20OrReferenceType<Aa20Parameter>> parameters) {
        this.parameters = parameters;
        return this;
    }

    public Map<String, Aa20OrReferenceType<Aa20CorellationId>> getCorrelationIds() {
        return correlationIds;
    }

    /**
     * @param correlationIds An object to hold reusable Correlation ID Objects.
     */
    public Aa20Components setCorrelationIds(Map<String, Aa20OrReferenceType<Aa20CorellationId>> correlationIds) {
        this.correlationIds = correlationIds;
        return this;
    }

    public Map<String, Aa20OrReferenceType<Aa20OperationTrait>> getOperationTraits() {
        return operationTraits;
    }

    /**
     * @param operationTraits An object to hold reusable Operation Trait Objects.
     */
    public Aa20Components setOperationTraits(Map<String, Aa20OrReferenceType<Aa20OperationTrait>> operationTraits) {
        this.operationTraits = operationTraits;
        return this;
    }

    public Map<String, Aa20OrReferenceType<Aa20MessageTrait>> getMessageTraits() {
        return messageTraits;
    }

    /**
     * @param messageTraits An object to hold reusable Message Trait Objects.
     */
    public Aa20Components setMessageTraits(Map<String, Aa20OrReferenceType<Aa20MessageTrait>> messageTraits) {
        this.messageTraits = messageTraits;
        return this;

    }

    public Map<String, Aa20OrReferenceType<Aa20ServerBindings>> getServerBindings() {
        return serverBindings;
    }

    /**
     * @param serverBindings An object to hold reusable Server Binding Objects.
     */
    public Aa20Components setServerBindings(Map<String, Aa20OrReferenceType<Aa20ServerBindings>> serverBindings) {
        this.serverBindings = serverBindings;
        return this;

    }

    public Map<String, Aa20OrReferenceType<Aa20ChannelBindings>> getChannelBindings() {
        return channelBindings;
    }

    /**
     * @param channelBindings An object to hold reusable Channel Binding Objects.
     */
    public Aa20Components setChannelBindings(Map<String, Aa20OrReferenceType<Aa20ChannelBindings>> channelBindings) {
        this.channelBindings = channelBindings;
        return this;

    }

    public Map<String, Aa20OrReferenceType<Aa20OperationBindings>> getOperationBindings() {
        return operationBindings;
    }

    /**
     * @param operationBindings An object to hold reusable Operation Binding Objects.
     */
    public Aa20Components setOperationBindings(Map<String, Aa20OrReferenceType<Aa20OperationBindings>> operationBindings) {
        this.operationBindings = operationBindings;
        return this;

    }

    public Map<String, Aa20OrReferenceType<Aa20MessageBinding>> getMessageBindings() {
        return messageBindings;
    }

    /**
     * @param messageBindings An object to hold reusable Message Binding Objects.
     */
    public Aa20Components setMessageBindings(Map<String, Aa20OrReferenceType<Aa20MessageBinding>> messageBindings) {
        this.messageBindings = messageBindings;
        return this;
    }

    // --------------------------------------- create methods ---------------------------------------------------------

    private Aa20Reference createItemAsReference(Consumer<Aa20OrReferenceType> putIntoMap, String $ref) {
        Aa20Reference reference = new Aa20Reference($ref);
        putIntoMap.accept(reference);
        return reference;
    }

    private <T> Aa20OrReferenceType<T> createItem(Supplier<Aa20OrReferenceType<T>> supplier, Map<String, Aa20OrReferenceType<T>> map, String name) {
        Aa20OrReferenceType<T> item = supplier.get();
        map.put(name, item);
        return item;
    }

    public Aa20Schema createSchema(String name) {
        return createItem(() -> new Aa20Schema(), schemas, name).asObject(Aa20Schema.class);
    }

    public Aa20Reference createSchemaAsReference(String name, String $ref) {
       return createItemAsReference(r -> schemas.put(name, r), $ref);
    }

    public Aa20Message createMessage(String name) {
        return createItem(() -> new Aa20Message(), messages, name).asObject(Aa20Message.class);
    }

    public Aa20Reference createMessageAsReference(String name, String $ref) {
        return createItemAsReference(r -> messages.put(name, r), $ref);
    }

    public Aa20SecuritySchemaApiKey createSecuritySchemaApiKey(String name, String in) {
        Aa20SecuritySchemaApiKey securitySchema = new Aa20SecuritySchemaApiKey(in);
        securitySchemes.put(name, securitySchema);
        return securitySchema;
    }

    public Aa20SecuritySchemaSymetricEncryption createSecuritySchemaSymetricEncryption(String name) {
        Aa20SecuritySchemaSymetricEncryption securitySchema = new Aa20SecuritySchemaSymetricEncryption();
        securitySchemes.put(name, securitySchema);
        return securitySchema;
    }

    public Aa20SecuritySchemaAsymetricEncryption createSecuritySchemaAsymetricEncryption(String name) {
        Aa20SecuritySchemaAsymetricEncryption securitySchema = new Aa20SecuritySchemaAsymetricEncryption();
        securitySchemes.put(name, securitySchema);
        return securitySchema;
    }

    public Aa20SecuritySchemaHttpApiKey createSecuritySchemaHttpApiKey(String name, String in) {
        Aa20SecuritySchemaHttpApiKey securitySchema = new Aa20SecuritySchemaHttpApiKey(in);
        securitySchemes.put(name, securitySchema);
        return securitySchema;
    }

    public Aa20SecuritySchemaHttp createSecuritySchemaHttp(String name, String schema) {
        Aa20SecuritySchemaHttp securitySchema = new Aa20SecuritySchemaHttp(schema);
        securitySchemes.put(name, securitySchema);
        return securitySchema;
    }

    public Aa20SecuritySchemaOAuth2 createSecuritySchemaHttpOAuth2(String name) {
        Aa20SecuritySchemaOAuth2 securitySchema = new Aa20SecuritySchemaOAuth2();
        securitySchemes.put(name, securitySchema);
        return securitySchema;
    }

    public Aa20SecuritySchemaOpenIdConnect createSecuritySchemaOpenIdConnectDiscovery(String name, String openIdConnect) {
        Aa20SecuritySchemaOpenIdConnect securitySchema = new Aa20SecuritySchemaOpenIdConnect(openIdConnect);
        securitySchemes.put(name, securitySchema);
        return securitySchema;
    }

    public Aa20SecuritySchemaUserPassword createSecuritySchemaUserPassword(String name) {
        Aa20SecuritySchemaUserPassword securitySchema = new Aa20SecuritySchemaUserPassword();
        securitySchemes.put(name, securitySchema);
        return securitySchema;
    }

    public Aa20SecuritySchemaX509Certificate createSecuritySchemaX509Certificate(String name) {
        Aa20SecuritySchemaX509Certificate securitySchema = new Aa20SecuritySchemaX509Certificate();
        securitySchemes.put(name, securitySchema);
        return securitySchema;
    }

    public Aa20Reference createSecuritySchemaAsReference(String name, String $ref) {
        return createItemAsReference(r -> securitySchemes.put(name, r), $ref);
    }

    public Aa20Parameter createParameter(String name) {
        return createItem(() -> new Aa20Parameter(), parameters, name).asObject(Aa20Parameter.class);
    }

    public Aa20Reference createParameterAsReference(String name, String $ref) {
        return createItemAsReference(r -> parameters.put(name, r), $ref);
    }

    public Aa20CorellationId createCorellationId(String name, String location) {
        return createItem(() -> new Aa20CorellationId(location), correlationIds, name).asObject(Aa20CorellationId.class);
    }

    public Aa20Reference createCorellationIdAsReference(String name, String $ref) {
        return createItemAsReference(r -> correlationIds.put(name, r), $ref);
    }

    public Aa20OperationTrait createOperationTrait(String name) {
        return createItem(() -> new Aa20OperationTrait(), operationTraits, name).asObject(Aa20OperationTrait.class);
    }

    public Aa20Reference createOperationTraitAsReference(String name, String $ref) {
        return createItemAsReference(r -> operationTraits.put(name, r), $ref);
    }

    public Aa20MessageTrait createMessageTrait(String name) {
        return createItem(() -> new Aa20MessageTrait(), messageTraits, name).asObject(Aa20MessageTrait.class);
    }

    public Aa20Reference createMessageTraitAsReference(String name, String $ref) {
        return createItemAsReference(r -> messageTraits.put(name, r), $ref);
    }

//    public Aa20ServerBindings createServerBindings(String name) {
//        return createItem(() -> new Aa20ServerBindings(), serverBindings, name).asObject(Aa20ServerBindings.class);
//    }

    public Aa20Reference createServerBindingAsReference(String name, String $ref) {
        return createItemAsReference(r -> serverBindings.put(name, r), $ref);
    }

    public Aa20ChannelBindings createChannelBinding(String name) {
        return createItem(() -> new Aa20ChannelBindings(), channelBindings, name).asObject(Aa20ChannelBindings.class);
    }

    public Aa20Reference createChannelBindingAsReference(String name, String $ref) {
        return createItemAsReference(r -> channelBindings.put(name, r), $ref);
    }

    public Aa20MessageBinding createMessageBinding(String name) {
        return createItem(() -> new Aa20MessageBinding(), messageBindings, name).asObject(Aa20MessageBinding.class);
    }

    public Aa20Reference createMessageBindingAsReference(String name, String $ref) {
        return createItemAsReference(r -> messageBindings.put(name, r), $ref);
    }

}
