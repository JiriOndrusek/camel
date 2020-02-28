package org.apache.camel.asyncapi;

import org.apache.camel.CamelContext;
import org.apache.camel.Processor;
import org.apache.camel.spi.AsyncAPIConfiguration;
import org.apache.camel.spi.AsyncApiProcessorFactory;

import java.util.Collections;

public class AsyncApiProcessorFactoryImpl implements AsyncApiProcessorFactory {
    @Override
    public Processor createApiProcessor(CamelContext camelContext, String contextPath, String contextIdPattern, boolean contextIdListing, AsyncAPIConfiguration asyncAPIConfiguration) throws Exception {
        //todo
        return new AsyncApiProcessor(contextIdPattern, contextIdListing, Collections.emptyMap(), asyncAPIConfiguration);
    }
}
