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

import java.nio.charset.StandardCharsets;
import java.util.List;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.allminilml6v2.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.apache.camel.CamelContext;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit6.CamelTestSupport;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LangChain4jIngestTest extends CamelTestSupport {

    private final EmbeddingModel embeddingModel = new AllMiniLmL6V2EmbeddingModel();
    private final InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();

    @Override
    protected CamelContext createCamelContext() throws Exception {
        CamelContext context = super.createCamelContext();

        LangChain4jIngestComponent component
                = context.getComponent(LangChain4jIngest.SCHEME, LangChain4jIngestComponent.class);
        component.getConfiguration().setEmbeddingStore(embeddingStore);
        component.getConfiguration().setEmbeddingModel(embeddingModel);

        return context;
    }

    @Override
    protected RoutesBuilder createRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                from("direct:ingest")
                        .to("langchain4j-ingest:docs?maxSegmentSize=120&maxOverlapSize=20");

                from("direct:ingest-tiny-segments")
                        .to("langchain4j-ingest:tiny?maxSegmentSize=30&maxOverlapSize=0");
            }
        };
    }

    @Test
    void ingestedSegmentsAreStoredWithPipelineAndDocumentIdMetadata() {
        LangChain4jIngestResult result = fluentTemplate.to("direct:ingest")
                .withBody("Apache Camel is a powerful integration framework. "
                          + "It connects hundreds of systems with a consistent routing model.")
                .withHeader(LangChain4jIngestHeaders.DOCUMENT_ID, "manual.txt")
                .request(LangChain4jIngestResult.class);

        assertThat(result.outcome()).isEqualTo(LangChain4jIngestResult.Outcome.INGESTED);
        assertThat(result.pipeline()).isEqualTo("docs");
        assertThat(result.documentId()).isEqualTo("manual.txt");
        assertThat(result.segmentsWritten()).isGreaterThanOrEqualTo(1);

        List<EmbeddingMatch<TextSegment>> matches = search("integration framework", 5);
        assertThat(matches).isNotEmpty();
        Metadata metadata = matches.get(0).embedded().metadata();
        assertThat(metadata.getString(LangChain4jIngest.METADATA_PIPELINE)).isEqualTo("docs");
        assertThat(metadata.getString(LangChain4jIngest.METADATA_DOCUMENT_ID)).isEqualTo("manual.txt");
    }

    @Test
    void blankBodyIsNotIngested() {
        LangChain4jIngestResult result = fluentTemplate.to("direct:ingest")
                .withBody("   ")
                .withHeader(LangChain4jIngestHeaders.DOCUMENT_ID, "empty.txt")
                .request(LangChain4jIngestResult.class);

        assertThat(result.outcome()).isEqualTo(LangChain4jIngestResult.Outcome.EMPTY);
        assertThat(result.segmentsWritten()).isZero();
    }

    @Test
    void nonStringBodyIsConvertedToText() {
        // consumers deliver wrapped payloads (GenericFile, byte[]); the producer must convert
        // rather than expect a String or Document body
        LangChain4jIngestResult result = fluentTemplate.to("direct:ingest")
                .withBody("Bytes from a queue still become a document.".getBytes(StandardCharsets.UTF_8))
                .withHeader(LangChain4jIngestHeaders.DOCUMENT_ID, "bytes.txt")
                .request(LangChain4jIngestResult.class);

        assertThat(result.outcome()).isEqualTo(LangChain4jIngestResult.Outcome.INGESTED);

        List<EmbeddingMatch<TextSegment>> matches = search("bytes from a queue", 1);
        assertThat(matches.get(0).embedded().metadata().getString(LangChain4jIngest.METADATA_DOCUMENT_ID))
                .isEqualTo("bytes.txt");
    }

    @Test
    void documentIdIsOptional() {
        LangChain4jIngestResult result = fluentTemplate.to("direct:ingest")
                .withBody("A document without an id is still ingested.")
                .request(LangChain4jIngestResult.class);

        assertThat(result.outcome()).isEqualTo(LangChain4jIngestResult.Outcome.INGESTED);
        assertThat(result.documentId()).isNull();

        List<EmbeddingMatch<TextSegment>> matches = search("document without an id", 1);
        Metadata metadata = matches.get(0).embedded().metadata();
        assertThat(metadata.getString(LangChain4jIngest.METADATA_PIPELINE)).isEqualTo("docs");
        assertThat(metadata.containsKey(LangChain4jIngest.METADATA_DOCUMENT_ID)).isFalse();
    }

    @Test
    void metadataHeadersAreFoldedIntoSegmentMetadata() {
        fluentTemplate.to("direct:ingest")
                .withBody("The quarterly report shows steady growth.")
                .withHeader(LangChain4jIngestHeaders.DOCUMENT_ID, "report.txt")
                .withHeader("langchain4j.metadata.source", "unit-test")
                .send();

        List<EmbeddingMatch<TextSegment>> matches = search("quarterly report", 1);
        assertThat(matches.get(0).embedded().metadata().getString("source")).isEqualTo("unit-test");
    }

    @Test
    void documentBodyKeepsItsMetadata() {
        Document document = Document.from("Somewhat longer prose about camels crossing the desert.",
                Metadata.from("origin", "caravan"));

        LangChain4jIngestResult result = fluentTemplate.to("direct:ingest")
                .withBody(document)
                .withHeader(LangChain4jIngestHeaders.DOCUMENT_ID, "camels.txt")
                .request(LangChain4jIngestResult.class);

        assertThat(result.outcome()).isEqualTo(LangChain4jIngestResult.Outcome.INGESTED);

        List<EmbeddingMatch<TextSegment>> matches = search("camels crossing the desert", 1);
        Metadata metadata = matches.get(0).embedded().metadata();
        assertThat(metadata.getString("origin")).isEqualTo("caravan");
        assertThat(metadata.getString(LangChain4jIngest.METADATA_PIPELINE)).isEqualTo("docs");
        assertThat(metadata.getString(LangChain4jIngest.METADATA_DOCUMENT_ID)).isEqualTo("camels.txt");
    }

    @Test
    void largeDocumentIsEmbeddedInBatches() {
        StringBuilder text = new StringBuilder();
        for (int i = 1; i <= 40; i++) {
            text.append("Numbered marker sentence ").append(i).append(".\n");
        }

        LangChain4jIngestResult result = fluentTemplate.to("direct:ingest-tiny-segments")
                .withBody(text.toString())
                .withHeader(LangChain4jIngestHeaders.DOCUMENT_ID, "large.txt")
                .request(LangChain4jIngestResult.class);

        assertThat(result.outcome()).isEqualTo(LangChain4jIngestResult.Outcome.INGESTED);
        // more segments than one embedding batch, so the batching loop ran more than once
        assertThat(result.segmentsWritten()).isGreaterThan(LangChain4jIngest.EMBEDDING_BATCH_SIZE);

        List<EmbeddingMatch<TextSegment>> matches = search("Numbered marker sentence", 50);
        assertThat(matches).hasSize(result.segmentsWritten());
    }

    private List<EmbeddingMatch<TextSegment>> search(String query, int maxResults) {
        return embeddingStore.search(EmbeddingSearchRequest.builder()
                .queryEmbedding(embeddingModel.embed(query).content())
                .maxResults(maxResults)
                .build()).matches();
    }
}
