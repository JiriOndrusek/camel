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
 * Contact information for the exposed API.
 */
public class Aa20Contact {

    String name;
    String url;
    String email;

    public String getName() {
        return name;
    }

    /**
     * The identifying name of the contact person/organization.
     */
    public Aa20Contact setName(String name) {
        this.name = name;
        return this;
    }

    public String getUrl() {
        return url;
    }

    /**
     * The URL pointing to the contact information. MUST be in the format of a URL.
     */
    public Aa20Contact setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getEmail() {
        return email;
    }

    /**
     * The email address of the contact person/organization. MUST be in the format of an email address.
     */
    public Aa20Contact setEmail(String email) {
        this.email = email;
        return this;
    }
}
