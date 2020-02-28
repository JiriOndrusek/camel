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
package org.apache.camel.asyncapi;

import org.apache.camel.Exchange;

import java.io.IOException;

//todo
public class AsyncApiResponseAdapter implements RestApiResponseAdapter {

    private final Exchange exchange;

    public AsyncApiResponseAdapter(Exchange exchange) {
        this.exchange = exchange;
    }

    @Override
    public void setHeader(String name, String value) {
        exchange.getIn().setHeader(name, value);
    }

    @Override
    public void writeBytes(byte[] bytes) throws IOException {
        exchange.getIn().setBody(bytes);
    }

    @Override
    public void noContent() {
        exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, 204);
    }
}
