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
public interface Aa20Operation {

    String getOperationId();

    String getSummary();

    String getDescription();

    List<Aa20Tag> getTags();

    Aa20ExternalDocumentation getExternalDocs();

    Aa20MessageBinding getBindings();

    List<Aa20OperationTrait> getTraits();

    Aa20Message getMessage();
}
