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
package org.apache.camel.component.aws2.sqs.integration;

import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.aws2.sqs.AmazonSQSClientMock;
import org.apache.camel.component.aws2.sqs.Sqs2Component;
import org.apache.camel.component.aws2.sqs.Sqs2Constants;
import org.apache.camel.component.aws2.sqs.Sqs2Endpoint;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SqsDeadletterWithClientRegistryLocalstackIT extends Aws2SQSBaseTest {

    @EndpointInject("direct:start")
    private ProducerTemplate template;

    @EndpointInject("mock:result")
    private MockEndpoint result;

    @Override
    protected CamelContext createCamelContext() throws Exception {
        CamelContext ctx = super.createCamelContext();
        AmazonSQSClientMock awsSQSClient = new AmazonSQSClientMock();


        Sqs2Component sqs = ctx.getComponent("aws2-sqs", Sqs2Component.class);

        //todo if those 2 lines are enabled, test is successful, becouse it will use client from context
        sqs.getConfiguration().setAmazonSQSClient(null);
        ctx.getRegistry().bind("awsSQSClient", awsSQSClient);

        return ctx;

    }

    @Test
    public void deadletter() throws Exception {
        result.expectedMessageCount(1);

        Exchange exchange = template.send("direct:start", ExchangePattern.InOnly, new Processor() {
            public void process(Exchange exchange) throws Exception {
                exchange.getIn().setBody("test1");
            }
        });

        assertMockEndpointsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {

        //todo use generated query name for usage with real aws provider
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                errorHandler(deadLetterChannel("aws2-sqs://deadletter?autoCreateQueue=true")
                        .useOriginalMessage());

                from("direct:start").startupOrder(2).process(e -> {throw new IllegalStateException();}).toF("aws2-sqs://%s?autoCreateQueue=true", sharedNameGenerator.getName());

                from("aws2-sqs://deadletter").to("mock:result");
            }
        };
    }
}
