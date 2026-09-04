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

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.DefaultExchange;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TikaTextDecodeTest {

    private final TikaTextDecode decode = new TikaTextDecode();

    /**
     * The reason this step exists: Tika copies the parsed document's metadata over the exchange headers, including its
     * Content-Type — so a document declaring another charset could steer the exchange charset heuristic and mangle its
     * own extracted text. The decode reads raw bytes and applies the pinned UTF-8 regardless.
     */
    @Test
    void decodesThePinnedUtf8DespiteADocumentSteeredContentType() throws Exception {
        Exchange exchange = new DefaultExchange(new DefaultCamelContext());
        ByteArrayOutputStream tikaOutput = new ByteArrayOutputStream();
        tikaOutput.writeBytes("\nCafé — señor Piña\n".getBytes(StandardCharsets.UTF_8));
        exchange.getMessage().setBody(tikaOutput);
        // what a crafted document's own metadata, copied by Tika, could claim
        exchange.getMessage().setHeader(Exchange.CONTENT_TYPE, "text/html; charset=ISO-8859-1");

        decode.process(exchange);

        assertThat(exchange.getMessage().getBody(String.class)).isEqualTo("Café — señor Piña");
    }

    /** A parse yielding no body becomes blank text — the EMPTY outcome downstream — not a NullPointerException. */
    @Test
    void aNullParseResultBecomesBlankText() {
        Exchange exchange = new DefaultExchange(new DefaultCamelContext());
        exchange.getMessage().setBody(null);

        decode.process(exchange);

        assertThat(exchange.getMessage().getBody(String.class)).isEmpty();
    }
}
