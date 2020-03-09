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

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Lists the required security schemes to execute this operation. The name used for each property MUST correspond to a security scheme declared in the Security Schemes under the Components Object.
 *
 * When a list of Security Requirement Objects is defined on a Server object, only one of the Security Requirement Objects in the list needs to be satisfied to authorize the connection.
 */
@JsonInclude(JsonInclude.Include.ALWAYS)
public abstract class Aa20SecurityRequirement<T extends Aa20SecurityRequirement> {

    abstract List<String> getSchemas();

    public T createSchema(String schema) {
        getSchemas().add(schema);
        return (T)this;
    }
}
