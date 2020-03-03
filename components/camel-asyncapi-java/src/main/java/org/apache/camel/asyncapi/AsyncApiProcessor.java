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
package org.apache.camel.asyncapi;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.apicurio.datamodels.asyncapi.models.AaiDocument;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.spi.AsyncApiConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


public class AsyncApiProcessor implements Processor {

    private static final Logger LOG = LoggerFactory.getLogger(AsyncApiProcessor.class);
    private final String contextIdPattern;
    private final boolean contextIdListing;
    private final AsyncApiConfiguration configuration;

    @SuppressWarnings("unchecked")
    public AsyncApiProcessor(String contextIdPattern, boolean contextIdListing, Map<String, Object> parameters, AsyncApiConfiguration configuration) {
        this.contextIdPattern = contextIdPattern;
        this.contextIdListing = contextIdListing;
        this.configuration = configuration;
    }

    @Override
    public void process(Exchange exchange) throws Exception {

        AsyncApiResponseAdapter adapter = new AsyncApiResponseAdapter(exchange);

        renderResourceListing(configuration, adapter);
    }

    public void renderResourceListing(AsyncApiConfiguration asyncAPIConfiguration, AsyncApiResponseAdapter response)
            throws Exception {
        LOG.trace("renderResourceListing");

        AaiDocument asyncApi = asyncAPIConfiguration.getAsyncapiDoc();

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        Object dump = io.apicurio.datamodels.Library.writeNode(asyncApi);
        byte[] bytes = mapper.writeValueAsBytes(dump);
        int len = bytes.length;
        response.setHeader(Exchange.CONTENT_LENGTH, "" + len);

        response.writeBytes(bytes);
    }

}
