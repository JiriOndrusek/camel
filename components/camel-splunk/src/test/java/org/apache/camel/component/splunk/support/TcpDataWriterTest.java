package org.apache.camel.component.splunk.support;

import java.util.Optional;

public class TcpDataWriterTest {

    public void localPortTest() {
        TcpDataWriter tdw = new TcpDataWriter(null, null);
        tdw.setLocalPort(Optional.of(2222));
        tdw.setPort(1111);
        //        tdw.createSocket();

    }

}
