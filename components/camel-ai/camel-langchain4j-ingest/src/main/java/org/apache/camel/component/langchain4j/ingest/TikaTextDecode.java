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

import org.apache.camel.Exchange;
import org.apache.camel.Experimental;
import org.apache.camel.Processor;

/**
 * Decodes the bytes of Tika's plain-text output as the UTF-8 the parse step pinned with
 * {@code tikaParseOutputEncoding}. This step exists because a plain {@code getBody(String.class)} would go through the
 * exchange charset heuristic — and Tika copies the parsed document's own metadata over the exchange headers, including
 * its {@code Content-Type}, so a crafted document declaring another charset could steer the decode and mangle its own
 * extracted text. Reading the raw bytes and decoding as the pinned charset closes that door.
 */
@Experimental
public class TikaTextDecode implements Processor {

    @Override
    public void process(Exchange exchange) {
        byte[] text = exchange.getMessage().getBody(byte[].class);
        // a parse yielding no body flows on as blank text and becomes the EMPTY outcome,
        // instead of an opaque NullPointerException here
        exchange.getMessage().setBody(text == null ? "" : new String(text, StandardCharsets.UTF_8).strip());
    }
}
