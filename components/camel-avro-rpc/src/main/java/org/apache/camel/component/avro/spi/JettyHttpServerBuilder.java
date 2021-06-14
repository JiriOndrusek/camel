package org.apache.camel.component.avro.spi;

import org.apache.avro.ipc.Server;
import org.apache.avro.ipc.jetty.HttpServer;
import org.apache.avro.ipc.specific.SpecificResponder;

import java.io.IOException;

public class JettyHttpServerBuilder implements AvroRpcHttpServerBuilder {
    @Override
    public Server create(SpecificResponder responder, int port) throws IOException {
        return new HttpServer(responder, port);
    }
}
