package org.apache.camel.asyncapi.model;

import org.apache.camel.asyncApi.Aa20Contact;
import org.apache.camel.asyncApi.Aa20ExternalDocumentation;

public class Aa20ExternaDocumentationImpl implements Aa20ExternalDocumentation {

    private String description;
    private String url;

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

//    public static class Builder extends NestedBuilder<Aa20InfoImpl.Builder, Aa20ExternalDocumentation> {
//
//    }


}
