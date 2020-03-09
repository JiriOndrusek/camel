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

import org.apache.camel.asyncApi.Aa20Contact;

public class Aa20ContactImpl implements Aa20Contact {

    String name;
    String url;
    String email;

    public static Aa20ContactImpl.Builder newBuilder() {
        return new Aa20ContactImpl.Builder();
    }

    private Aa20ContactImpl() {
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // --------------------------------------- builder ---------------------------------------------------------

    public static class Builder extends NestedBuilder<Aa20InfoImpl.Builder, Aa20Contact> {
        String name;
        String url;
        String email;

        public String getName() {
            return name;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public String getUrl() {
            return url;
        }

        public Builder withUrl(String url) {
            this.url = url;
            return this;
        }

        public String getEmail() {
            return email;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        @Override
        public Aa20Contact build() {
            Aa20ContactImpl contact = new Aa20ContactImpl();
            contact.setName(this.name);
            contact.setUrl(this.url);
            contact.setEmail((this.email));
            return contact;
        }
    }
}
