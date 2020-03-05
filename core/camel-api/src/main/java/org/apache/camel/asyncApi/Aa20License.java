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

/**
 * License information for the exposed API.
 */
public class Aa20License {

    String name;
    String url;

    public Aa20License(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Required. The license name used for the API.
     */
    public Aa20License setName(String name) {
        this.name = name;
        return this;
    }

    public String getUrl() {
        return url;
    }

    /**
     * A URL to the license used for the API. MUST be in the format of a URL.
     */
    public Aa20License setUrl(String url) {
        this.url = url;
        return this;
    }
}
