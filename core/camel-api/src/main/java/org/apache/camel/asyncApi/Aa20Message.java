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

import java.util.*;

/**
 * Describes a message received on a given channel and operation.
 */
public interface Aa20Message  {


    Aa20OrReferenceType<Aa20Schema> getHeaders() ;

    Aa20OrReferenceType<Aa20CorellationId> getCorrelationId();

    String getSchemaFormat() ;

    String getContentType();

    String getName();

    String getTitle();

    List<String> getExamples();

    Map<String, Object> getPayload();

    List<Aa20OrReferenceType<Aa20MessageTrait>> getTraits();

    Set<Aa20Message> getOneOf();

    String get$ref();
}