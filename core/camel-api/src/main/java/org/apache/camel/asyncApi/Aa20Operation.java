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

import java.util.LinkedList;
import java.util.List;

/**
 * Describes a publish or a subscribe operation. This provides a place
 * to document how and why messages are sent and received. For example,
 * an operation might describe a chat application use case where a user
 * sends a text message to a group.
 */
public class Aa20Operation extends AbstractOperation<Aa20Operation> {

    List<Aa20OrReferenceType<Aa20OperationTrait>> traits = new LinkedList<>();
    private Aa20OrReferenceType<Aa20Message> message;


    public void setTraits(List<Aa20OrReferenceType<Aa20OperationTrait>> traits) {
        this.traits = traits;
    }

    public List<Aa20OrReferenceType<Aa20OperationTrait>> getTraits() {
        return traits;
    }

    public Aa20OrReferenceType<Aa20Message> getMessage() {
        return message;
    }

    public void setMessage(Aa20OrReferenceType<Aa20Message> message) {
        this.message = message;
    }

// --------------------------------------- create methods ---------------------------------------------------------

    /**
     * @return A list of traits to apply to the operation object.
     * Traits MUST be merged into the operation object using
     * the JSON Merge Patch algorithm in the same order they are defined here.
     */
    public Aa20OperationTrait createTrait() {
        Aa20OperationTrait trait = new Aa20OperationTrait();
        this.traits.add(trait);
        return trait;
    }

    public Aa20Operation createTraitAsReference(String $ref) {
        Aa20Reference ref = new Aa20Reference($ref);
        this.traits.add(ref);
        return this;

    }

    /**
     * A definition of the message that will be published or received on this channel. oneOf is allowed here to specify multiple messages, however, a message MUST be valid only against one of the referenced message objects.
     */
    public Aa20Message createMessage() {
        Aa20Message message = new Aa20Message();
        this.message = message;
        return message;
    }

    public Aa20Operation createMessageAsReference(String $ref) {
        Aa20Reference ref = new Aa20Reference($ref);
        this.message = ref;
        return this;
    }
}
