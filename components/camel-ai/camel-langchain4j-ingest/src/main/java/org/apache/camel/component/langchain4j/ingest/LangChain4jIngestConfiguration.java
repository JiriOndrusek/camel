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
import dev.langchain4j.store.embedding.EmbeddingStore;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.component.langchain4j.embeddingstore.EmbeddingStoreFactory;
import org.apache.camel.spi.Configurer;
import org.apache.camel.spi.Metadata;
import org.apache.camel.spi.UriParam;
import org.apache.camel.spi.UriParams;

/**
 * Configuration for the LangChain4j ingest component.
 *
 * <p>
 * Either {@code embeddingStore} or {@code embeddingStoreFactory} should be configured, with the factory taking
 * precedence if both are present. The {@code embeddingModel} is always required.
 * </p>
 */
@Configurer
@UriParams
public class LangChain4jIngestConfiguration implements Cloneable {

    @Metadata(autowired = true)
    @UriParam(description = "The embedding store the ingested segments are written to")
    private EmbeddingStore<TextSegment> embeddingStore;

    @UriParam(description = "The embedding store factory to use for creating the embedding store if no embeddingStore is provided")
    @Metadata(autowired = true)
    private EmbeddingStoreFactory embeddingStoreFactory;

    @Metadata(required = true, autowired = true)
    @UriParam(description = "The embedding model used to embed the document segments")
    private EmbeddingModel embeddingModel;

    @UriParam(description = "Maximum size of a segment in characters",
              defaultValue = LangChain4jIngest.DEFAULT_MAX_SEGMENT_SIZE)
    private int maxSegmentSize = 500;

    @UriParam(description = "Number of characters adjacent segments share; must be smaller than maxSegmentSize",
              defaultValue = LangChain4jIngest.DEFAULT_MAX_OVERLAP_SIZE)
    private int maxOverlapSize = 50;

    public EmbeddingStore<TextSegment> getEmbeddingStore() {
        return embeddingStore;
    }

    /**
     * Sets the embedding store to use
     */
    public void setEmbeddingStore(EmbeddingStore<TextSegment> embeddingStore) {
        this.embeddingStore = embeddingStore;
    }

    public EmbeddingStoreFactory getEmbeddingStoreFactory() {
        return embeddingStoreFactory;
    }

    public void setEmbeddingStoreFactory(EmbeddingStoreFactory embeddingStoreFactory) {
        this.embeddingStoreFactory = embeddingStoreFactory;
    }

    public EmbeddingModel getEmbeddingModel() {
        return embeddingModel;
    }

    public void setEmbeddingModel(EmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }

    public int getMaxSegmentSize() {
        return maxSegmentSize;
    }

    public void setMaxSegmentSize(int maxSegmentSize) {
        this.maxSegmentSize = maxSegmentSize;
    }

    public int getMaxOverlapSize() {
        return maxOverlapSize;
    }

    public void setMaxOverlapSize(int maxOverlapSize) {
        this.maxOverlapSize = maxOverlapSize;
    }

    // ************************
    //
    // Clone
    //
    // ************************

    public LangChain4jIngestConfiguration copy() {
        try {
            return (LangChain4jIngestConfiguration) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeCamelException(e);
        }
    }
}
