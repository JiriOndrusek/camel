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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.ExtendedExchange;
import org.apache.camel.support.DefaultExchange;
import org.apache.camel.support.DefaultExchangeHolder;

public final class LevelDBCamelCodec {

    private final ObjectMapper objectMapper;

    public LevelDBCamelCodec(Module module) {
        objectMapper = new ObjectMapper();

        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

//        SimpleModule simpleModule = new SimpleModule();
//        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
//        simpleModule.addSerializer(long.class, ToStringSerializer.instance);
//
//        objectMapper.registerModule(simpleModule);
        if(module != null) {
            objectMapper.registerModule(module);
        }
    }

    public byte[] marshallKey(String key) throws IOException {
        return objectMapper.writeValueAsBytes(key);
    }

    public String unmarshallKey(byte[] buffer) throws IOException {
        return objectMapper.readValue(buffer, String.class);
    }

    public byte[] marshallExchange(CamelContext camelContext, Exchange exchange, boolean allowSerializedHeaders)
            throws IOException {

        Object inBody = exchange.getIn().getBody();
        Object outBody = null;
        if (exchange.getMessage() != null) {
            outBody = exchange.getMessage().getBody();
        }

        // use DefaultExchangeHolder to marshal to a serialized object
        DefaultExchangeHolder pe = DefaultExchangeHolder.marshal(exchange, false, allowSerializedHeaders);
        // add the aggregated size and timeout property as the only properties we want to retain
        DefaultExchangeHolder.addProperty(pe, Exchange.AGGREGATED_SIZE,
                exchange.getProperty(Exchange.AGGREGATED_SIZE, Integer.class));
        DefaultExchangeHolder.addProperty(pe, Exchange.AGGREGATED_TIMEOUT,
                exchange.getProperty(Exchange.AGGREGATED_TIMEOUT, Long.class));
        // add the aggregated completed by property to retain
        DefaultExchangeHolder.addProperty(pe, Exchange.AGGREGATED_COMPLETED_BY,
                exchange.getProperty(Exchange.AGGREGATED_COMPLETED_BY, String.class));
        // add the aggregated correlation key property to retain
        DefaultExchangeHolder.addProperty(pe, Exchange.AGGREGATED_CORRELATION_KEY,
                exchange.getProperty(Exchange.AGGREGATED_CORRELATION_KEY, String.class));
        // and a guard property if using the flexible toolbox aggregator
        DefaultExchangeHolder.addProperty(pe, Exchange.AGGREGATED_COLLECTION_GUARD,
                exchange.getProperty(Exchange.AGGREGATED_COLLECTION_GUARD, String.class));
        // persist the from endpoint as well
        if (exchange.getFromEndpoint() != null) {
            DefaultExchangeHolder.addProperty(pe, "CamelAggregatedFromEndpoint", exchange.getFromEndpoint().getEndpointUri());
        }

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream(); DataOutputStream dos = new DataOutputStream(baos)) {
            serializeByteArray(inBody, dos);
            serializeByteArray(outBody, dos);
            objectMapper.writeValue(baos, pe);
            System.out.println(objectMapper.writeValueAsString(pe));
            byte[] b = baos.toByteArray();
            return b;
        }
    }

    private void serializeByteArray(Object body, DataOutputStream dos) throws IOException {
        if (body instanceof byte[]) {
            int length = ((byte[]) body).length;
            ByteBuffer bb = ByteBuffer.allocate(4);
            bb.putInt(length);
            dos.write(bb.array());
            dos.write((byte[]) body);
        } else {
            ByteBuffer bb = ByteBuffer.allocate(4);
            bb.putInt(0);
            dos.write(bb.array());
        }
    }

    public Exchange unmarshallExchange(CamelContext camelContext, byte[] buffer) throws IOException {
        Object inBody;
        Object outBody;
        DefaultExchangeHolder pe;
        try (ByteArrayInputStream bis = new ByteArrayInputStream(buffer); DataInputStream dis = new DataInputStream(bis)) {
            inBody = deserializeByteArray(dis);
            outBody = deserializeByteArray(dis);
            pe = objectMapper.readValue(bis, DefaultExchangeHolder.class);
        }

        Exchange answer = new DefaultExchange(camelContext);
        DefaultExchangeHolder.unmarshal(answer, pe);
        // restore the from endpoint
        String fromEndpointUri = (String) answer.removeProperty("CamelAggregatedFromEndpoint");
        if (fromEndpointUri != null) {
            Endpoint fromEndpoint = camelContext.hasEndpoint(fromEndpointUri);
            if (fromEndpoint != null) {
                answer.adapt(ExtendedExchange.class).setFromEndpoint(fromEndpoint);
            }
        }

        if (inBody != null) {
            answer.getIn().setBody(inBody);
        }
        if (outBody != null) {
            answer.getMessage().setBody(outBody);
        }

        return answer;
    }

    private Object deserializeByteArray(DataInputStream dis) throws IOException {
        byte[] b = new byte[4];
        dis.read(b);
        int length = ByteBuffer.wrap(b).getInt();
        byte[] payload = null;
        if (length > 0) {
            payload = new byte[length];
            dis.read(payload);
        }

        return payload;
    }
}
