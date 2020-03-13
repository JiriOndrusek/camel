package org.apache.camel.asyncapi.model;

import org.apache.camel.asyncApi.Aa20ExternalDocumentation;
import org.apache.camel.asyncApi.Aa20Tag;

import java.util.function.Consumer;

public class Aa20TagImpl extends AbstractAa20SpecificationExtensionImpl implements Aa20Tag {

    private  String name;
    private String description;
    private Aa20ExternalDocumentation externalDocs;

    public static Builder newBuilder() {
        return new Builder();
    }

    private Aa20TagImpl() {
        super(null);
    }

    private Aa20TagImpl(Builder b) {
        super(b);
        this.name = b.name;
        this.description = b.description;
        this.externalDocs = b.externalDocs;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Aa20ExternalDocumentation getExternalDocs() {
        return externalDocs;
    }

    public void setExternalDocs(Aa20ExternalDocumentation externalDocs) {
        this.externalDocs = externalDocs;
    }


    // --------------------------------------- builder ---------------------------------------------------------

    public static class Builder extends AbstractSpecificationExtensionsBuilder<Aa20ContactImpl.Builder, Aa20Tag> {

        private  String name;
        private String description;
        private Aa20ExternalDocumentation externalDocs;

        private Builder() {
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder witDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder addExternalDocs(Consumer<Aa20ExternalDocumentationImpl.Builder> externalDocs) {
            Aa20ExternalDocumentationImpl.Builder builder = Aa20ExternalDocumentationImpl.newBuilder();
            externalDocs.accept(builder);
            this.externalDocs = builder.done();
            return this;
        }

        @Override
        public Aa20Tag done() {
            return new Aa20TagImpl(this);
        }
    }
}
