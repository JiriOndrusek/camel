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

/**
 * Constants for the LangChain4j ingest component.
 *
 * <p>
 * This class defines the component scheme, default splitter values and the metadata keys stamped into every stored
 * segment.
 * </p>
 */
public class LangChain4jIngest {
    /** Component URI scheme for ingest endpoints */
    public static final String SCHEME = "langchain4j-ingest";

    /** Default maximum size of a segment in characters */
    public static final String DEFAULT_MAX_SEGMENT_SIZE = "500";

    /** Default number of characters adjacent segments share */
    public static final String DEFAULT_MAX_OVERLAP_SIZE = "50";

    /**
     * Segments are embedded in batches of this size: a single embedAll over a large document's full segment list can
     * exceed an embedding provider's per-request limits
     */
    public static final int EMBEDDING_BATCH_SIZE = 32;

    /** Metadata key carrying the name of the ingestion pipeline that wrote the segment */
    public static final String METADATA_PIPELINE = "camel_ingest_pipeline";

    /** Metadata key carrying the id of the document the segment was split from */
    public static final String METADATA_DOCUMENT_ID = "camel_ingest_document_id";

    private LangChain4jIngest() {
    }
}
