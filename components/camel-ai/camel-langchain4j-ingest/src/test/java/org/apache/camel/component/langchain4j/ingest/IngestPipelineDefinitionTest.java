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

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class IngestPipelineDefinitionTest {

    @Test
    void nameMustBeUriSafe() {
        assertThatThrownBy(() -> IngestPipelineDefinition.directory("my pipe", "/tmp/docs"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("letters, digits");
    }

    @Test
    void directoryMustNotCarryUriSyntax() {
        assertThatThrownBy(() -> IngestPipelineDefinition.directory("docs", "/tmp/docs?delete=true"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("must not contain");
    }

    @Test
    void consumerUriMustHaveAScheme() {
        assertThatThrownBy(() -> IngestPipelineDefinition.consumer("events", "not-a-uri"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("not a consumer URI");
    }

    @Test
    void typedParserOverloadIsAccepted() {
        IngestPipelineDefinition definition = IngestPipelineDefinition.directory("docs", "/tmp/docs")
                .parser(IngestPipelineDefinition.Parser.TIKA);

        org.assertj.core.api.Assertions.assertThat(definition.parser())
                .isEqualTo(IngestPipelineDefinition.Parser.TIKA);
    }

    @Test
    void nullTypedParserIsRejected() {
        assertThatThrownBy(() -> IngestPipelineDefinition.directory("docs", "/tmp/docs")
                .parser((IngestPipelineDefinition.Parser) null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("must not be null");
    }

    @Test
    void unknownParserIsRejectedWithTheChoices() {
        assertThatThrownBy(() -> IngestPipelineDefinition.directory("docs", "/tmp/docs").parser("pdfbox"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("tika")
                .hasMessageContaining("docling")
                .hasMessageContaining("pdfbox");
    }

    @Test
    void blankDocumentSplitterReferenceIsRejected() {
        assertThatThrownBy(() -> IngestPipelineDefinition.directory("docs", "/tmp/docs").documentSplitter(" "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("documentSplitter must not be null or blank");
    }

    @Test
    void zeroEmbeddingBatchSizeIsRejectedEarly() {
        assertThatThrownBy(() -> IngestPipelineDefinition.directory("docs", "/tmp/docs").embeddingBatchSize(0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("embeddingBatchSize must be positive");
    }

    @Test
    void zeroMaxDocumentSizeIsRejectedEarly() {
        assertThatThrownBy(() -> IngestPipelineDefinition.directory("docs", "/tmp/docs").maxDocumentSize(0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("maxDocumentSize must be positive");
    }

    @Test
    void splitterBoundsAreRejectedEarly() {
        assertThatThrownBy(() -> IngestPipelineDefinition.directory("docs", "/tmp/docs").splitter(100, 100))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("smaller than it");
    }
}
