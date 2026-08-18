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
package org.apache.camel.component.langchain4j.ingest;

import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.spi.Metadata;
import org.apache.camel.spi.annotations.Component;
import org.apache.camel.support.DefaultComponent;

/**
 * Apache Camel component for ingesting documents into LangChain4j embedding stores.
 *
 * <p>
 * The producer takes the message body (plain text or a LangChain4j {@code Document}), splits it into segments with a
 * recursive splitter, embeds the segments in batches and stores them in the configured embedding store. Each stored
 * segment is stamped with the pipeline name (the endpoint path) and, when present, the id of the ingested document
 * taken from the {@code CamelLangChain4jIngestDocumentId} header.
 * </p>
 *
 * <p>
 * Any Camel consumer can feed the producer:
 * </p>
 *
 * <pre>{@code
 * from("file:documents?noop=true")
 *         .setHeader(LangChain4jIngestHeaders.DOCUMENT_ID, header(Exchange.FILE_NAME))
 *         .to("langchain4j-ingest:docs?maxSegmentSize=500&maxOverlapSize=50");
 * }</pre>
 *
 * @since 4.23.0
 */
@Component(LangChain4jIngest.SCHEME)
public class LangChain4jIngestComponent extends DefaultComponent {

    @Metadata
    private LangChain4jIngestConfiguration configuration;

    public LangChain4jIngestComponent() {
        this(null);
    }

    public LangChain4jIngestComponent(CamelContext context) {
        super(context);

        this.configuration = new LangChain4jIngestConfiguration();
    }

    public LangChain4jIngestConfiguration getConfiguration() {
        return configuration;
    }

    /**
     * The configuration;
     */
    public void setConfiguration(LangChain4jIngestConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    protected Endpoint createEndpoint(
            String uri,
            String remaining,
            Map<String, Object> parameters)
            throws Exception {

        LangChain4jIngestConfiguration configuration = this.configuration.copy();

        LangChain4jIngestEndpoint endpoint = new LangChain4jIngestEndpoint(uri, this, remaining, configuration);
        setProperties(endpoint, parameters);

        return endpoint;
    }
}
