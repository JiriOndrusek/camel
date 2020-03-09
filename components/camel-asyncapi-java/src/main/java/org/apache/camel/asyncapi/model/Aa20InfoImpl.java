package org.apache.camel.asyncapi.model;

import org.apache.camel.asyncApi.Aa20Contact;
import org.apache.camel.asyncApi.Aa20Info;
import org.apache.camel.asyncApi.Aa20License;

public class Aa20InfoImpl implements Aa20Info {

    private String title;
    private String version;
    private String description;
    private String termsOfService;
    private Aa20Contact contact;
    private Aa20License license;

    public static Aa20InfoImpl.Builder newBuilder() {
        return new Aa20InfoImpl.Builder();
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
        String title;
        String version;
        String description;
        String termsOfService;

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

        @Override
        public Aa20Info build() {
            Aa20InfoImpl info = new Aa20InfoImpl();
            info.setTitle(this.title);
            info.setDescription(this.description);
            info.setVersion(this.version);
            info.setTermsOfService(this.termsOfService);
            return info;
        }
    }
}
