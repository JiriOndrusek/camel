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
 * Describes a trait that MAY be applied to a Message Object. This object MAY contain any property from the Message Object, except payload and traits.
 *
 * If you’re looking to apply traits to an operation, see the Operation Trait Object.
 */
public class Aa20MessageTrait extends AbstractMessage<Aa20MessageTrait> {

    List<String> examples = new LinkedList<>();

    public List<String> getExamples() {
        return examples;
    }

    /**
     * todo
     * @param example An array with examples of valid message objects.
     */
    public Aa20MessageTrait addExample(String example) {
        this.examples.add(example);
        return this;
    }
}
