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

public class Aa20ChannelItem {

    String $ref;
    String description;
    Aa20OperationObject subscribe;
    //todo publish
    //todo parameters
    //todo bindings


    public String get$ref() {
        return $ref;
    }

    /**
     * Allows for an external definition of this channel item. The referenced
     * structure MUST be in the format of a Channel Item Object. If there are
     * conflicts between the referenced definition and this Channel Item’s
     * definition, the behavior is undefined.
     */
    public Aa20ChannelItem set$ref(String $ref) {
        this.$ref = $ref;
        return this;
    }

    public String getDescription() {
        return description;
    }

    /**
     * An optional description of this channel item. CommonMark syntax can be
     * used for rich text representation.
     */
    public Aa20ChannelItem setDescription(String description) {
        this.description = description;
        return this;
    }

    public Aa20OperationObject getSubscribe() {
        return subscribe;
    }

    /**
     * A definition of the SUBSCRIBE operation.
     */
    public Aa20ChannelItem setSubscribe(Aa20OperationObject subscribe) {
        this.subscribe = subscribe;
        return this;
    }
}