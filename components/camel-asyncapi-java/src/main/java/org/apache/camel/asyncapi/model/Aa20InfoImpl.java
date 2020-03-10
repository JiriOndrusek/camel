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

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.apache.camel.asyncApi.Aa20Contact;
import org.apache.camel.asyncApi.Aa20Info;
import org.apache.camel.asyncApi.Aa20License;

import java.util.function.Consumer;

public class Aa20InfoImpl implements Aa20Info {

    private String title;
    private String version;
    private String description;
    private String termsOfService;
    private Aa20Contact contact;
    private Aa20License license;

    public static Aa20InfoImpl.Builder newBuilder(Aa20ObjectImpl.Builder parent, Consumer<Aa20Info> c) {
        return new Aa20InfoImpl.Builder(parent, c);
    }

    private Aa20InfoImpl() {
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getTermsOfService() {
        return termsOfService;
    }

    public void setTermsOfService(String termsOfService) {
        this.termsOfService = termsOfService;
    }

    @Override
    public Aa20Contact getContact() {
        return contact;
    }

    public void setContact(Aa20Contact contact) {
        this.contact = contact;
    }

    @Override
    public Aa20License getLicense() {
        return license;
    }

    public void setLicense(Aa20License license) {
        this.license = license;
    }

    // --------------------------------------- builder ---------------------------------------------------------

    public static class Builder extends NestedBuilder<Aa20ObjectImpl.Builder, Aa20Info> {
        private String title;
        private String version;
        private String description;
        private String termsOfService;
        private Aa20Contact contact;
        private Aa20License license;

        private Builder(Aa20ObjectImpl.Builder parent, Consumer<Aa20Info> c) {
            super(parent, c);
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withVersion(String version) {
            this.version = version;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withTermsOfService(String termsOfService) {
            this.termsOfService = termsOfService;
            return this;
        }

        public void withAa20ContactImpl(Aa20Contact contact) {
            this.contact = contact;
        }

        public void withAa20LicenseImpl(Aa20License license) {
            this.license = license;
        }

        public Aa20ContactImpl.Builder addContact() {
            return Aa20ContactImpl.newBuilder(this, o -> {withAa20ContactImpl(o);});
        }

        public Aa20LicenseImpl.Builder addLicense() {
            return Aa20LicenseImpl.newBuilder(this, o -> {withAa20LicenseImpl(o);});
        }

        @Override
        public Aa20Info build() {
            Aa20InfoImpl info = new Aa20InfoImpl();
            info.setTitle(this.title);
            info.setDescription(this.description);
            info.setVersion(this.version);
            info.setTermsOfService(this.termsOfService);
            info.setContact(this.contact);
            info.setLicense(this.license);
            return info;
        }
    }
}
