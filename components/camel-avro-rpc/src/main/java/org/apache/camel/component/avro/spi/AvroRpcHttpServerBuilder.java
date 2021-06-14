package org.apache.camel.component.avro.spi;

import org.apache.avro.ipc.Server;
import org.apache.avro.ipc.specific.SpecificResponder;

import java.io.IOException;

public interface AvroRpcHttpServerBuilder {

    public Server create(SpecificResponder responder, int port) throws IOException;
}
