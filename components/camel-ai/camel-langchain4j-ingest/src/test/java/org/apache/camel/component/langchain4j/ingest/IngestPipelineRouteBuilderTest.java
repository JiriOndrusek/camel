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

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.spi.IdempotentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class IngestPipelineRouteBuilderTest {

    @TempDir
    Path directory;

    @Test
    void directoryPipelineIngestsTheFiles() throws Exception {
        Files.writeString(directory.resolve("a.txt"), "Alpha document about camels.");
        Files.writeString(directory.resolve("b.txt"), "Beta document about routes.");

        InMemoryEmbeddingStore<TextSegment> store = new InMemoryEmbeddingStore<>();
        DeterministicEmbeddingModel model = new DeterministicEmbeddingModel(16);
        try (DefaultCamelContext context = new DefaultCamelContext()) {
            context.getRegistry().bind("store", store);
            context.getRegistry().bind("model", model);
            context.addRoutes(IngestPipelineRouteBuilder.of(
                    IngestPipelineDefinition.directory("docs", directory.toString())));
            NotifyBuilder ingested = new NotifyBuilder(context).whenDone(2).create();

            context.start();

            assertThat(ingested.matches(10, TimeUnit.SECONDS)).isTrue();
            assertThat(documentIdsInStore(store, model, "Alpha document about camels."))
                    .contains("a.txt");
            assertThat(documentIdsInStore(store, model, "Beta document about routes."))
                    .contains("b.txt");

            // the safe file-consumer defaults are part of the topology contract
            String fileUri = context.getRoute("langchain4j-ingest-docs").getEndpoint().getEndpointUri();
            assertThat(fileUri).contains("noop=true").contains("readLock=changed").contains("charset=UTF-8");
        }
    }

    @Test
    void consumerPipelineRepliesAndDeduplicates() throws Exception {
        try (DefaultCamelContext context = contextWithBeans()) {
            context.addRoutes(IngestPipelineRouteBuilder.of(
                    IngestPipelineDefinition.consumer("events", "direct:events")
                            .documentId("MyKey")
                            .idempotentRepository("eventsRegister")
                            .idempotentRepositoryAutoCreate(true)));
            context.start();
            ProducerTemplate template = context.createProducerTemplate();

            IngestResult first = template.requestBodyAndHeader("direct:events", "some event payload",
                    "MyKey", "k-1", IngestResult.class);
            IngestResult second = template.requestBodyAndHeader("direct:events", "the same key again",
                    "MyKey", "k-1", IngestResult.class);

            assertThat(first.outcome()).isEqualTo(IngestResult.Outcome.INGESTED);
            assertThat(second.outcome()).isEqualTo(IngestResult.Outcome.SKIPPED);
            // auto-create bound the register under the configured name
            assertThat(context.getRegistry().lookupByNameAndType("eventsRegister", IdempotentRepository.class))
                    .isNotNull();
        }
    }

    /**
     * Also the instance-overload path: the registry holds no beans at all, the definition carries the store and model
     * objects — the wiring the camel-quarkus delegation uses for its CDI-resolved beans.
     */
    @Test
    void documentIdTakesASimpleExpression() throws Exception {
        try (DefaultCamelContext context = new DefaultCamelContext()) {
            context.addRoutes(IngestPipelineRouteBuilder.of(
                    IngestPipelineDefinition.consumer("combo", "direct:combo")
                            .documentId("${header.A}-${header.B}")
                            .embeddingStore(new InMemoryEmbeddingStore<TextSegment>())
                            .embeddingModel(new DeterministicEmbeddingModel(16))));
            context.start();
            ProducerTemplate template = context.createProducerTemplate();

            IngestResult result = template.request("direct:combo", exchange -> {
                exchange.getIn().setHeader("A", "x");
                exchange.getIn().setHeader("B", "y");
                exchange.getIn().setBody("combined identity");
            }).getMessage().getBody(IngestResult.class);

            assertThat(result.documentId()).isEqualTo("x-y");
        }
    }

    /**
     * The raw-payload guard sits before the parse: an oversized payload is rejected counting bytes — the endpoint's own
     * cap counts extracted characters and would only fire after tika had already paid for the parse, so the unit in the
     * message is the proof of where the rejection happened.
     */
    @Test
    void oversizedRawPayloadIsRejectedBeforeTheParse() throws Exception {
        try (DefaultCamelContext context = contextWithBeans()) {
            context.addRoutes(IngestPipelineRouteBuilder.of(
                    IngestPipelineDefinition.consumer("guarded", "direct:guarded")
                            .parser(IngestPipelineDefinition.Parser.TIKA)
                            .maxDocumentSize(64)));
            context.start();
            ProducerTemplate template = context.createProducerTemplate();

            String big = "<html><body>" + "x".repeat(100) + "</body></html>";
            assertThatThrownBy(() -> template.requestBodyAndHeader("direct:guarded", big,
                    LangChain4jIngestHeaders.DOCUMENT_ID, "too-big"))
                    .hasStackTraceContaining("exceeds maxDocumentSize")
                    .hasStackTraceContaining("bytes");

            IngestResult small = template.requestBodyAndHeader("direct:guarded",
                    "<html><body>small enough</body></html>",
                    LangChain4jIngestHeaders.DOCUMENT_ID, "small", IngestResult.class);
            assertThat(small.outcome()).isEqualTo(IngestResult.Outcome.INGESTED);
        }
    }

    /**
     * On a consumer pipeline every header arrives with the payload, so a forged {@code CamelFileLength: 1} must not
     * talk an oversized payload past the guard: the declared length is only trusted on a directory pipeline, whose own
     * file consumer set it.
     */
    @Test
    void aForgedLengthHeaderCannotBypassTheRawPayloadGuard() throws Exception {
        try (DefaultCamelContext context = contextWithBeans()) {
            context.addRoutes(IngestPipelineRouteBuilder.of(
                    IngestPipelineDefinition.consumer("spoofed", "direct:spoofed")
                            .parser(IngestPipelineDefinition.Parser.TIKA)
                            .maxDocumentSize(64)));
            context.start();
            ProducerTemplate template = context.createProducerTemplate();

            String big = "<html><body>" + "x".repeat(100) + "</body></html>";
            Exception failure = template.request("direct:spoofed", exchange -> {
                exchange.getIn().setHeader(org.apache.camel.Exchange.FILE_LENGTH, 1L);
                exchange.getIn().setHeader(LangChain4jIngestHeaders.DOCUMENT_ID, "forged-length");
                exchange.getIn().setBody(big);
            }).getException();

            assertThat(failure)
                    .isNotNull()
                    .hasMessageContaining("exceeds maxDocumentSize")
                    .hasMessageContaining("bytes");
        }
    }

    /**
     * Also stands in for the missing-parser hint: both real parsers (camel-tika, camel-docling) are test dependencies
     * of this module now, so no parser is genuinely absent here — the parser variant shares this requireComponent path
     * and differs only in the artifact name.
     */
    @Test
    void missingSourceComponentFailsNamingTheArtifact() throws Exception {
        try (DefaultCamelContext context = contextWithBeans()) {
            assertThatThrownBy(() -> context.addRoutes(IngestPipelineRouteBuilder.of(
                    IngestPipelineDefinition.consumer("events", "nope:whatever"))))
                    .hasStackTraceContaining("org.apache.camel:camel-nope");
        }
    }

    @Test
    void duplicatePipelineNamesAreRejected() throws Exception {
        try (DefaultCamelContext context = contextWithBeans()) {
            assertThatThrownBy(() -> context.addRoutes(IngestPipelineRouteBuilder.of(
                    IngestPipelineDefinition.directory("docs", "/tmp/a"),
                    IngestPipelineDefinition.consumer("docs", "direct:docs"))))
                    .hasStackTraceContaining("defined twice");
        }
    }

    private static DefaultCamelContext contextWithBeans() {
        DefaultCamelContext context = new DefaultCamelContext();
        context.getRegistry().bind("store", new InMemoryEmbeddingStore<TextSegment>());
        context.getRegistry().bind("model", new DeterministicEmbeddingModel(16));
        return context;
    }

    private static java.util.List<String> documentIdsInStore(
            InMemoryEmbeddingStore<TextSegment> store, DeterministicEmbeddingModel model, String query) {
        return store.search(EmbeddingSearchRequest.builder()
                .queryEmbedding(model.embed(query).content())
                .maxResults(100)
                .build()).matches().stream()
                .map(match -> match.embedded().metadata().getString(LangChain4jIngest.METADATA_DOCUMENT_ID))
                .toList();
    }
}
