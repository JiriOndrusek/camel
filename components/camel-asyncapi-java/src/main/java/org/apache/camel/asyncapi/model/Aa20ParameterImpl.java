package org.apache.camel.asyncapi.model;

import org.apache.camel.asyncApi.Aa20Parameter;
import org.apache.camel.asyncApi.Aa20Schema;

import java.util.function.Consumer;

public class Aa20ParameterImpl extends AbstractAa20SpecificationExtensionImpl implements Aa20Parameter {

    private String location;
    private String description;
    private Aa20Schema schema;
    private String $ref;

    public static Builder newBuilder() {
        return new Builder();
    }

    private Aa20ParameterImpl() {
        super(null);
    }

    private Aa20ParameterImpl(Builder b) {
        super(b);
        this.location = b.location;
        this.description = b.description;
        this.schema = b.schema;
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
    public Aa20Schema getSchema() {
        return schema;
    }

    public void setSchema(Aa20Schema schema) {
        this.schema = schema;
    }

    @Override
    public String get$ref() {
        return $ref;
    }

    public void set$ref(String $ref) {
        this.$ref = $ref;
    }

    // --------------------------------------- builder ---------------------------------------------------------

    public static class Builder extends AbstractSpecificationExtensionsBuilder<Aa20ContactImpl.Builder, Aa20Parameter> {

        private String location;
        private String description;
        private Aa20Schema schema;
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

        public Builder addSchema(Consumer<Aa20SchemaImpl.Builder> schema) {
            Aa20SchemaImpl.Builder builder = Aa20SchemaImpl.newBuilder();
            schema.accept(builder);
            this.schema = builder.done();
            return this;
        }


        public Builder with$ref(String $ref) {
            this.$ref = $ref;
            return this;
        }

        @Override
        public Aa20Parameter done() {
            return new Aa20ParameterImpl(this);
        }
    }
}
