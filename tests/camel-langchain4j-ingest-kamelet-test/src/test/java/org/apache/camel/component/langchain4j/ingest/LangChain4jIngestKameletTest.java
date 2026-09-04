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
import java.util.List;
import java.util.concurrent.TimeUnit;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.apache.camel.BindToRegistry;
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit6.CamelTestSupport;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The kamelets shipped in the jar under {@code classpath:kamelets} — the declarative twins of the
 * {@link IngestPipelineRouteBuilder} topology.
 */
class LangChain4jIngestKameletTest extends CamelTestSupport {

    @TempDir
    static Path directory;

    @BindToRegistry("store")
    private final InMemoryEmbeddingStore<TextSegment> store = new InMemoryEmbeddingStore<>();

    @BindToRegistry("model")
    private final DeterministicEmbeddingModel model = new DeterministicEmbeddingModel(16);

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                from("direct:in")
                        .to("kamelet:langchain4j-ingest-sink?documentIdHeader=MyKey");
                from("kamelet:langchain4j-ingest-file-source?directory=" + directory)
                        .routeId("files-pipe")
                        .to("kamelet:langchain4j-ingest-sink?pipelineName=files");
            }
        };
    }

    @Test
    void sinkKameletIngests() {
        IngestResult result = template.requestBodyAndHeader("direct:in", "hello from the kamelet",
                "MyKey", "k-1", IngestResult.class);

        assertThat(result.outcome()).isEqualTo(IngestResult.Outcome.INGESTED);
        assertThat(result.pipeline()).isEqualTo("ingest");
        assertThat(result.documentId()).isEqualTo("k-1");
    }

    @Test
    void fileSourceAndSinkFormAPipelineWithoutRouteCode() throws Exception {
        NotifyBuilder ingested = new NotifyBuilder(context).fromRoute("files-pipe").whenDone(1).create();
        Files.writeString(directory.resolve("notes.txt"), "Notes about the ingestion kamelets.");

        assertThat(ingested.matches(10, TimeUnit.SECONDS)).isTrue();

        List<String> documentIds = store.search(EmbeddingSearchRequest.builder()
                .queryEmbedding(model.embed("Notes about the ingestion kamelets.").content())
                .maxResults(100)
                .build()).matches().stream()
                .map(match -> match.embedded().metadata().getString(LangChain4jIngest.METADATA_DOCUMENT_ID))
                .toList();
        assertThat(documentIds).contains("notes.txt");
    }
}
