package org.apache.camel.asyncapi.model;

import org.apache.camel.asyncApi.Aa20CorellationId;

public class Aa20CorrelationIdImpl extends AbstractAa20SpecificationExtensionImpl implements Aa20CorellationId {

    private String location;
    private String description;
    private String $ref;

    public static Builder newBuilder() {
        return new Builder();
    }

    private Aa20CorrelationIdImpl() {
        super(null);
    }

    private Aa20CorrelationIdImpl(Builder b) {
        super(b);
        this.location = b.location;
        this.description = b.description;
        this.$ref = b.$ref;
    }

    @Override
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String get$ref() {
        return $ref;
    }

    public void set$ref(String $ref) {
        this.$ref = $ref;
    }

    // --------------------------------------- builder ---------------------------------------------------------

    public static class Builder extends AbstractSpecificationExtensionsBuilder<Builder, Aa20CorellationId> {

        private String location;
        private String description;
        private String $ref;

        private Builder() {
        }

        public Builder withLocation(String location) {
            this.location = location;
            return this;
        }

        public Builder witDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder with$ref(String $ref) {
            this.$ref = $ref;
            return this;
        }

        @Override
        public Aa20CorellationId done() {
            return new Aa20CorrelationIdImpl(this);
        }
    }
}
