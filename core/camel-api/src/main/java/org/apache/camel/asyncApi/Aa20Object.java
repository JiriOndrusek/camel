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
import java.util.Map;

/**
 * This is the root document object for the API specification. It combines
 * resource listing and API declaration together into one document.
 */
public interface Aa20Object {

    String getAsyncapi();

    String getId() ;

    Aa20Info getInfo();

    Map<String, Aa20Server> getServers() ;

    public String getDefaultContentType();

    Map<String, Aa20ChannelItem> getChannels();

    Aa20Components getComponents();

    List<Aa20Tag> getTags();

    Aa20ExternalDocumentation getExternalDocs();
}
