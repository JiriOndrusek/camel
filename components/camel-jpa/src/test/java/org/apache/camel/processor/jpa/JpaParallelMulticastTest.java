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
package org.apache.camel.processor.jpa;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.examples.SendEmail;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JpaParallelMulticastTest extends AbstractJpaTest {
    protected static final String SELECT_ALL_STRING = "select x from " + SendEmail.class.getName() + " x";
    private static final int COUNT = 6;

    @Test
    public void testMulticastParallel() throws Exception {
        Future<Object> future = template.asyncRequestBody("direct:multicast", "wrong@wrong.org");

        future.get(10, TimeUnit.SECONDS);

        assertCorrectEmails();
    }

    private void assertCorrectEmails() {
        List<?> results = entityManager
                //                .createQuery("select e from " + SendEmail.class.getName() + " e WHERE e.address = 'something@correct.org'")
                .createQuery("select e from " + SendEmail.class.getName() + " e")
                .getResultList();
        assertEquals(COUNT, results.size());
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                from("direct:multicast")
                        .loop(COUNT)
                        .setHeader("loopIndex", exchangeProperty(Exchange.LOOP_INDEX))
                        .process(ex -> {
                            int index = ex.getIn().getHeader("loopIndex", Integer.class);
                            SendEmail se = new SendEmail(index + "@wrong.org");
                            ex.getIn().setBody(se);
                        })
                        .to("jpa://" + SendEmail.class.getName())
                        .end()
                        //select all
                        .to("jpa://" + SendEmail.class.getName() + "?query=SELECT e FROM SendEmail e")
                        .split(body())
                        .aggregate(constant("1"), new AggregationStrategy() {
                            @Override
                            public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
                                //                                System.out.println(newExchange.getIn().getBody());
                                if (oldExchange == null) {
                                    return newExchange;
                                }
                                SendEmail body1 = oldExchange.getIn().getBody(SendEmail.class);
                                SendEmail body2 = newExchange.getIn().getBody(SendEmail.class);
                                oldExchange.getIn().setBody(Arrays.asList(new SendEmail[] { body1, body2 }));
                                //                                System.out.println("aggregated :" + body1 + ", " + body2);
                                return oldExchange;
                            }
                        })
                        .completionSize(2)
                        .completionTimeout(1000)
                        .log("multicast")
                        .multicast().parallelProcessing().to("direct:a", "direct:b")
                        // use end to indicate end of multicast route
                        .end().to("mock:result");

                process("direct:a", 0);
                process("direct:b", 1);
            }

            private void process(String uri, int index) {
                from(uri)
                        .process(ex -> {
                            //                            System.out.println("processing " + ex.getIn().getBody());
                            Object o = ex.getIn().getBody();
                            if (o instanceof List<?>) {
                                SendEmail se = (SendEmail) ((List<?>) o).get(index);
                                ex.getIn().setBody(se);
                                se.setAddress("something@correct.org");
                            }
                            System.out.println("Updating:" + ex.getIn().getBody());
                        })
                        .to("jpa://" + SendEmail.class.getName());
            }
        };
    }

    @Override
    protected String routeXml() {
        return "org/apache/camel/processor/jpa/springJpaRouteTest.xml";
    }

    @Override
    protected String selectAllString() {
        return SELECT_ALL_STRING;
    }
}
