package org.apache.camel.spi;

import io.apicurio.datamodels.asyncapi.v2.models.Aai20Document;


public class AsyncAPIConfiguration {


    private Aai20Document asyncapiDoc;

    public Aai20Document getAsyncapiDoc() {
        return asyncapiDoc;
    }

    public void setAsyncapiDoc(Aai20Document asyncapiDoc) {
        this.asyncapiDoc = asyncapiDoc;
    }
}
