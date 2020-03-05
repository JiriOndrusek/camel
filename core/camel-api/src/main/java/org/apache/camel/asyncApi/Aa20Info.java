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
 * The object provides metadata about the API. The metadata can be used by the clients if needed.
 */
public class Aa20Info {

    String title;
    String version;
    String description;
    String termsOfService;
    Aa20Contact contact;
    Aa20License license;

    public Aa20Info(String title, String version) {
        this.title = title;
        this.version = version;
    }

    public String getTitle() {
        return title;
    }

    /**
     * Required. The title of the application.
     */
    public Aa20Info setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getVersion() {
        return version;
    }

    /**
     * Required Provides the version of the application API (not to be confused with the specification version).
     */
    public Aa20Info setVersion(String version) {
        this.version = version;
        return this;
    }

    public String getDescription() {
        return description;
    }

    /**
     * A short description of the application. CommonMark syntax can be used for rich text representation.
     */
    public Aa20Info setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getTermsOfService() {
        return termsOfService;
    }

    /**
     * A URL to the Terms of Service for the API. MUST be in the format of a URL.
     */
    public Aa20Info setTermsOfService(String termsOfService) {
        this.termsOfService = termsOfService;
        return this;
    }

    public Aa20Contact getContact() {
        return contact;
    }

    /**
     * The contact information for the exposed API.
     */
    public Aa20Contact createContact() {
        this.contact = new Aa20Contact();
        return this.contact;
    }

    public Aa20License getLicense() {
        return license;
    }

    /**
     * The license information for the exposed API.
     */
    public Aa20Info setLicense(Aa20License license) {
        this.license = license;
        return this;
    }
}
