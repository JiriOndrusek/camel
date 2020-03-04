package org.apache.camel.asyncapi;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.spi.AsyncApiConfiguration;

public class AsyncApiProcessor implements Processor {

    private final AsyncApiConfiguration configuration;

    public AsyncApiProcessor(AsyncApiConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void process(Exchange exchange) throws Exception {

    }
}
