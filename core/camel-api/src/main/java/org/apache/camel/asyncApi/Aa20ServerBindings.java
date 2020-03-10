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

import java.util.Map;

public interface Aa20ServerBindings {

   Map<String, Object> getHttp();

   Map<String, Object> getWs();

   Map<String, Object> getKafka();

   Map<String, Object> getAmqp();

   Map<String, Object> getAmqp1();

   Map<String, Object> getMqtt();

   Map<String, Object> getMqtt5();

   Map<String, Object> getNats();

   Map<String, Object> getJms();

   Map<String, Object> getSns();

   Map<String, Object> getSqs();

   Map<String, Object> getStomp();

   Map<String, Object> getRedis();
}
