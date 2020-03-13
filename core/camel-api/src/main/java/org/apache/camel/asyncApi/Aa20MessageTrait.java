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

import java.util.List;

/**
 * Describes a trait that MAY be applied to a Message Object. This object MAY contain any property from the Message Object, except payload and traits.
 *
 * If you’re looking to apply traits to an operation, see the Operation Trait Object.
 */
public interface Aa20MessageTrait {

   Aa20Schema getHeaders() ;

   Aa20CorrelationId getCorrelationId();

   String getSchemaFormat() ;

   String getContentType();

   String getName();

   String getTitle();

   String getSummary();

   String getDescription();

   List<Aa20Tag> getTags();

   Aa20ExternalDocumentation getExternalDocs();

   Aa20MessageBindings getBindings();

   List<String> getExamples();

   String get$ref();
}
