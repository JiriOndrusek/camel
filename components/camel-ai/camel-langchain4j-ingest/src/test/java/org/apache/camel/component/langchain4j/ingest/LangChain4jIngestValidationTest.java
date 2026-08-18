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

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.allminilml6v2.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * The producer refuses to start with an unusable configuration; the messages name what is missing or wrong.
 */
class LangChain4jIngestValidationTest {

    private static final EmbeddingModel EMBEDDING_MODEL = new AllMiniLmL6V2EmbeddingModel();

    @Test
    void overlapNotSmallerThanSegmentSizeIsRejected() {
        assertThatThrownBy(() -> start("langchain4j-ingest:bad?maxSegmentSize=100&maxOverlapSize=100", true, true))
                .hasStackTraceContaining("maxSegmentSize must be positive and maxOverlapSize must be smaller");
    }

    @Test
    void nonPositiveSegmentSizeIsRejected() {
        assertThatThrownBy(() -> start("langchain4j-ingest:bad?maxSegmentSize=0&maxOverlapSize=0", true, true))
                .hasStackTraceContaining("maxSegmentSize must be positive");
    }

    @Test
    void missingEmbeddingStoreIsRejected() {
        assertThatThrownBy(() -> start("langchain4j-ingest:nostore", false, true))
                .hasStackTraceContaining("no embeddingStore or embeddingStoreFactory");
    }

    @Test
    void missingEmbeddingModelIsRejected() {
        assertThatThrownBy(() -> start("langchain4j-ingest:nomodel", true, false))
                .hasStackTraceContaining("embeddingModel");
    }

    private void start(String uri, boolean withStore, boolean withModel) throws Exception {
        try (DefaultCamelContext context = new DefaultCamelContext()) {
            LangChain4jIngestComponent component = new LangChain4jIngestComponent();
            if (withStore) {
                component.getConfiguration().setEmbeddingStore(new InMemoryEmbeddingStore<TextSegment>());
            }
            if (withModel) {
                component.getConfiguration().setEmbeddingModel(EMBEDDING_MODEL);
            }
            context.addComponent(LangChain4jIngest.SCHEME, component);
            context.addRoutes(new RouteBuilder() {
                @Override
                public void configure() {
                    from("direct:in").to(uri);
                }
            });
            context.start();
        }
    }
}
