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

import java.util.Map;

public interface Aa20Components {

   Map<String, Aa20Message> getMessages();

   Map<String, Aa20Schema> getSchemas();

    Map<String, Aa20SecurityScheme> getSecuritySchemes();

    Map<String, Aa20Parameter> getParameters();

    Map<String, Aa20CorrelationId> getCorrelationIds();

    Map<String, Aa20OperationTrait> getOperationTraits();

    Map<String, Aa20MessageTrait> getMessageTraits();

    Map<String, Aa20ServerBindings> getServerBindings();

    Map<String, Aa20ChannelBindings> getChannelBindings();

    Map<String, Aa20OperationBindings> getOperationBindings();

    Map<String, Aa20MessageBindings> getMessageBindings();
}
