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
package org.apache.camel.asyncapi.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.camel.asyncApi.Aa20SecurityRequirement;
import org.apache.camel.asyncApi.Aa20ServerBindings;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Aa20ServerBindingsImpl implements Aa20ServerBindings {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, Object> http;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, Object> ws;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, Object> kafka;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, Object> amqp;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, Object> amqp1;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, Object> mqtt;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, Object> mqtt5;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, Object> nats;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, Object> jms;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, Object> sns;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, Object> sqs;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, Object> stomp;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, Object> redis;

    public static Aa20ServerBindingsImpl.Builder newBuilder(Aa20ServerImpl.Builder parent, Consumer<Aa20ServerBindings> consumer) {
        return new Aa20ServerBindingsImpl.Builder(parent, consumer);
    }

    @Override
    public Map<String, Object> getHttp() {
        return http;
    }

    public void setHttp(Map<String, Object> http) {
        this.http = http;
    }

    @Override
    public Map<String, Object> getWs() {
        return ws;
    }

    public void setWs(Map<String, Object> ws) {
        this.ws = ws;
    }

    @Override
    public Map<String, Object> getKafka() {
        return kafka;
    }

    public void setKafka(Map<String, Object> kafka) {
        this.kafka = kafka;
    }

    @Override
    public Map<String, Object> getAmqp() {
        return amqp;
    }

    public void setAmqp(Map<String, Object> amqp) {
        this.amqp = amqp;
    }

    @Override
    public Map<String, Object> getAmqp1() {
        return amqp1;
    }

    public void setAmqp1(Map<String, Object> amqp1) {
        this.amqp1 = amqp1;
    }

    @Override
    public Map<String, Object> getMqtt() {
        return mqtt;
    }

    public void setMqtt(Map<String, Object> mqtt) {
        this.mqtt = mqtt;
    }

    @Override
    public Map<String, Object> getMqtt5() {
        return mqtt5;
    }

    public void setMqtt5(Map<String, Object> mqtt5) {
        this.mqtt5 = mqtt5;
    }

    @Override
    public Map<String, Object> getNats() {
        return nats;
    }

    public void setNats(Map<String, Object> nats) {
        this.nats = nats;
    }

    @Override
    public Map<String, Object> getJms() {
        return jms;
    }

    public void setJms(Map<String, Object> jms) {
        this.jms = jms;
    }

    @Override
    public Map<String, Object> getSns() {
        return sns;
    }

    public void setSns(Map<String, Object> sns) {
        this.sns = sns;
    }

    @Override
    public Map<String, Object> getSqs() {
        return sqs;
    }

    public void setSqs(Map<String, Object> sqs) {
        this.sqs = sqs;
    }

    @Override
    public Map<String, Object> getStomp() {
        return stomp;
    }

    public void setStomp(Map<String, Object> stomp) {
        this.stomp = stomp;
    }

    @Override
    public Map<String, Object> getRedis() {
        return redis;
    }

    public void setRedis(Map<String, Object> redis) {
        this.redis = redis;
    }
// --------------------------------------- builder ---------------------------------------------------------

    public static class Builder extends NestedBuilder<Aa20ServerImpl.Builder, Aa20ServerBindings> {
        private Map<String, Object> http;
        private Map<String, Object> ws;
        private Map<String, Object> kafka;
        private Map<String, Object> amqp;
        private Map<String, Object> amqp1;
        private Map<String, Object> mqtt;
        private Map<String, Object> mqtt5;
        private Map<String, Object> nats;
        private Map<String, Object> jms;
        private Map<String, Object> sns;
        private Map<String, Object> sqs;
        private Map<String, Object> stomp;
        private Map<String, Object> redis;

        public Builder(Aa20ServerImpl.Builder parent, Consumer<Aa20ServerBindings> consumer) {
            super(parent, consumer);
        }

        public Builder withHttp() {
            if(http == null) {
                http = new HashMap<>();
            }
            return this;
        }

        public Builder withHttpSchema(String schema) {
            if(http == null) {
                http = new HashMap<>();
            }
            //todo use builder for json
            http.put(schema, "todo");
            return this;
        }

        public Builder withWs() {
            if(ws == null) {
                ws = new HashMap<>();
            }
            return this;
        }

        public Builder withWsSchema(String schema) {
            if(ws == null) {
                ws = new HashMap<>();
            }
            //todo use builder for json
            ws.put(schema, "todo");
            return this;
        }


        @Override
        public Aa20ServerBindings build() {
            Aa20ServerBindingsImpl bindings = new Aa20ServerBindingsImpl();
            bindings.setHttp(this.http);
            bindings.setWs(this.ws);
            return bindings;
        }
    }
}
