package org.apache.camel.component.leveldb;

import java.io.IOException;

public interface LevelDBSerializer {

    byte[] serialize(Object object) throws IOException;

    Object deserialize(byte[] buffer) throws IOException, ClassNotFoundException;
}
