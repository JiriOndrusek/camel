package org.apache.camel.asyncapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.asyncApi.Aa20Object;
import org.apache.camel.spi.AsyncApiConfigurationOwner;

public class AsyncApiProcessor implements Processor {

    private final AsyncApiConfigurationOwner configurationOwner;

    public AsyncApiProcessor(AsyncApiConfigurationOwner configurationOwner) {
        this.configurationOwner = configurationOwner;
    }

    @Override
    public void process(Exchange exchange) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        Aa20Object object = configurationOwner.createAsyncAPIDefinition();
        byte[] bytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(object);
//        String s = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);


        exchange.getIn().setBody(bytes);


//        Aai20Document object = configurationOwner.createAsyncAPIDefinition();
//        io.apicurio.datamodels.Library.writeNode(object);
//        Object dump = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(object);
//        byte[] bytes = objectMapper.writeValueAsBytes(dump);
//        int len = bytes.length;
//        exchange.getIn().setHeader(Exchange.CONTENT_LENGTH, "" + len);

        exchange.getIn().setBody(bytes);
    }
}
