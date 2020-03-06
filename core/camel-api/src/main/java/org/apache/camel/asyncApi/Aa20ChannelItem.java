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

/**
 * Describes the operations available on a single channel.
 * todo This object can be extended with Specification Extensions.
 */
public class Aa20ChannelItem {

    //todo The referenced
    //     * structure MUST be in the format of a Channel Item Object. If there are
    //     * conflicts between the referenced definition and this Channel Item’s
    //     * definition, the behavior is undefined.
    String $ref;
    // todo CommonMark syntax can be used for rich text representation.
    String description;
    Aa20Operation subscribe;
    Aa20Operation publish;
    Map<String, Aa20OrReferenceType<Aa20Parameter>> parameters = new LinkedHashMap<>();
    Aa20ChannelBindings bindings = new Aa20ChannelBindings();


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

    public Aa20Operation getSubscribe() {
        return subscribe;
    }


    public Aa20Operation getPublish() {
        return publish;
    }


    public void setSubscribe(Aa20Operation subscribe) {
        this.subscribe = subscribe;
    }

    public void setPublish(Aa20Operation publish) {
        this.publish = publish;
    }

    public Map<String, Aa20OrReferenceType<Aa20Parameter>> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Aa20OrReferenceType<Aa20Parameter>> parameters) {
        this.parameters = parameters;
    }



    public Aa20ChannelBindings getBindings() {
        return bindings;
    }

    //todo
    public void setBindings(Aa20ChannelBindings bindings) {
        this.bindings = bindings;
    }


    // --------------------------------------- create methods ---------------------------------------------------------


    /**
     * A definition of the SUBSCRIBE operation.
     */
    public Aa20Operation createSubscribe() {
        Aa20Operation subscribe = new Aa20Operation();
        this.subscribe = subscribe;
        return subscribe;
    }


    /**
     * A definition of the PUBLISH operation.
     */
    public Aa20Operation createPublish() {
        Aa20Operation publish = new Aa20Operation();
        this.publish = publish;
        return publish;
    }

    public Aa20Parameter createParameter(String name) {
        Aa20Parameter parameter = new Aa20Parameter();
        parameters.put(name, parameter);
        return parameter;
    }

    public Aa20ChannelItem createParameterAsReference(String name, String $ref) {
        Aa20Reference parameter = new Aa20Reference($ref);
        parameters.put(name, parameter);
        return this;
    }
}