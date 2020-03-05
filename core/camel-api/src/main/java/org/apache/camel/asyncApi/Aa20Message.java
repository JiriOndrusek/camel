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
 * Describes a message received on a given channel and operation.
 */
public class Aa20Message extends AbstractMessage<Aa20Message> {


    List<Aa20MessageTrait> traits = new LinkedList<>();

    public List<Aa20MessageTrait> getTraits() {
        return traits;
    }

    /**
     * todo
     * @return A list of traits to apply to the message object. Traits MUST be merged into the message object using the JSON Merge Patch algorithm in the same order they are defined here. The resulting object MUST be a valid Message Object.
     */
    public Aa20MessageTrait createTrait() {
        Aa20MessageTrait trait = new Aa20MessageTrait();
        this.traits.add(trait);
        return trait;
    }
}
