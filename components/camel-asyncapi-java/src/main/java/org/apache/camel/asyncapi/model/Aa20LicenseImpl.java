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

import org.apache.camel.asyncApi.Aa20License;

public class Aa20LicenseImpl implements Aa20License {

    String name;
    String url;

    public static Aa20LicenseImpl.Builder newBuilder() {
        return new Aa20LicenseImpl.Builder();
    }

    private Aa20LicenseImpl() {
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

    // --------------------------------------- builder ---------------------------------------------------------

    public static class Builder extends NestedBuilder<Aa20InfoImpl.Builder, Aa20License> {
        String name;
        String url;

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

        @Override
        public Aa20License build() {
            Aa20LicenseImpl license = new Aa20LicenseImpl();
            license.setName(this.name);
            license.setUrl(this.url);
            return license;
        }
    }
}
