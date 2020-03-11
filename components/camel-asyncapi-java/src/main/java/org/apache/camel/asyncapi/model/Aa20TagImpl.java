package org.apache.camel.asyncapi.model;

import org.apache.camel.asyncApi.Aa20ExternalDocumentation;
import org.apache.camel.asyncApi.Aa20Tag;

public class Aa20TagImpl implements Aa20Tag {


    private Aa20TagImpl() {
    }

    private  String name;
    private String description;
    private Aa20ExternalDocumentation externalDocs;

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
}
