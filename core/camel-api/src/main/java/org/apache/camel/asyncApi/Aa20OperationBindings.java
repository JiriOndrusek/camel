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
package org.apache.camel.asyncApi;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Map describing protocol-specific definitions for an operation.
 */
public class Aa20OperationBindings extends LinkedHashMap<Aa20OperationBindings.type, Object> {

    public enum type {
        http,
        ws,
        kafka,
        amqp,
        amqp1,
        mqtt,
        mqtt5,
        nats,
        jms,
        sns,
        sqs,
        stomp,
        redis;
    }
}
