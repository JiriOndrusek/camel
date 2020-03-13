package org.apache.camel.asyncapi.model;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.camel.asyncApi.Aa20ExternalDocumentation;
import org.apache.camel.asyncApi.Aa20Schema;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Aa20SchemaImpl extends AbstractAa20SpecificationExtensionImpl implements Aa20Schema {

    private String discriminator;
    private Aa20ExternalDocumentation externalDocs;
    private boolean deprecated;
    private String $ref;
    @JsonIgnore
    private Map<String, Object> data;

    public static Builder newBuilder() {
        return new Builder();
    }

    private Aa20SchemaImpl() {
        super(null);
    }

    private Aa20SchemaImpl(Builder b) {
        super(b);
        this.discriminator = b.discriminator;
        this.externalDocs = b.externalDocs;
        this.deprecated = b.deprecated;
        this.$ref = b.$ref;
    }

    @Override
    public String getDiscriminator() {
        return discriminator;
    }

    public void setDiscriminator(String discriminator) {
        this.discriminator = discriminator;
    }

    @Override
    public Aa20ExternalDocumentation getExternalDocs() {
        return externalDocs;
    }

    public void setExternalDocs(Aa20ExternalDocumentation externalDocs) {
        this.externalDocs = externalDocs;
    }

    @Override
    public boolean isDeprecated() {
        return deprecated;
    }

    public void setDeprecated(boolean deprecated) {
        this.deprecated = deprecated;
    }

    @Override
    public String get$ref() {
        return $ref;
    }

    public void set$ref(String $ref) {
        this.$ref = $ref;
    }

    @Override
    public Map<String, Object> getData() {
        Map<String, Object> data = new LinkedHashMap<>(getSpecificationExtensions());
        data.entrySet().removeIf(entry -> entry.getKey().startsWith("x-"));
        return data;
    }

    @JsonAnySetter
    @Override
    public void addSpecificationExtensions(String propertyKey, Object value) {
        if(!propertyKey.startsWith("x-")) {
            if(data == null) {
                data = new LinkedHashMap<>();
            }
            data.put(propertyKey, value);
        }
        super.addSpecificationExtensions(propertyKey, value);
    }

    // --------------------------------------- builder ---------------------------------------------------------

    public static class Builder extends AbstractSpecificationExtensionsBuilder<Builder, Aa20Schema> {

        private String discriminator;
        private Aa20ExternalDocumentation externalDocs;
        private boolean deprecated;
        private String $ref;

        private Builder() {
        }

        public Builder withDiscriminator(String discriminator) {
            this.discriminator = discriminator;
            return this;
        }

        public Builder withExternalDocs(Aa20ExternalDocumentation externalDocs) {
            this.externalDocs = externalDocs;
            return this;
        }


        public Builder withDeprecated(boolean deprecated) {
            this.deprecated = deprecated;
            return this;
        }


        public Builder with$ref(String $ref) {
            this.$ref = $ref;
            return this;
        }

        private Builder withData(String name, Object value) {
            if(name.startsWith("x-")) {
                throw new IllegalArgumentException("todo");
            }
            this.specificationExtensions.put(name, value);
            return this;
        }

        public Builder withObject(String name, Object value) {
            return withData(name, value);
        }

        public Builder addObject(String name, Consumer<Aa20ObjectBuilder> object) {
            Aa20ObjectBuilder o = Aa20ObjectBuilder.newBuilder();
            object.accept(o);
            Object ob = o.done();
            return withData(name, ob);
        }

        @Override
        public Aa20Schema done() {
            return new Aa20SchemaImpl(this);
        }
    }
}
