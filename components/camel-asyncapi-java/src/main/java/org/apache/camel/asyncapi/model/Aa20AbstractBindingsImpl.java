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
import org.apache.camel.asyncApi.Aa20AbstractBindings;
import org.apache.camel.asyncApi.Aa20ServerBindings;

import java.util.HashMap;
import java.util.Map;

public class Aa20AbstractBindingsImpl implements Aa20AbstractBindings {

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

    private String $ref;

    protected Aa20AbstractBindingsImpl() {
    }

    protected Aa20AbstractBindingsImpl(AbstractBindingsBuilder b) {
        this.http = b.http;
        this.ws = b.ws;
        this.kafka = b.kafka;
        this.amqp = b.amqp;
        this.amqp1 = b.amqp1;
        this.mqtt = b.mqtt;
        this.mqtt5 = b.mqtt5;
        this.nats = b.nats;
        this.jms = b.jms;
        this.sns = b.sns;
        this.sqs = b. sqs;
        this.stomp = b.stomp;
        this.redis = b.redis;
        this.$ref = b.$ref;
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

    @Override
    public String get$ref() {
        return $ref;
    }

    public void set$ref(String $ref) {
        this.$ref = $ref;
    }

    // --------------------------------------- builder ---------------------------------------------------------

    public abstract static class AbstractBindingsBuilder<B extends AbstractBindingsBuilder, T extends Aa20AbstractBindings> extends AbstractBuilder<T> {
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
        private String $ref;

        public AbstractBindingsBuilder() {
        }

        public B withHttp() {
            if(http == null) {
                http = new HashMap<>();
            }
            return (B)this;
        }

        public B withHttpSchema(String schema) {
            if(http == null) {
                http = new HashMap<>();
            }
            //todo use builder for json
            http.put(schema, "todo");
            return (B)this;
        }

        public B withWs() {
            if(ws == null) {
                ws = new HashMap<>();
            }
            return (B)this;
        }

        public B withWsSchema(String schema) {
            if(ws == null) {
                ws = new HashMap<>();
            }
            //todo use builder for json
            ws.put(schema, "todo");
            return (B)this;
        }

        public B withAmqp1() {
            if(amqp1 == null) {
                amqp1 = new HashMap<>();
            }
            return (B)this;
        }

        public B withAmqp1Schema(String schema) {
            if(amqp1 == null) {
                amqp1 = new HashMap<>();
            }
            //todo use builder for json
            amqp1.put(schema, "todo");
            return (B)this;
        }

        public B withKafka() {
            if(kafka == null) {
                kafka = new HashMap<>();
            }
            return (B)this;
        }

        public B withKafkaSchema(String schema, Object o) {
            if(kafka == null) {
                kafka = new HashMap<>();
            }
            //todo use builder for json
            kafka.put(schema, o);
            return (B)this;
        }

        public B withAmqp() {
            if(amqp == null) {
                amqp = new HashMap<>();
            }
            return (B)this;
        }

        public B withAmqpSchema(String schema) {
            if(amqp == null) {
                amqp = new HashMap<>();
            }
            //todo use builder for json
            amqp.put(schema, "todo");
            return (B)this;
        }

        public B withMqtt() {
            if(mqtt == null) {
                mqtt = new HashMap<>();
            }
            return (B)this;
        }

        public B withMqttSchema(String schema) {
            if(mqtt == null) {
                mqtt = new HashMap<>();
            }
            //todo use builder for json
            mqtt.put(schema, "todo");
            return (B)this;
        }

        public B withMqtt5() {
            if(mqtt5 == null) {
                mqtt5 = new HashMap<>();
            }
            return (B)this;
        }

        public B withMqtt5Schema(String schema) {
            if(mqtt5 == null) {
                mqtt5 = new HashMap<>();
            }
            //todo use builder for json
            mqtt5.put(schema, "todo");
            return (B)this;
        }

        public B withNats() {
            if(nats == null) {
                nats = new HashMap<>();
            }
            return (B)this;
        }

        public B withNatsSchema(String schema) {
            if(nats == null) {
                nats = new HashMap<>();
            }
            //todo use builder for json
            ws.put(schema, "todo");
            return (B)this;
        }

        public B withJms() {
            if(jms == null) {
                jms = new HashMap<>();
            }
            return (B)this;
        }

        public B withJmsSchema(String schema) {
            if(jms == null) {
                jms = new HashMap<>();
            }
            //todo use builder for json
            jms.put(schema, "todo");
            return (B)this;
        }

        public B witSns() {
            if(sns == null) {
                sns = new HashMap<>();
            }
            return (B)this;
        }

        public B withSnsSchema(String schema) {
            if(sns == null) {
                sns = new HashMap<>();
            }
            //todo use builder for json
            sns.put(schema, "todo");
            return (B)this;
        }

        public B withSqs() {
            if(sqs == null) {
                sqs = new HashMap<>();
            }
            return (B)this;
        }

        public B withSqsSchema(String schema) {
            if(sqs == null) {
                sqs = new HashMap<>();
            }
            //todo use builder for json
            sqs.put(schema, "todo");
            return (B)this;
        }


        public B withStomp() {
            if(stomp == null) {
                stomp = new HashMap<>();
            }
            return (B)this;
        }

        public B withStompSchema(String schema) {
            if(stomp == null) {
                stomp = new HashMap<>();
            }
            //todo use builder for json
            stomp.put(schema, "todo");
            return (B)this;
        }

        public B withRedis() {
            if(redis == null) {
                redis = new HashMap<>();
            }
            return (B)this;
        }

        public B withRedisSchema(String schema) {
            if(redis == null) {
                redis = new HashMap<>();
            }
            //todo use builder for json
            redis.put(schema, "todo");
            return (B)this;
        }


        public B with$ref(String $ref) {
            this.$ref = $ref;
            return (B)this;
        }

    }
}
