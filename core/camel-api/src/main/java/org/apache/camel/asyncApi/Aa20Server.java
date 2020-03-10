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
 * An object representing a message broker, a server or any other kind of computer
 * program capable of sending and/or receiving data. This object is used to capture
 * details such as URIs, protocols and security configuration. Variable substitution
 * can be used so that some details, for example usernames and passwords, can be
 * injected by code generation tools.
 */
public interface Aa20Server {

    String getUrl();

    String getProtocol();

    String getProtocolVersion();

    String getDescription();

    Map<String, Aa20ServerVariable> getVariables();

    Aa20SecurityRequirement getSecurity();

    Aa20ServerBindings getBindings();
}
