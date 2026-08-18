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

import java.util.List;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingStore;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.component.langchain4j.embeddingstore.EmbeddingStoreFactory;
import org.apache.camel.support.DefaultProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Producer that splits the incoming document, embeds the segments and writes them to the embedding store.
 *
 * <p>
 * Deliberately naive: it writes whatever it is given and remembers nothing, so ingesting a document twice leaves two
 * copies. Keeping a store in step with a changing source — skipping unchanged documents, replacing changed ones,
 * removing deleted ones — needs a record of what was written, and an engine with such a record can replace this one
 * behind the same endpoint.
 * </p>
 */
public class LangChain4jIngestProducer extends DefaultProducer {
    private static final Logger LOG = LoggerFactory.getLogger(LangChain4jIngestProducer.class);

    private DocumentSplitter splitter;

    public LangChain4jIngestProducer(LangChain4jIngestEndpoint endpoint) {
        super(endpoint);
    }

    @Override
    public LangChain4jIngestEndpoint getEndpoint() {
        return (LangChain4jIngestEndpoint) super.getEndpoint();
    }

    @Override
    public void doStart() throws Exception {
        super.doStart();

        LangChain4jIngestConfiguration config = getEndpoint().getConfiguration();
        String pipeline = getEndpoint().getPipelineName();

        int maxSegmentSize = config.getMaxSegmentSize();
        int maxOverlapSize = config.getMaxOverlapSize();
        if (maxSegmentSize <= 0 || maxOverlapSize < 0 || maxOverlapSize >= maxSegmentSize) {
            throw new IllegalArgumentException(
                    "Ingestion pipeline '" + pipeline
                                               + "': maxSegmentSize must be positive and maxOverlapSize must be smaller than maxSegmentSize, got maxSegmentSize="
                                               + maxSegmentSize + " and maxOverlapSize=" + maxOverlapSize);
        }

        EmbeddingStoreFactory embeddingStoreFactory = config.getEmbeddingStoreFactory();
        if (embeddingStoreFactory != null) {
            embeddingStoreFactory.setCamelContext(getEndpoint().getCamelContext());
            EmbeddingStore es = embeddingStoreFactory.createEmbeddingStore();
            config.setEmbeddingStore(es);
        }
        if (config.getEmbeddingStore() == null) {
            throw new IllegalArgumentException(
                    "Ingestion pipeline '" + pipeline
                                               + "': no embeddingStore or embeddingStoreFactory is configured and none could be autowired from the registry");
        }
        if (config.getEmbeddingModel() == null) {
            throw new IllegalArgumentException(
                    "Ingestion pipeline '" + pipeline
                                               + "': no embeddingModel is configured and none could be autowired from the registry");
        }

        splitter = DocumentSplitters.recursive(maxSegmentSize, maxOverlapSize);
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        final Message in = exchange.getMessage();
        final String pipeline = getEndpoint().getPipelineName();

        try {
            String documentId = in.getHeader(LangChain4jIngestHeaders.DOCUMENT_ID, String.class);

            Document document = resolveDocument(exchange);
            if (document == null) {
                in.setBody(new LangChain4jIngestResult(pipeline, documentId, 0, LangChain4jIngestResult.Outcome.EMPTY));
                return;
            }

            // the pipeline name and document id travel with every segment: retrieval can cite the
            // source document, and an engine that keeps the store in step with the source needs
            // them to find a document's segments again
            document.metadata().put(LangChain4jIngest.METADATA_PIPELINE, pipeline);
            if (documentId != null && !documentId.isBlank()) {
                document.metadata().put(LangChain4jIngest.METADATA_DOCUMENT_ID, documentId);
            }

            LangChain4jIngestConfiguration config = getEndpoint().getConfiguration();
            List<TextSegment> segments = splitter.split(document);
            for (int from = 0; from < segments.size(); from += LangChain4jIngest.EMBEDDING_BATCH_SIZE) {
                List<TextSegment> batch = segments.subList(from,
                        Math.min(from + LangChain4jIngest.EMBEDDING_BATCH_SIZE, segments.size()));
                config.getEmbeddingStore().addAll(config.getEmbeddingModel().embedAll(batch).content(), batch);
            }

            LOG.debug("Ingestion pipeline '{}': wrote {} segment(s) of document '{}'", pipeline, segments.size(),
                    documentId);
            in.setBody(new LangChain4jIngestResult(
                    pipeline, documentId, segments.size(), LangChain4jIngestResult.Outcome.INGESTED));
        } catch (Exception e) {
            exchange.setException(e);
        }
    }

    /**
     * The document to ingest: the body itself when it already is a {@link Document}, otherwise the body text converted
     * with the {@code camel-langchain4j-core} converter, which also folds {@code langchain4j.metadata.*} headers into
     * the document metadata. Returns {@code null} when there is nothing to ingest.
     */
    private Document resolveDocument(Exchange exchange) throws Exception {
        final Message in = exchange.getMessage();

        Object body = in.getBody();
        if (body instanceof Document document) {
            return document;
        }

        // the body is converted to text first: consumers deliver wrapped payloads - a file
        // consumer a GenericFile, a queue consumer bytes - and converter chains are not composed
        // automatically, so converting the body straight to Document would fail for them
        String text = in.getBody(String.class);
        if (text == null || text.isBlank()) {
            return null;
        }
        return exchange.getContext().getTypeConverter().mandatoryConvertTo(Document.class, exchange, text);
    }
}
