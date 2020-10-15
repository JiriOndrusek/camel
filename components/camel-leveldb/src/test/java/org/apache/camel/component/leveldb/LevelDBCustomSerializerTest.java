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
package org.apache.camel.component.leveldb;

import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.BeforeEach;

import static org.apache.camel.test.junit5.TestSupport.deleteDirectory;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LevelDBCustomSerializerTest extends CamelTestSupport {

    @Override
    @BeforeEach
    public void setUp() throws Exception {
        deleteDirectory("target/data");
        super.setUp();
    }

    //    @Test
    public void testLevelDBAggregate() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:aggregated");
        mock.expectedBodiesReceived("ABCDE");
        for (int i = 0; i < 5; i++) {
            mock.message(i).header("test_customSerialization").isEqualTo(Boolean.TRUE);
        }

        template.sendBodyAndHeader("direct:start", "A", "id", 123);
        template.sendBodyAndHeader("direct:start", "B", "id", 123);
        template.sendBodyAndHeader("direct:start", "C", "id", 123);
        template.sendBodyAndHeader("direct:start", "D", "id", 123);
        template.sendBodyAndHeader("direct:start", "E", "id", 123);

        assertMockEndpointsSatisfied(30, TimeUnit.SECONDS);

        // from endpoint should be preserved
        assertEquals("direct://start", mock.getReceivedExchanges().get(0).getFromEndpoint().getEndpointUri());
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            // START SNIPPET: e1
            public void configure() throws Exception {
                // create the leveldb repo
                LevelDBAggregationRepository repo = new LevelDBAggregationRepository("repo1", "target/data/leveldb.dat");
                ObjectMapper om = createObjectMapper();

                //                repo.setSerializer(new LevelDBSerializer() {
                //                    @Override
                //                    public byte[] serialize(Object object) throws IOException {
                //                        if (object instanceof DefaultExchangeHolder) {
                //                            DefaultExchangeHolder.addProperty((DefaultExchangeHolder) object, "test_customSerialization",
                //                                    "true");
                //                        }
                //                        return om.writeValueAsBytes(object);
                //                    }
                //
                //                    @Override
                //                    public Object deserialize(byte[] buffer) throws IOException, ClassNotFoundException {
                //                        return om.readValue(buffer, DefaultExchangeHolder.class);
                //                    }
                //                });

                // here is the Camel route where we aggregate
                from("direct:start")
                        .aggregate(header("id"), new MyAggregationStrategy())
                        // use our created leveldb repo as aggregation repository
                        .completionSize(5).aggregationRepository(repo)
                        .to("mock:aggregated");
            }
            // END SNIPPET: e1
        };
    }

    public static class MyAggregationStrategy implements AggregationStrategy {

        @Override
        public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
            if (oldExchange == null) {
                return newExchange;
            }
            String body1 = oldExchange.getIn().getBody(String.class);
            String body2 = newExchange.getIn().getBody(String.class);

            oldExchange.getIn().setBody(body1 + body2);
            return oldExchange;
        }
    }

    public static ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        SimpleModule module = new SimpleModule();
        module.addSerializer(Long.class, ToStringSerializer.instance);
        module.addSerializer(long.class, ToStringSerializer.instance);
        objectMapper.registerModule(module);

        return objectMapper;
    }
}
