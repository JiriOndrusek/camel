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
package org.apache.camel.component.cassandra;

import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.net.URL;

public abstract class BaseCassandraTest extends CamelTestSupport {

    public static boolean canTest() {
        // we cannot test on CI
        //todo do not commit
        URL url = Thread.currentThread().getContextClassLoader().getResource("camel-cassandra.yaml");
        System.setProperty("cassandra.config", "file://"+url.getFile());
        System.out.println("-------------------------------------------------------------");
        System.out.println("-------------------------------------------------------------");
        System.out.println("-------------------------------------------------------------");
        return true;
    }

    @Override
    public void afterAll(ExtensionContext context) {
        super.afterAll(context);
        try {
            CassandraUnitUtils.cleanEmbeddedCassandra();
        } catch (Throwable e) {
            // ignore shutdown errors
        }
    }

}
