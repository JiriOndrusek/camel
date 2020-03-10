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

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.camel.asyncApi.Aa20SchemaType;
import org.apache.camel.asyncApi.Aa20SecurityRequirement;
import org.apache.camel.asyncApi.Aa20SecurityRequirementApiKey;

import java.util.LinkedList;
import java.util.List;

public class Aa20SecurityRequirementApiKeyImpl extends Aa20SecurityRequirementImpl implements Aa20SecurityRequirementApiKey {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Override
    public List<String> getApiKey() {
        return getSchemas();
    };


}
