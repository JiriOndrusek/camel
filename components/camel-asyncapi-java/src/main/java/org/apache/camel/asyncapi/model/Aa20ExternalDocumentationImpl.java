package org.apache.camel.asyncapi.model;

import org.apache.camel.asyncApi.Aa20ExternalDocumentation;

public class Aa20ExternalDocumentationImpl extends AbstractAa20SpecificationExtensionImpl implements Aa20ExternalDocumentation {

    private String description;
    private String url;

    public Aa20ExternalDocumentationImpl() {
        super(null);
    }

    public Aa20ExternalDocumentationImpl(Builder b) {
        super(b);
        this.description = b.description;
        this.url = b.url;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // --------------------------------------- builder ---------------------------------------------------------

    public static class Builder extends AbstractSpecificationExtensionsBuilder<Aa20ContactImpl.Builder, Aa20ExternalDocumentation> {

        private String description;
        private String url;

        private Builder() {
        }


        public Builder witDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withUrl(String url) {
            this.url = url;
            return this;
        }

        @Override
        public Aa20ExternalDocumentation done() {
            return new Aa20ExternalDocumentationImpl(this);
        }
    }

}
