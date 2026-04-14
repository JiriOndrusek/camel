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
package org.apache.camel.telemetry;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;

/**
 * This interface represents a decorator specific to the component/endpoint being instrumented.
 */
public interface SpanDecorator {

    void beforeTracingEvent(Span span, Exchange exchange, Endpoint endpoint);

    void afterTracingEvent(Span span, Exchange exchange);

    String getComponent();

    String getComponentClassName();

    String getOperationName(Exchange exchange, Endpoint endpoint);

    SpanContextPropagationExtractor getExtractor(Exchange exchange);

    SpanContextPropagationInjector getInjector(Exchange exchange);

    /**
     * Returns the span kind for the given operation.
     * <p>
     * The span kind indicates the role of the span in a distributed trace. Possible values:
     * <ul>
     * <li>"INTERNAL" - default, for local operations</li>
     * <li>"CLIENT" - for outgoing HTTP requests</li>
     * <li>"SERVER" - for incoming HTTP requests</li>
     * <li>"PRODUCER" - for message producers</li>
     * <li>"CONSUMER" - for message consumers</li>
     * </ul>
     *
     * @param  operation The operation type (EVENT_SENT, EVENT_RECEIVED, EVENT_PROCESS)
     * @return           The span kind as a string
     */
    default String getSpanKind(Op operation) {
        return "INTERNAL";
    }

}
